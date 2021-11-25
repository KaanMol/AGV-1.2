package vehicle;

import common.Config;
import hardware.Buzzer;
import interfaces.DrivingNotificationUpdater;
import interfaces.Updatable;

public class DrivingNotification implements Updatable {
    private Buzzer buzzer;
    private DrivingNotificationUpdater callback;

    public DrivingNotification(DrivingNotificationUpdater callback){
        this.callback = callback;
        this.buzzer = new Buzzer(Config.buzzerPin, 500, 500);

    }

    public void update(){
        if(this.callback.getHeading() == 3){
            System.out.println("heading is backwards");
            this.callback.buzzerUpdate();
        }
    }

    public void buzzerOn(){
        System.out.println("buzzerOn");
        this.buzzer.start();
    }

}
