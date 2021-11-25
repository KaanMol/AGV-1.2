package vehicle;

import TI.Timer;
import common.Config;
import enums.Direction;
import hardware.Buzzer;
import interfaces.DrivingNotificationUpdater;
import interfaces.Updatable;

public class DrivingNotification implements Updatable {
    private Buzzer buzzer;
    private DrivingNotificationUpdater callback;
    private Timer timer = new Timer(0);
    private boolean isOn = false;
    private boolean isSet = false;

    public DrivingNotification(){
        this.callback = callback;
        this.buzzer = new Buzzer(Config.buzzerPin);

    }

    public void startBuzzer() {
        this.isSet = true;
        this.timer.setInterval(500);
    }

    public void stopBuzzer() {
        this.isSet = false;
        this.buzzer.stop();
    }

    public void update(){
        if (this.timer.timeout() == false) {
            return;
        }

        if (this.isSet == false) return;

        if (isOn) {
            this.buzzer.stop();
        } else {
           this.buzzer.start();
        }

        this.isOn = !this.isOn;
    }

}
