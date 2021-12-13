package interfaces;

import enums.LineDirection;

public interface LineDetectionUpdater {
    void onLineDetectionUpdate(LineDirection lineDirection);
}
