package hardware;

import TI.BoeBot;
import TI.PinMode;

public class Button {

    private int inputPin;

    /**
     *
     * @param inputPin The pin that the button is connected to
     */
    public Button(int inputPin) {
        this.inputPin = inputPin;
        BoeBot.setMode(this.inputPin, PinMode.Input);
    }

    /**
     * Checks if button is pressed
     * @return state of parameter
     */
    public boolean isPressed() {
        return !BoeBot.digitalRead(inputPin);
    }
}
