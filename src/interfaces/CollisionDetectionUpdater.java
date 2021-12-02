package interfaces;

import enums.WhiskerStatus;

public interface CollisionDetectionUpdater {
    void onCollisionDetectionUpdate(WhiskerStatus whiskerCollision);
}