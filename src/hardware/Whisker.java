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
     * Checks if whisker is pressed
     * @return state of parameter
     */
    public boolean isPressed() {
        return !BoeBot.digitalRead(inputPin);
    }
}