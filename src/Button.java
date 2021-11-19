import TI.BoeBot;
import TI.PinMode;

public class Button {

    private int inputPin;


    public Button(int inputPin) {
        this.inputPin = inputPin;
        BoeBot.setMode(this.inputPin, PinMode.Input);
    }

    /**
     * checks if button is pressed
     */
    public boolean isPressed() {
        return !BoeBot.digitalRead(inputPin);
    }

    /**
     * method for interface in the future
     */
    public void update(){

    }
}
