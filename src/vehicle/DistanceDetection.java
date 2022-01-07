package vehicle;

import TI.Timer;
import configuration.Config;
import configuration.DistanceSensor;
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
        this.ultraSonic = new UltraSonic(DistanceSensor.INPUT_PIN, DistanceSensor.OUTPUT_PIN);
    }

    public void update() {
        if (this.timer.timeout() == false) {
            return;
        }

        int distance = ultraSonic.Readings();
        boolean hasObstacle = false;
        if (distance <= 750 && distance > 150) {
            hasObstacle = true;
        }

        this.callback.onDistanceDetectionUpdate(hasObstacle);

        this.timer.mark();
    }
}
