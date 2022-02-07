package hardware;

import TI.BoeBot;
import TI.PinMode;
import common.Config;


public class  UltraSonic {
    private int inputPin;
    private int outputPin;

    public UltraSonic(int inputPin, int outputPin) {
        this.outputPin = outputPin;
        this.inputPin = inputPin;

        BoeBot.setMode(this.inputPin, PinMode.Input);
        BoeBot.setMode(this.outputPin, PinMode.Output);
    }

    /**
     *
     * @return double in centimeters
     */
    public float getDistance() {
        BoeBot.digitalWrite(outputPin, true);
        BoeBot.wait(1);
        BoeBot.digitalWrite(outputPin, false);
        int pulse = BoeBot.pulseIn(inputPin, true, 10000);

        return (pulse / 29) / 2;
    }
}
