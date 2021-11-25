package vehicle;

import common.Config;
import hardware.Whisker;
import interfaces.CollisionDetectionUpdater;
import interfaces.Updatable;

public class CollisionDetection implements Updatable {
    private CollisionDetectionUpdater callback;
    private Whisker leftWhisker;
    private Whisker rightWhisker;

    public CollisionDetection(CollisionDetectionUpdater callback) {
        this.callback = callback;
        this.leftWhisker = new Whisker(Config.leftWhiskerPin);
        this.rightWhisker = new Whisker(Config.rightWhiskerPin);
    }

    public void update() {
        System.out.println(this.leftWhisker.getPinNum());
        this.leftWhisker.setPinMode();
        if (this.leftWhisker.isPressed()) {
            System.out.println("left whisker is pressed");
            this.callback.onCollisionDetectionUpdate(0);
        }
        this.rightWhisker.setPinMode();
        if (this.rightWhisker.isPressed()) {
            this.callback.onCollisionDetectionUpdate(1);
        }

        if (this.leftWhisker.isPressed() && this.rightWhisker.isPressed()) {
            this.callback.onCollisionDetectionUpdate(2);
        }
    }
}
