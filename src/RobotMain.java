import TI.BoeBot;
import configuration.Config;
import configuration.Wireless;
import enums.*;
import hardware.Button;
import interfaces.*;
import vehicle.*;

import java.util.ArrayList;

public class RobotMain implements MovementUpdater, CollisionDetectionUpdater, WirelessUpdater, InfraredUpdater, LineDetectionUpdater, DistanceDetectionUpdater {
    private ArrayList<Updatable> processes;
    private Movement movement;
    private Blinkers blinkers;
    private Remote remote;
    private DrivingNotification drivingNotification;
    private CollisionDetection collisionDetection;
    private WirelessConnection wirelessConnection;
    private LineDetection lineDetection;
    private Button emergencyStop;
    private boolean emergencyStopActivated = false;
    private boolean hasObstacle = false;
    private Direction lastHeading = Direction.FORWARD;
    private DrivingLights drivinglights;
    private Gripper gripper;
    private DistanceDetection distanceDetection;
    private Button startButton;

    ControlOwner controlOwner = ControlOwner.Line;

    public static void main(String[] args) {
        new RobotMain();
    }

    /**
     * Constructor
     */
    public RobotMain() {

        this.initialize();
        this.updater();
    }

    /**
     * Initializes all systems and adds them to the queue
     */
    private void initialize() {
        this.processes = new ArrayList<>();

        this.movement = new Movement(this);
        this.processes.add(this.movement);
        this.movement.forward();

        this.collisionDetection = new CollisionDetection(this);
        this.processes.add(this.collisionDetection);

        this.lineDetection = new LineDetection(this);
        this.processes.add(this.lineDetection);

        this.drivingNotification = new DrivingNotification();
        this.processes.add(this.drivingNotification);

        this.blinkers = new Blinkers();
        this.processes.add(this.blinkers);

        this.remote = new Remote(this);
        this.processes.add(this.remote);

        this.drivinglights = new DrivingLights();

        this.wirelessConnection = new WirelessConnection(this);
        this.processes.add(this.wirelessConnection);

        this.gripper = new Gripper();
        this.processes.add(this.gripper);

        this.distanceDetection = new DistanceDetection(this);
        this.processes.add(this.distanceDetection);

        this.emergencyStop = new Button(Config.emergencyStopButtonPin);

        this.startButton = new Button(0);
    }

    /**
     * Handles the updates of the system by calling the update method from Updatable interface
     */
    private void updater() {
        while (true) {
            BoeBot.wait(1);
            if (this.emergencyStopActivated == false) {
                for (Updatable process : processes) {
                    if (this.emergencyStop.isPressed()) {
                        System.out.println("stop");
                        this.movement.neutral();
                        this.emergencyStopActivated = true;
                        break;
                    }
                    if (this.emergencyStopActivated) {
                        this.movement.neutral();
                    }
                    process.update();
                }
            } else if (this.startButton.isPressed()) {
                this.movement.forward();
                this.emergencyStopActivated = false;
            }
        }
    }

    /**
     * Stops all systems in the case of emergency
     */
    private void stop() {
        this.movement.neutral();
        this.drivingNotification.stop();
        this.blinkers.stop();
    }

    /**
     * This method is called when the distance from the ultrasonic sensor is too little
     */
    public void onDistanceDetectionUpdate(boolean hasObstacle) {
        if (hasObstacle) {
            this.movement.neutral();
        }
    }

    /**
     * This method moves the robot towards the side the user told it to with the remote
     */
    public void onInfraredCommandUpdate(int signal) {
        if (this.controlOwner != ControlOwner.Remote) {
            System.out.println("Remote took control!");
            this.controlOwner = ControlOwner.Remote;
        }

        if (signal == configuration.Remote.FORWARD_COMMAND) {
            this.movement.forward();
        } else if (signal == configuration.Remote.BACKWARD_COMMAND) {
            this.movement.backward();
        } else if (signal == configuration.Remote.RIGHT_COMMAND) {
            this.movement.turnRight();
        } else if (signal == configuration.Remote.LEFT_COMMAND) {
            this.movement.turnLeft();
        } else if (signal == configuration.Remote.NEUTRAL_COMMAND) {
            this.movement.neutral();
        } else if (signal == configuration.Remote.EMERGENCY_STOP_COMMAND) {
            this.emergencyStopActivated = true;
        } else if (signal == configuration.Remote.TRANSFER_CONTROL_COMMAND) {
            System.out.println("Line follower was given control!");
            this.controlOwner = ControlOwner.Line;
        } else if (signal == configuration.Remote.GRIPPER_COMMAND) {
            this.gripper.toggle();
        }
    }

    /**
     * Callback that gets called when the vehicle direction changes.
     *
     * @param heading - The direction where the vehicle is heading to
     */
    public void onMovementUpdate(Direction heading) {
        if (heading == Direction.LEFT || heading == Direction.RIGHT) {
            this.blinkers.start(heading);
            this.drivingNotification.stop();

        } else if (heading == Direction.FORWARD || heading == Direction.BACKWARD || heading == Direction.NEUTRAL) {
            this.blinkers.stop();
            this.drivinglights.start(heading);
            if (heading == Direction.BACKWARD) {
                this.drivingNotification.start();
            } else {
                this.drivingNotification.stop();
            }

        } else {
            this.blinkers.stop();
        }
    }

    /**
     * Callback that gets called when the vehicle detects a collision.
     *
     * @param whiskerCollision - Which whiskers are triggered.
     */
    public void onCollisionDetectionUpdate(WhiskerStatus whiskerCollision) {
        switch (whiskerCollision) {
            case LEFT:
            case BOTH:
                this.movement.setManoeuvre(Manoeuvre.LEFT);
                break;
            case RIGHT:
                this.movement.setManoeuvre(Manoeuvre.RIGHT);
                break;
        }
    }

    /**
     * Callback that gets called when the vehicle detects Bluetooth
     * @param data Which ASCII Number is given
     */
    public void onWirelessUpdate(int data) {
        if (this.controlOwner != ControlOwner.Wireless) {
            System.out.println("Wireless took control!");
            this.controlOwner = ControlOwner.Wireless;
        }

        if (data == Wireless.backward) {
            this.movement.backward();
        } else if (data == Wireless.left) {
            this.movement.turnLeft();
        } else if (data == Wireless.right) {
            this.movement.turnRight();
        } else if (data == Wireless.forward) {
            this.movement.forward();
        } else if (data == Wireless.stop) {
            this.movement.neutral();
        } else if (data == Wireless.transfer) {
            System.out.println("Linefollower was given control!");
            this.controlOwner = ControlOwner.Line;
        } else if (data == Wireless.gripper) {
            this.gripper.toggle();
        } else if (data == Wireless.brake) {
            this.gripper.toggle();
        }
    }

    /**
     * Callback that gets called when the vehicle detects a line.
     * @param lineDetection
     */
    public void onLineDetectionUpdate(LineDirection lineDetection) {
        if (this.controlOwner != ControlOwner.Line) {
            return;
        }

        switch (lineDetection) {
            case FORWARD:
                this.movement.forward();
                break;
            case LEFT:
                this.movement.turnLeft();
                break;
            case RIGHT:
                this.movement.turnRight();
                break;
            case STOP:
                this.movement.neutral();
                this.drivingNotification.start();
                this.drivinglights.lineLights();
                break;
        }
    }

    public void emergencyStop() {
        if(this.emergencyStop.isPressed()){
            this.movement.neutral();
            while(this.emergencyStop.isPressed()){
            }
        }

    }
}
