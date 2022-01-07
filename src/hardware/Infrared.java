package hardware;

import TI.BoeBot;
import TI.PinMode;
import configuration.Config;

public class Infrared {
    private int infraredPin;

    public Infrared (int infraredPin) {
        this.infraredPin = infraredPin;
        BoeBot.setMode(this.infraredPin, PinMode.Input);
    }

    public int getRemoteCode() {

        int pulseLen = BoeBot.pulseIn(this.infraredPin, false, 6000);

        if (pulseLen > 2000) {
            int lengths[] = new int[12];
            for (int i = 0; i < 12; i++) {
                lengths[i] = BoeBot.pulseIn(this.infraredPin, false, 20000);
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
