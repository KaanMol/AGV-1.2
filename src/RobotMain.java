import TI.BoeBot;
import common.Config;
import enums.Direction;
import enums.Manoeuvre;
import enums.WhiskerStatus;
import hardware.Button;
import hardware.IRSensor;
import interfaces.CollisionDetectionUpdater;
import interfaces.IRSignalMover;
import interfaces.MovementUpdater;
import interfaces.Updatable;
import vehicle.*;

import java.util.ArrayList;

public class RobotMain implements MovementUpdater, CollisionDetectionUpdater, IRSignalMover {
    private ArrayList<Updatable> processes;
    private Movement movement;
    private Blinkers blinkers;
    private Infrared infrared;
    private DrivingNotification drivingNotification;
    private CollisionDetection collisionDetection;
    private Button emergencyStop;
    private boolean emergencyStopActivated = false;
    private IRSensor sensor = new IRSensor();


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

        this.drivingNotification = new DrivingNotification();
        this.processes.add(this.drivingNotification);

        this.blinkers = new Blinkers();
        this.processes.add(this.blinkers);
        
        this.infrared = new Infrared(this);
        this.processes.add(this.infrared);
    }

    /**
     * Handles the updates of the system by calling the update method from Updatable interface
     */
    private void updater() {
        while (this.emergencyStopActivated == false) {
            for (Updatable process: processes) {
                if (this.emergencyStop.isPressed()) {
                    this.emergencyStopActivated = true;
                    break;
                }
                sensor.getRemoteCode();
                process.update();
            }
            BoeBot.wait(1);
        }
        this.stop();
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
    /*
    public void moveIRSignal(Direction heading){
        System.out.println(heading);
        if (heading == Direction.FORWARD) {
            this.movement.forward();
        }
        else if (heading == Direction.BACKWARD) {
            this.movement.backward();
        }
        else if (heading == Direction.RIGHT) {
            this.movement.turnRight();
        }
        else if (heading == Direction.LEFT) {
            this.movement.turnLeft();
        }
        else if (heading == Direction.NEUTRAL) {
            this.movement.neutral();
        }
    }
    */

    public void getOrder(int signal) {
        if (signal == Config.remoteForward) {
            this.movement.forward();
        }
        else if (signal == Config.remoteBackward) {
            this.movement.backward();
        }
        else if (signal == Config.remoteRight) {
            this.movement.turnRight();
        }
        else if (signal == Config.remoteLeft) {
            this.movement.turnLeft();
        }
        else if (signal == Config.remoteNeutral) {
            this.movement.neutral();
        }
        else if (signal == Config.remoteEmergencyStop) {
            this.emergencyStopActivated = true;
        }
    }

    /**
     * Callback that gets called when the vehicle direction changes.
     * @param heading - The direction where the vehicle is heading to
     */
    public void onMovementUpdate(Direction heading) {
        if (heading == Direction.LEFT || heading == Direction.RIGHT) {
            this.blinkers.start(heading);
            this.drivingNotification.stop();
        } else {
            this.blinkers.stop();

            if (heading == Direction.BACKWARD) {
                this.drivingNotification.start();
            } else {
                this.drivingNotification.stop();
            }
        }
    }

    /**
     * Callback that gets called when the vehicle detects a collision.
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
}
