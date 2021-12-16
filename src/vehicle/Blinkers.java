package vehicle;

import TI.Timer;
import common.Config;
import enums.Direction;
import hardware.Led;
import interfaces.Updatable;
import java.awt.*;

public class Blinkers implements Updatable {
    private Led topLeft;
    private Led topRight;
    private Led bottomLeft;
    private Led bottomRight;

    private Timer timer;
    private boolean isOn;
    private boolean isSet;

    private Direction direction;

    /**
     * Constructor
     */
    public Blinkers() {
        this.initialize();
    }

    /**
     * Initialize all attributes in class and sets defaults
     */
    public void initialize() {
        this.topLeft = new Led(Led.topLeft);
        this.topRight = new Led(Led.topRight);
        this.bottomLeft = new Led(Led.bottomLeft);
        this.bottomRight = new Led(Led.bottomRight);

        this.timer = new Timer(0);
        this.isOn = false;
        this.isSet = false;

        this.direction = Config.defaultDirection;

        this.off();
    }

    /**
     * Starts the blinker cycle
     * @param direction - The direction the blinkers should turn on
     */
    public void start(Direction direction) {
        if (this.direction == direction) {
            return;
        }

        this.direction = direction;
        this.isOn = true;
        this.isSet = true;
        this.timer.setInterval(500);

        this.on();
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
        this.direction = Direction.NEUTRAL;
        this.off();
    }

    /**
     * Turns on the specified side of the blinkers.
     * This uses the attribute direction
     */
    private void on() {
        if (this.direction == Direction.LEFT) {
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
