package hardware;

import TI.BoeBot;
import TI.PinMode;
import common.Config;
import enums.Direction;

public class LineFollower {
    private int inputPin;
    private final int upperbound = 2000;
    private final int lowerbound = 500;

    public LineFollower(int inputPin) {
        this.inputPin = inputPin;
        BoeBot.setMode(this.inputPin, PinMode.Input);
    }

    public int getPinNum() {
        return this.inputPin;
    }

    public void setPinMode() {
        BoeBot.setMode(this.inputPin, PinMode.Input);
    }

    public boolean isOnLine(){
        int color = BoeBot.analogRead(inputPin);
        return color > lowerbound && color < upperbound;
    }
}
