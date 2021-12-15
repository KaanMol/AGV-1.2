package hardware;

import TI.BoeBot;
import TI.PinMode;
import common.Config;
import enums.Direction;

public class LineFollower {
    private int inputPin;
    private final int bound = 800;
    private boolean on = true;

    /**
     *
     * @param inputPin The pin that the LineFollower is connected to
     */
    public LineFollower(int inputPin) {
        this.inputPin = inputPin;
        BoeBot.setMode(this.inputPin, PinMode.Input);
    }

    /**
     * Checks if LineFollower is on line
     * @return state of parameter
     */
    public boolean isOnLine(){
        int color = BoeBot.analogRead(inputPin);
        return color > bound;
    }
}
