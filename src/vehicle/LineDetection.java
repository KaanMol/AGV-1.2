package vehicle;

import TI.Timer;
import enums.Direction;
import enums.LineDirection;
import hardware.LineFollower;
import interfaces.LineDetectionUpdater;
import interfaces.Updatable;
import common.Config;

import javax.sound.sampled.Line;

public class LineDetection implements Updatable {

    private LineDetectionUpdater callback;
    private LineFollower leftLineFollower;
    private LineFollower middleLineFollower;
    private LineFollower rightLineFollower;
    private Timer timer = new Timer(250);

    /**
     * Constructor
     * @param callback - Is used to send the state of the linefollowers to
     */
    public LineDetection(LineDetectionUpdater callback) {
        this.callback = callback;
        this.leftLineFollower = new LineFollower(Config.leftLineFollowerPin) ;
        this.middleLineFollower = new LineFollower(Config.middleLineFollowerPin);
        this.rightLineFollower = new LineFollower(Config.rightLineFollowerPin);
        this.timer.mark();
    }

    /**
     * Checks which linefollowers are on line, and calls the callback attribute with the status.
     */
    public void update() {
        if (this.timer.timeout() == false) {
            return;
        }
        if (!middleLineFollower.isOnLine()) {
            this.callback.onLineDetectionUpdate(LineDirection.STOP);
        } else if (this.leftLineFollower.isOnLine() && !this.rightLineFollower.isOnLine()) {
            this.callback.onLineDetectionUpdate(LineDirection.LEFT);
        } else if (!leftLineFollower.isOnLine() && this.rightLineFollower.isOnLine()) {
            this.callback.onLineDetectionUpdate(LineDirection.RIGHT);
        } else {
            this.callback.onLineDetectionUpdate(LineDirection.FORWARD);
        }
        timer.mark();

    }
}
