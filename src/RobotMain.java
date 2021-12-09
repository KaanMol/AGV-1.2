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
import vehicle.Blinkers;
import vehicle.CollisionDetection;
import vehicle.DrivingNotification;
import vehicle.Movement;
import java.util.ArrayList;

public class RobotMain implements MovementUpdater, CollisionDetectionUpdater {
    private ArrayList<Updatable> processes;
    private Movement movement;
    private Blinkers blinkers;
    private DrivingNotification drivingNotification;
    private CollisionDetection collisionDetection;
    private Button emergencyStop;
    private boolean emergencyStopActivated = false;

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
        this.movement.forward();

        this.collisionDetection = new CollisionDetection(this);
        this.processes.add(this.collisionDetection);

        this.drivingNotification = new DrivingNotification();
        this.processes.add(this.drivingNotification);

        this.blinkers = new Blinkers();
        this.processes.add(this.blinkers);
    }

    /**
     * Handles the updates of the system by calling the update method from Updatable interface
     */
    private void updater() {
        while (true) {
            if (!this.emergencyStopActivated && !this.emergencyStop.isPressed()) {
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
                    if (!this.emergencyStopActivated) {
                        process.update();
                    }
                }
                BoeBot.wait(1);
            } else if (!this.emergencyStop.isPressed()) {
                while (true) {
                    if (this.emergencyStop.isPressed()) {
                        System.out.println("start");
                        this.emergencyStopActivated = false;
                        while(true) {
                            System.out.println("h");
                            if(!this.emergencyStop.isPressed()) {
                                this.movement.forward();
                                break;
                            }
                        }
                        break;
                    }
                }
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
     * Callback that gets called when the vehicle direction changes.
     *
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

    public void emergencyStop() {
        if(this.emergencyStop.isPressed()){
            this.movement.neutral();
            while(this.emergencyStop.isPressed()){
            }
        }

    }
}
