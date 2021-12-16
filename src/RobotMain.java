import TI.BoeBot;
import common.Config;
import common.WirelessConfig;
import enums.Direction;
import enums.LineDirection;
import enums.Manoeuvre;
import enums.WhiskerStatus;
import hardware.Button;
import hardware.Infrared;
import interfaces.CollisionDetectionUpdater;
import interfaces.InfraredUpdater;
import interfaces.LineDetectionUpdater;
import interfaces.MovementUpdater;
import interfaces.Updatable;
import vehicle.*;

import interfaces.WirelessUpdater;
import java.util.ArrayList;

public class RobotMain implements MovementUpdater, CollisionDetectionUpdater, WirelessUpdater, InfraredUpdater, LineDetectionUpdater {
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
    private Infrared sensor = new Infrared();
    private DrivingLights drivinglights;

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
        this.emergencyStop = new Button(Config.emergencyStopButtonPin);

        this.movement = new Movement(this);
        this.processes.add(this.movement);

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
        this.processes.add(this.drivinglights);

        this.wirelessConnection = new WirelessConnection(this);
        this.processes.add(this.wirelessConnection);
    }

    /**
     * Handles the updates of the system by calling the update method from Updatable interface
     */
    private void updater() {
        while (this.emergencyStopActivated == false) {
            for (Updatable process : processes) {
                if (this.emergencyStop.isPressed()) {
                    this.emergencyStopActivated = true;
                    break;
                }
                sensor.getRemoteCode();
                process.update();
            }
            BoeBot.wait(1);
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
     * This method moves the robot towards the side the user told it to with the remote
     */
    public void onInfraredCommandUpdate(int signal) {
        if (signal == Config.remoteForward) {
            this.movement.forward();
        } else if (signal == Config.remoteBackward) {
            this.movement.backward();
        } else if (signal == Config.remoteRight) {
            this.movement.turnRight();
        } else if (signal == Config.remoteLeft) {
            this.movement.turnLeft();
        } else if (signal == Config.remoteNeutral) {
            this.movement.neutral();
        } else if (signal == Config.remoteEmergencyStop) {
            this.emergencyStopActivated = true;
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

        } else if (heading == Direction.FORWARD || heading == Direction.BACKWARD) {
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
     * Callbakc that gets called when the vehicle detects Bluetooth
     * @param data Which ASCII Number is given
     */
    public void onWirelessUpdate(int data) {
        if (data == WirelessConfig.backward) {
            this.movement.backward();
        } else if (data == WirelessConfig.left) {
            this.movement.turnLeft();
        } else if (data == WirelessConfig.right) {
            this.movement.turnRight();
        } else if (data == WirelessConfig.forward) {
            this.movement.forward();
        } else if (data == WirelessConfig.stop) {
            this.movement.neutral();
        } else if (data == WirelessConfig.transfer) {
        }
    }

    /**
     * Callback that gets called when the vehicle detects a line.
     * @param lineDetection
     */
    public void onLineDetectionUpdate(LineDirection lineDetection) {
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
                break;
        }
    }
}
