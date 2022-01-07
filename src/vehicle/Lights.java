package vehicle;

import TI.Timer;
import enums.Direction;
import hardware.Led;
import interfaces.Updatable;
import java.awt.*;

public class Lights implements Updatable {
    private Led topLeft;
    private Led topMiddle;
    private Led topRight;
    private Led bottomLeft;
    private Led bottomMiddle;
    private Led bottomRight;

    private Timer timer;
    private boolean isOn;
    private boolean isSet;

    private Direction blinkDirection;

    /**
     * Constructor
     */
    public Lights() {
        this.initialize();
    }

    /**
     * Initialize all attributes in class and sets defaults
     */
    public void initialize() {
        this.topLeft = new Led(Led.topLeft);
        this.topMiddle = new Led(Led.topMiddle);
        this.topRight = new Led(Led.topRight);
        this.bottomLeft = new Led(Led.bottomLeft);
        this.bottomMiddle = new Led(Led.bottomMiddle);
        this.bottomRight = new Led(Led.bottomRight);

        this.timer = new Timer(0);
        this.isOn = false;
        this.isSet = false;

        this.blinkDirection = configuration.Movement.DEFAULT_DIRECTION;

        this.off();
    }

    /**
     * Starts the blinker cycle
     * @param direction - The direction the blinkers should turn on
     */
    public void blink(Direction direction) {
        if (this.blinkDirection == direction) {
            return;
        }

        this.blinkDirection = direction;
        this.isOn = true;
        this.isSet = true;
        this.timer.setInterval(500);

        this.on();
    }

    public void emergencyLights() {
        this.topLeft.setColor(Color.RED);
        this.topMiddle.setColor(Color.RED);
        this.topRight.setColor(Color.RED);
        this.bottomLeft.setColor(Color.RED);
        this.topMiddle.setColor(Color.RED);
        this.topRight.setColor(Color.RED);

        this.isOn = true;
        this.isSet = true;
        this.timer.setInterval(500);
    }

    public void drivingLights() {
        this.off();

        this.topLeft.setColor(Color.WHITE);
        this.topMiddle.setColor(Color.WHITE);
        this.topRight.setColor(Color.WHITE);
        this.bottomLeft.setColor(Color.RED);
        this.topMiddle.setColor(Color.RED);
        this.topRight.setColor(Color.RED);
    }

    /**
     * Stops blinker cycle with timer
     */
    public void stop() {
        if(this.isSet == false) {
            return;
        }
        this.isOn = false;
        this.isSet = false;
        this.blinkDirection = Direction.NEUTRAL;
        this.off();
    }

    /**
     * Turns on the specified side of the blinkers.
     * This uses the attribute direction
     */
    private void on() {
        if (this.blinkDirection == Direction.LEFT) {
            this.topLeft.setColor(Color.YELLOW);
            this.bottomLeft.setColor(Color.YELLOW);
        } else {
            this.topRight.setColor(Color.YELLOW);
            this.bottomRight.setColor(Color.YELLOW);
        }
    }

    /**
     * Turns off all the blinkers
     */
    private void off() {
        this.topLeft.off();
        this.topRight.off();
        this.bottomLeft.off();
        this.bottomRight.off();
    }

    /**
     * Turns blinkers on and off on a timed interval in the case this keep getting called
     */
    public void update() {
        if (this.timer.timeout() == false || this.isSet == false) {
            return;
        }

        if (this.isOn == false) {
            this.on();
        } else {
            this.off();
        }
        this.isOn = !this.isOn;
        this.timer.mark();
    }
}
