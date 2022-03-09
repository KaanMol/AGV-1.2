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
    private boolean isLookingForBread;

    public DistanceDetection(DistanceDetectionUpdater callback) {
        this.callback = callback;
        this.timer = new Timer(250);
        this.bottomUltraSonic = new UltraSonic(Config.bottomUltraSonicInputPin, Config.bottomUltraSonicOutputPin);
        this.topUltraSonic = new UltraSonic(Config.topUltraSonicInputPin, Config.topUltraSonicOutputPin);

    }

    public UltraSonic getBottomUltraSonic(){
        return this.bottomUltraSonic;
    }

    public UltraSonic getTopUltraSonic(){
        return this.topUltraSonic;
    }

    public void update() {
        if (this.timer.timeout() == false) {
            return;
        }

        final double bottomDistance = bottomUltraSonic.getDistance();
        final double topDistance = topUltraSonic.getDistance();

        HashMap<Ultrasonic, Double> hasObstacle = new HashMap<>();
        hasObstacle.put(Ultrasonic.BOTTOM, bottomDistance);
        hasObstacle.put(Ultrasonic.TOP, topDistance);

//        if(this.isLookingForBread){
//            if(bottomDistance < 20) {
//                System.out.println("Bread Found");
//
//            }
//        }

        if(this.bottomUltraSonic.getDistance() < 10){
            bottomObstacle = true;
        }

        this.timer.mark();
        this.callback.onDistanceDetectionUpdate(hasObstacle);
        //this.callback.onDistanceDetectionUpdate(bottomObstacle);
    }
}
