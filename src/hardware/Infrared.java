package hardware;

import TI.BoeBot;
import TI.PinMode;
import common.WirelessConfig;

public class Infrared {

    public int getRemoteCode() {
        BoeBot.setMode(WirelessConfig.InfraredPin, PinMode.Input);
        int pulseLen = BoeBot.pulseIn(WirelessConfig.InfraredPin, false, 6000);

        if (pulseLen > 2000) {
            int lengths[] = new int[12];
            for (int i = 0; i < 12; i++) {
                lengths[i] = BoeBot.pulseIn(WirelessConfig.InfraredPin, false, 20000);
            }

            int output = 0;

            for (int i = 0; i < 12; i++) {
                if (lengths[i] > 800) {
                    output = output | (1 << i);
                }
            }

            return output;
        } else {
            return -1;
        }
    }
}
