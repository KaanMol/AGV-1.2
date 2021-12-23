package vehicle;

import TI.Timer;
import common.Config;
import enums.Direction;
import hardware.Led;
import java.awt.*;

public class DrivingLights {
    private Led topLeft;
    private Led topRight;
    private Led bottomLeft;
    private Led bottomRight;
    private Direction direction;
    private Timer timer = new Timer(500);
    private boolean isOn = false;


    /**
     * Initializes the LED's and direction.
     */
    public DrivingLights() {
        this.topLeft = new Led(Led.topLeft);
        this.topRight = new Led(Led.topRight);
        this.bottomLeft = new Led(Led.bottomLeft);
        this.bottomRight = new Led(Led.bottomRight);
        this.direction = Direction.NEUTRAL;
        this.off();
    }

    /**
     * Returns if the given direction is neutral and otherwise changes the
     * direction attribute in the given direction.
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
            topRight.setColor(Color.WHITE);
            topLeft.setColor(Color.WHITE);
        }
        else if (this.direction == Direction.BACKWARD) {
            bottomRight.setColor(Color.RED);
            bottomLeft.setColor(Color.RED);
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
        this.bottomLeft.off();
        this.bottomRight.off();
    }
}
