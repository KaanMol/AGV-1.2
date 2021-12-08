package interfaces;

import enums.Direction;
import enums.LineDirection;

public interface MovementUpdater {
    void onMovementUpdate(Direction heading);
}