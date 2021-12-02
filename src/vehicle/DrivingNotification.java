package vehicle;

import TI.Timer;
import common.Config;
import hardware.Buzzer;
import interfaces.Updatable;

public class DrivingNotification implements Updatable {
    private Timer timer;

    private boolean isOn;
    private boolean isSet;

    private Buzzer buzzer;

    /**
     * Constructor
     */
    public DrivingNotification() {
        this.initialize();
    }

    /**
     * Initializes all attributes and sets defaults
     */
    public void initialize() {
        this.isOn = false;
        this.isSet = false;
        this.timer = new Timer(0);
        this.buzzer = new Buzzer(Config.buzzerPin);
    }

    /**
     * Starts buzzer cycle
     * Sets all values so the update method will turn the buzzer on and off
     */
    public void start() {
        if (this.isSet == true) {
            return;
        }

        this.timer.setInterval(Config.buzzerDelay);
        this.isSet = true;
        this.isOn = true;
        this.buzzer.start();
    }

    /**
     * Stops buzzer cycle
     * Sets values so update wont keep turning the buzzer on
     */
    public void stop() {
        this.isSet = false;
        this.isOn = false;
        this.buzzer.stop();
    }

    /**
     * Turns buzzer on and off for a given interval in the case this method keeps getting called
     */
    public void update() {
        if (this.timer.timeout() == false || this.isSet == false) {
            return;
        }

        if (this.isOn == false) {
            this.buzzer.start();
        } else {
            this.buzzer.stop();
        }
        
        this.isOn = !this.isOn;
        this.timer.mark();
    }
}
