import TI.BoeBot;
import TI.PinMode;

public class Button {

    private int inputPin;

    public Button(int inputPin) {
        this.inputPin = inputPin;
        BoeBot.setMode(this.inputPin, PinMode.Input);
    }

    public boolean isPressed() {
        return !BoeBot.digitalRead(inputPin);
    }

    public void update(){

    }
}
