package vehicle;

import common.Config;
import hardware.UltraSonic;
import interfaces.DistanceDetectionUpdater;
import interfaces.Updatable;

public class DistanceDetection implements Updatable {
    private DistanceDetectionUpdater callback;
    private UltraSonic ultraSonic;

    public DistanceDetection(DistanceDetectionUpdater callback) {
        this.callback = callback;
        this.ultraSonic = new UltraSonic(Config.UltraSonicInputPin, Config.UltraSonicOutputPin);
    }

    public void update() {
        int distance = ultraSonic.Readings();
        if(distance <= 750){
            callback.onDistanceDetectionUpdate();
        }
    }
}
