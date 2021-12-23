package vehicle;

import TI.Timer;
import common.Config;
import hardware.UltraSonic;
import interfaces.DistanceDetectionUpdater;
import interfaces.Updatable;

public class DistanceDetection implements Updatable {
    private DistanceDetectionUpdater callback;
    private UltraSonic ultraSonic;
    private Timer timer;

    public DistanceDetection(DistanceDetectionUpdater callback) {
        this.callback = callback;
        this.timer = new Timer(250);
        this.ultraSonic = new UltraSonic(Config.UltraSonicInputPin, Config.UltraSonicOutputPin);
    }

    public void update() {
        if (this.timer.timeout() == false) {
            return;
        }

        int distance = ultraSonic.Readings();
        boolean hasObstacle = false;
        if (distance <= 15 && distance > 1) {
            hasObstacle = true;
        }

        this.callback.onDistanceDetectionUpdate(hasObstacle);

        this.timer.mark();
    }
}
