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
        if (this.leftWhisker.isPressed() && this.rightWhisker.isPressed()) {
            this.callback.onCollisionDetectionUpdate(2);
        } else if (this.leftWhisker.isPressed()) {
            this.callback.onCollisionDetectionUpdate(0);
        } else if (this.rightWhisker.isPressed()) {
            this.callback.onCollisionDetectionUpdate(1);
        }
    }
}
