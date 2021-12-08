package vehicle;

import common.Config;
import enums.Direction;
import hardware.IRSensor;
import interfaces.IRSignalMover;
import interfaces.Updatable;
import TI.Timer;

public class Infrared implements Updatable {
    private IRSignalMover callback;
    private Timer timer;
    private IRSensor irSensor;

    public Infrared(IRSignalMover callback){
        this.callback = callback;
        this.initialize();
    }

    public void initialize() {
        irSensor = new IRSensor();
        //timer = new Timer(0);
        //this.timer.setInterval(1);
    }



    public void update() {
        //int signal = irSensor.getRemoteCode();
        this.callback.getOrder(irSensor.getRemoteCode());
    }
}
