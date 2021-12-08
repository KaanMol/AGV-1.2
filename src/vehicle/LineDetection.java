package vehicle;

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

    public LineDetection(LineDetectionUpdater callback) {
        this.callback = callback;
        this.leftLineFollower = new LineFollower(Config.leftLineFollowerPin) ;
        this.middleLineFollower = new LineFollower(Config.middleLineFollowerPin);
        this.rightLineFollower = new LineFollower(Config.rightLineFollowerPin);
    }

    public void update() {
        if (!this.middleLineFollower.isOnLine()) {
            this.callback.onLineDetectionUpdater(LineDirection.STOP);
        } else if (!this.leftLineFollower.isOnLine()) {
            this.callback.onLineDetectionUpdater(LineDirection.RIGHT);
        } else if (!this.rightLineFollower.isOnLine()) {
            this.callback.onLineDetectionUpdater(LineDirection.LEFT);
        } else {
            this.callback.onLineDetectionUpdater(LineDirection.FORWARD);
        }
    }
}
