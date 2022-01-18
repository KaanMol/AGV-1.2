package vehicle;

import TI.Timer;
import common.Config;
import enums.Ultrasonic;
import hardware.UltraSonic;
import interfaces.DistanceDetectionUpdater;
import interfaces.Updatable;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class DistanceDetection implements Updatable {
    private DistanceDetectionUpdater callback;
    private UltraSonic bottomUltraSonic;
    private UltraSonic topUltraSonic;
    private Timer timer;
    private Pair<Ultrasonic, Boolean> bottom;
    private Pair<Ultrasonic, Boolean> top;
    private boolean bottomObstacle = false;
    private boolean topObstacle = false;

    public DistanceDetection(DistanceDetectionUpdater callback) {
        this.callback = callback;
        this.timer = new Timer(250);
        this.bottomUltraSonic = new UltraSonic(Config.bottomUltraSonicInputPin, Config.bottomUltraSonicOutputPin);
        this.topUltraSonic = new UltraSonic(Config.topUltraSonicInputPin, Config.topUltraSonicOutputPin);

    }

    public void update() {
        if (this.timer.timeout() == false) {
            return;
        }
        HashMap<Ultrasonic, Boolean> hasObstacle = new HashMap<>();
        hasObstacle.put(Ultrasonic.BOTTOM, bottomUltraSonic.Readings() <= 750 && bottomUltraSonic.Readings() > 150);
        hasObstacle.put(Ultrasonic.TOP, topUltraSonic.Readings() <= 750 && topUltraSonic.Readings() > 150);
        this.callback.onDistanceDetectionUpdate(hasObstacle);


        this.timer.mark();
    }
}
