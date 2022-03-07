package interfaces;

import enums.Direction;

public interface MovementUpdater {
    void onMovementUpdate(Direction heading);
}