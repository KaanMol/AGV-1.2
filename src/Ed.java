import TI.BoeBot;
import TI.Timer;
import enums.Direction;
import interfaces.CollisionDetectionUpdater;
import interfaces.MovementUpdater;
import interfaces.Updatable;
import vehicle.Blinkers;
import vehicle.CollisionDetection;
import vehicle.Movement;

import java.util.ArrayList;

public class Ed implements MovementUpdater, CollisionDetectionUpdater {
    private ArrayList<Updatable> processes;
    private Movement movement;
    private Blinkers blinkers;
    private CollisionDetection collisionDetection;
    private Timer timer;

    public Ed() {
        this.timer = new Timer(1000);
        this.processes = new ArrayList<>();
        this.initialize();
        this.updater();
    }

    public void initialize() {
        this.movement = new Movement(this);
        this.processes.add(this.movement);
        this.movement.forward();

        this.collisionDetection = new CollisionDetection(this);
        this.processes.add(this.collisionDetection);

        this.blinkers = new Blinkers();
        this.processes.add(this.blinkers);
    }

    public void updater() {
        while (true) {
            for (Updatable process: processes) {
                process.update();
            }
            BoeBot.wait(1);
        }
    }

    public void onMovementUpdate(Direction heading) {
        if (heading == Direction.LEFT || heading == Direction.RIGHT) {
            this.blinkers.setBlinker(heading);
        } else {
            this.blinkers.setBlinker(Direction.NEUTRAL);
        }
    }

    public void onCollisionDetectionUpdate(int bs) {
        if (bs == 0 || bs == 2) {
            this.movement.setManoeuvre("LEFT");
        }

        if (bs == 1) {
            this.movement.setManoeuvre("RIGHT");
        }

        if (bs == 2) {
//            System.out.println("Both!");
//
//            if (this.movement.getHeading() == Direction.BACKWARD) {
//                this.movement.forward();
//            } else if (this.movement.getHeading() == Direction.FORWARD) {
//                this.movement.backwards();
//            }

        }
    }
}
