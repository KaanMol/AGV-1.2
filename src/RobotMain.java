import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import common.Config;
import enums.Direction;
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

    public RobotMain() {
        this.initialize();
        this.updater();
    }

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

    private void updater() {
        while (this.emergencyStopActivated == false) {
            for (Updatable process: processes) {
                if (this.emergencyStop.isPressed()) {
                    System.out.println(this.emergencyStop.isPressed());
                    this.emergencyStopActivated = true;
                    break;
                }
                process.update();
            }
            BoeBot.wait(1);
        }
        System.out.println("STOP");
        this.stop();
    }

    private void stop() {
        this.movement.neutral();
        this.drivingNotification.stop();
        this.blinkers.stop();
    }

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

    public void onCollisionDetectionUpdate(int whiskerCollision) {
        if (whiskerCollision == 0 || whiskerCollision == 2) {
            this.movement.setManoeuvre("LEFT");
        }

        if (whiskerCollision == 1) {
            this.movement.setManoeuvre("RIGHT");
        }
    }
}
