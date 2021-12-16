package hardware;

import TI.BoeBot;
import TI.PinMode;

public class Whisker {

    private int inputPin;
    
    /**
     *
     * @param inputPin The pin that the whisker is connected to
     */
    public Whisker(int inputPin) {
        this.inputPin = inputPin;
        BoeBot.setMode(this.inputPin, PinMode.Input);
    }

    /**
     *
     * @return The pin that the whisker is connected to
     */
    public int getPinNum() {
        return this.inputPin;
    }

    /**
     *
     * @param inputPin The pin that the whisker is connected to
     */
    public void setPinMode(int inputPin) {
        BoeBot.setMode(inputPin, PinMode.Input);
        this.inputPin = inputPin;
    }

    /**
     * Checks if whisker is pressed
     * @return state of parameter
     */
    public boolean isPressed() {
        return !BoeBot.digitalRead(inputPin);
    }
}