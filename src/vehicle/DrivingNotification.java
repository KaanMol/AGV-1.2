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
    private boolean shouldBeOn = false;
    private boolean isSet = false;

    public DrivingNotification(){
        this.callback = callback;
        this.buzzer = new Buzzer(Config.buzzerPin, 2000, 10);

    }

    public void setBuzzer(){
        shouldBeOn = true;
    }

    public void update(){
        if(shouldBeOn)
        {
            this.buzzerOn();
            if(timer.timeout())
            {
                shouldBeOn = false;
            }
        }
        else if(!isSet){
            timer.setInterval(250);
        }
    }

    public void buzzerOn(){
        this.buzzer.start();
    }

}
