package enums;

import common.WirelessConfig;

public enum WirelessCommands {
    LEFT(WirelessConfig.left),
    RIGHT(WirelessConfig.right),
    BACKWARD(WirelessConfig.backward),
    FORWARD(WirelessConfig.forward);

    private final int value;

    WirelessCommands(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
