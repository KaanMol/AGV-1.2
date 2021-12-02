package vehicle;

import common.Config;
import enums.WhiskerStatus;
import hardware.Whisker;
import interfaces.CollisionDetectionUpdater;
import interfaces.Updatable;

public class CollisionDetection implements Updatable {
    private CollisionDetectionUpdater callback;
    private Whisker leftWhisker;
    private Whisker rightWhisker;

    /**
     * Constructor
     * @param callback - Is used to send the state of the whiskers to
     */
    public CollisionDetection(CollisionDetectionUpdater callback) {
        this.callback = callback;
        this.leftWhisker = new Whisker(Config.leftWhiskerPin);
        this.rightWhisker = new Whisker(Config.rightWhiskerPin);
    }

    /**
     * Checks which whisker is pressed, and calls the callback attribute with the status.
     */
    public void update() {
        if (this.leftWhisker.isPressed() && this.rightWhisker.isPressed()) {
            this.callback.onCollisionDetectionUpdate(WhiskerStatus.BOTH);
        } else if (this.leftWhisker.isPressed()) {
            this.callback.onCollisionDetectionUpdate(WhiskerStatus.LEFT);
        } else if (this.rightWhisker.isPressed()) {
            this.callback.onCollisionDetectionUpdate(WhiskerStatus.RIGHT);
        }
    }
}
