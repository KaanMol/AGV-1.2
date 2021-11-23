import TI.BoeBot;
import interfaces.CollisionDetectionUpdater;
import interfaces.MovementUpdater;
import interfaces.Updatable;
import vehicle.CollisionDetection;
import vehicle.Movement;

import java.util.ArrayList;

public class Ed implements MovementUpdater, CollisionDetectionUpdater {
    private ArrayList<Updatable> processes;
    private Movement movement;
    private CollisionDetection collisionDetection;

    public Ed() {
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
    }

    public void updater() {
        while (true) {
            for (Updatable process: processes) {
                process.update();
            }
            BoeBot.wait(1);
        }
    }

    public void onMovementUpdate() {

    }

    public void onCollisionDetectionUpdate(int bs) {
        if (bs == 2) {
            System.out.println("Both!");
            this.movement.backwards();
        }
    }
}
