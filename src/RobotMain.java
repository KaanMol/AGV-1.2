import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import common.Config;
import enums.Direction;
import enums.Manoeuvre;
import enums.WhiskerStatus;
import hardware.Button;
import interfaces.CollisionDetectionUpdater;
import interfaces.MovementUpdater;
import interfaces.Updatable;
import vehicle.*;

import java.util.ArrayList;

public class RobotMain implements MovementUpdater, CollisionDetectionUpdater {
    private ArrayList<Updatable> processes;
    private Movement movement;
    private Blinkers blinkers;
    private DrivingNotification drivingNotification;
    private CollisionDetection collisionDetection;
    private Button emergencyStop;
    private boolean emergencyStopActivated = false;
    private DrivingLights drivinglights;

    public static void main(String[] args) {
        new RobotMain();
    }

    /**
     * Constructor
     */
    public RobotMain() {
        this.initialize();
        this.drivinglights.start(Direction.FORWARD);
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
        this.movement.forward();

        this.collisionDetection = new CollisionDetection(this);
        this.processes.add(this.collisionDetection);

        this.drivingNotification = new DrivingNotification();
        this.processes.add(this.drivingNotification);

        this.blinkers = new Blinkers();
        this.processes.add(this.blinkers);
        this.drivinglights = new DrivingLights();

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
     * Callback that gets called when the vehicle direction changes.
     * @param heading - The direction where the vehicle is heading to
     */
    public void onMovementUpdate(Direction heading) {
        if (heading == Direction.LEFT || heading == Direction.RIGHT) {
            this.blinkers.start(heading);
            this.drivingNotification.stop();
        }

        else if(heading == Direction.FORWARD || heading == Direction.BACKWARD){
            if(this.blinkers.currentState()){
                this.blinkers.stop();
            }

            this.drivinglights.start(heading);
            if (heading == Direction.BACKWARD) {
                this.drivingNotification.start();
            } else {
                this.drivingNotification.stop();
            }
        }
        else {
            this.blinkers.stop();
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
