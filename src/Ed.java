import TI.BoeBot;
import TI.Timer;
import common.Config;
import enums.Direction;
import hardware.Buzzer;
import hardware.Button;
import interfaces.CollisionDetectionUpdater;
import interfaces.DrivingNotificationUpdater;
import interfaces.MovementUpdater;
import interfaces.Updatable;
import vehicle.Blinkers;
import vehicle.CollisionDetection;
import vehicle.DrivingNotification;
import vehicle.Movement;

import java.util.ArrayList;

public class Ed implements MovementUpdater, CollisionDetectionUpdater {
    private ArrayList<Updatable> processes;
    private Movement movement;
    private Blinkers blinkers;
    private DrivingNotification notification;
    private CollisionDetection collisionDetection;
    private Timer timer;
    private Button emergencyStop;
    private boolean emergencyStopActivated = false;

    public Ed() {
        this.timer = new Timer(1000);
        this.processes = new ArrayList<>();
        this.initialize();
        this.updater();
    }

    public void initialize() {
        this.emergencyStop = new Button(Config.emergencyStopButtonPin);

        this.movement = new Movement(this);
        this.processes.add(this.movement);
        this.movement.forward();
        System.out.println("forward");

        this.collisionDetection = new CollisionDetection(this);
        this.processes.add(this.collisionDetection);

        this.notification = new DrivingNotification();
        this.processes.add(this.notification);

        this.blinkers = new Blinkers();
        this.processes.add(this.blinkers);
    }

    public void updater() {
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
        this.movement.neutral();
    }

    public void onMovementUpdate(Direction heading) {
        if (heading == Direction.LEFT || heading == Direction.RIGHT) {
            this.blinkers.setBlinker(heading);
        } else {
            this.blinkers.setBlinker(Direction.NEUTRAL);
        }
        if(heading == Direction.BACKWARD)
        {
            this.notification.setBuzzer();
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
