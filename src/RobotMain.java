import TI.BoeBot;
import common.Config;
import common.WirelessConfig;
import enums.Direction;
import enums.Manoeuvre;
import enums.WhiskerStatus;
import hardware.Button;
import interfaces.CollisionDetectionUpdater;
import interfaces.MovementUpdater;
import interfaces.Updatable;
import interfaces.WirelessUpdater;
import vehicle.*;

import java.util.ArrayList;

public class RobotMain implements MovementUpdater, CollisionDetectionUpdater, WirelessUpdater {
    private ArrayList<Updatable> processes;
    private Movement movement;
    private Blinkers blinkers;
    private DrivingNotification drivingNotification;
    private CollisionDetection collisionDetection;
    private WirelessConnection wirelessConnection;
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
//        this.movement.forward();

        this.collisionDetection = new CollisionDetection(this);
        this.processes.add(this.collisionDetection);

        this.drivingNotification = new DrivingNotification();
        this.processes.add(this.drivingNotification);

        this.blinkers = new Blinkers();
        this.processes.add(this.blinkers);

        this.wirelessConnection = new WirelessConnection(this);
        this.processes.add(this.wirelessConnection);

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
}
