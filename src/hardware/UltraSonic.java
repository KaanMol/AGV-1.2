package hardware;

import TI.BoeBot;
import TI.PinMode;


public class  UltraSonic {
    private int inputPin;
    private int outputPin;

    public UltraSonic(int inputPin, int outputPin) {
        this.outputPin = outputPin;
        this.inputPin = inputPin;
        BoeBot.setMode(this.inputPin, PinMode.Input);
        BoeBot.setMode(this.outputPin, PinMode.Output);
    }

    public int Readings() {
        BoeBot.digitalWrite(this.outputPin, true);
        BoeBot.digitalWrite(this.outputPin, false);
        int pulse = BoeBot.pulseIn(this.inputPin, true, 10000);
        return pulse;
    }
}
