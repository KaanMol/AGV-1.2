package hardware;

import TI.BoeBot;
import TI.PinMode;
import java.awt.*;

public class Led {

    /**
     * the pins for the NeoPixels
     */
    public static final int bottomRight = 0;
    public static final int bottomMiddle = 1;
    public static final int bottomLeft = 2;
    public static final int topLeft = 3;
    public static final int topMiddle = 4;
    public static final int topRight = 5;

    private int inputPin;

    /**
     * @param inputPin The pin that the NeoPixel is connected to
     */
    public Led(int inputPin) {
        this.inputPin = inputPin;
        BoeBot.setMode(inputPin, PinMode.Output);
    }

    /**
     * sets color for NeoPixel
     * @param color Color for the NeoPixel
     */
    public void setColor(Color color) {
        BoeBot.rgbSet(this.inputPin, color);
        BoeBot.rgbShow();
    }

    /**
     * turns off NeoPixel
     */
    public void off() {
        BoeBot.rgbSet(this.inputPin, Color.black);
        BoeBot.rgbShow();
    }


}
