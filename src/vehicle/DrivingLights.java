package vehicle;

import TI.Timer;
import common.Config;
import enums.Direction;
import hardware.Led;
import java.awt.*;

public class DrivingLights {
    private Led topLeft;
    private Led topRight;
    private Led topMiddle;
    private Led bottomLeft;
    private Led bottomRight;
    private Led bottomMiddle;
    private Direction direction;
    private boolean frontLightsOn;
    private boolean backLightsOn;
    private boolean brakeLightsOn;
    private Timer timer = new Timer(500);
    private boolean isOn = false;

    /**
     * Initializes the LED's and direction.
     */
    public DrivingLights() {
        this.topLeft = new Led(Led.topLeft);
        this.topRight = new Led(Led.topRight);
        this.topMiddle = new Led(Led.topMiddle);
        this.bottomLeft = new Led(Led.bottomLeft);
        this.bottomRight = new Led(Led.bottomRight);
        this.bottomMiddle = new Led(Led.bottomMiddle);
        this.direction = Direction.LEFT;
        this.frontLightsOn = false;
        this.backLightsOn = false;
        this.brakeLightsOn = false;
        this.off();
    }

    /**
     * @param direction - The direction that the Bot is currently heading.
     */
    public void start(Direction direction) {
        if (this.direction == direction) {
            return;
        }
        this.direction = direction;
        this.on();
    }

    /**
     * Turns the front or back LED's on in a specific color depending the direction.
     */
    public void on() {
        if (this.direction == Direction.FORWARD) {
            if (this.backLightsOn == true || this.brakeLightsOn == true) {
                this.off();
            }
            topRight.setColor(Color.WHITE);
            topLeft.setColor(Color.WHITE);
            topMiddle.setColor(Color.WHITE);
            this.frontLightsOn = true;
        }
        else if (this.direction == Direction.BACKWARD) {
            if (this.frontLightsOn == true || this.brakeLightsOn == true) {
                this.off();
            }
            bottomRight.setColor(Color.getHSBColor(0, 1, 0.1f));
            bottomLeft.setColor(Color.getHSBColor(0, 1, 0.1f));
            bottomMiddle.setColor(Color.getHSBColor(0, 1, 0.1f));
            this.backLightsOn = true;
        }
        else if (this.direction == Direction.NEUTRAL) {
            if (this.frontLightsOn == true || this.backLightsOn == true) {
                this.off();
            }
            bottomRight.setColor(Color.RED);
            bottomLeft.setColor(Color.RED);
            bottomMiddle.setColor(Color.RED);
            this.brakeLightsOn = true;
        }
    }

    public void lineLights() {

        if (this.isOn == false) {
            topLeft.setColor(Color.red);
            topRight.setColor(Color.red);
            bottomLeft.setColor(Color.red);
            bottomRight.setColor(Color.red);
            this.isOn = !this.isOn;
        }
        if (timer.timeout() && this.isOn == true) {
            topLeft.setColor(Color.black);
            topRight.setColor(Color.black);
            bottomLeft.setColor(Color.black);
            bottomRight.setColor(Color.black);
            this.isOn = !this.isOn;
            this.timer.mark();
        }
    }

    /**
     * Changes the direction attribute back to neutral and calls the off method.
     */
    public void stop() {
        this.direction = Direction.NEUTRAL;
        this.off();
    }

    /**
     * Turns off the LED's.
     */
    private void off() {
        this.topLeft.off();
        this.topRight.off();
        this.topMiddle.off();
        this.bottomLeft.off();
        this.bottomRight.off();
        this.bottomMiddle.off();
        this.brakeLightsOn = false;
        this.backLightsOn = false;
        this.frontLightsOn = false;
    }
}
