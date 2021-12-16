package hardware;

import TI.BoeBot;
import TI.PinMode;

public class IRSensor {

    public int getRemoteCode() {
        BoeBot.setMode(7, PinMode.Input);
        int pulseLen = BoeBot.pulseIn(7, false, 6000);

        if (pulseLen > 2000) {
            int lengths[] = new int[12];
            for (int i = 0; i < 12; i++) {
                lengths[i] = BoeBot.pulseIn(7, false, 20000);
            }

            int output = 0;

            for (int i = 0; i < 12; i++) {
                if (lengths[i] > 800) {
                    output = output | (1 << i);
                    System.out.println(output );
                }
            }

            return output;
        } else {
            return -1;
        }
    }
}
