import TI.BoeBot;
import TI.PinMode;
import java.awt.*;

public class Led {

    public static final int bottomRight = 0;
    public static final int bottomMiddle = 1;
    public static final int bottomLeft = 2;
    public static final int topLeft = 3;
    public static final int topMiddle = 4;
    public static final int topRight = 5;

    private int inputPin;

    public Led(int inputPin) {
        this.inputPin = inputPin;
        BoeBot.setMode(inputPin, PinMode.Output);
    }

    public void setColor(Color color) {
        BoeBot.rgbSet(this.inputPin, color);
        BoeBot.rgbShow();
    }

    public void off() {
        BoeBot.rgbSet(this.inputPin, Color.black);
        BoeBot.rgbShow();
    }


}
