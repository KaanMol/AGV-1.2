package interfaces;

import enums.Ultrasonic;
import hardware.UltraSonic;
import javafx.util.Pair;

import java.util.HashMap;

public interface DistanceDetectionUpdater {
    void onDistanceDetectionUpdate(HashMap<Ultrasonic, Double> hasObstacle);
    //void onDistanceDetectionUpdate(boolean hasObstacle);
}
