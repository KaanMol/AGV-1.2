package vehicle;

import TI.Timer;
import enums.LineDirection;
import hardware.LineFollower;
import interfaces.LineDetectionUpdater;
import interfaces.Updatable;
import common.Config;

public class LineDetection implements Updatable {

    private LineDetectionUpdater callback;
    private LineFollower leftLineFollower;
    private LineFollower middleLineFollower;
    private LineFollower rightLineFollower;

    /**
     * Constructor
     * @param callback - Is used to send the state of the linefollowers to
     */
    public LineDetection(LineDetectionUpdater callback) {
        this.callback = callback;
        this.leftLineFollower = new LineFollower(Config.leftLineFollowerPin) ;
        this.middleLineFollower = new LineFollower(Config.middleLineFollowerPin);
        this.rightLineFollower = new LineFollower(Config.rightLineFollowerPin);
    }

    /**
     * Checks which linefollowers are on line, and calls the callback attribute with the status.
     */
    public void update() {
        if (middleLineFollower.isOnLine() == false
                && leftLineFollower.isOnLine() == false
                && rightLineFollower.isOnLine() == false) {
            this.callback.onLineDetectionUpdate(LineDirection.STOP);
        } else if (this.leftLineFollower.isOnLine() == true
                && this.rightLineFollower.isOnLine() == false) {
            this.callback.onLineDetectionUpdate(LineDirection.LEFT);
        } else if (!leftLineFollower.isOnLine() == false
                && this.rightLineFollower.isOnLine() == true) {
            this.callback.onLineDetectionUpdate(LineDirection.RIGHT);
        } else {
            this.callback.onLineDetectionUpdate(LineDirection.FORWARD);
        }
    }
}
