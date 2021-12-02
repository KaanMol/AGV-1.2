package vehicle;

import TI.Timer;
import common.Config;
import enums.Direction;
import hardware.Buzzer;
import hardware.Led;
import interfaces.DrivingNotificationUpdater;
import interfaces.Updatable;

import java.awt.*;

public class DrivingNotification implements Updatable {
    private Timer timer;

    private boolean isOn;
    private boolean isSet;

    private Buzzer buzzer;

    public void initialize() {
        this.isOn = false;
        this.isSet = false;
        this.timer = new Timer(0);
        this.buzzer = new Buzzer(Config.buzzerPin);

//        this.stop();
    }

    public DrivingNotification(){
        this.initialize();
    }

    public void start() {
        if (this.isSet == true) {
            return;
        }

        this.timer.setInterval(Config.buzzerDelay);
        this.isSet = true;
        this.isOn = true;
        this.buzzer.start();
    }

    public void stop() {
        this.isSet = false;
        this.isOn = false;
        this.buzzer.stop();
    }

    public void update(){
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
