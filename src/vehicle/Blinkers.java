package vehicle;

import TI.Timer;
import hardware.Led;
import interfaces.Updatable;

import java.awt.*;

public class Blinkers implements Updatable {
    private Led topLeft;
    private Led topRight;
    private Led bottomLeft;
    private Led bottomRight;

    private Timer timer;
    private boolean isOn = false;
    private boolean isInit = true;

    private int side = 1;

    public Blinkers() {
        this.topLeft = new Led(Led.topLeft);
        this.topRight = new Led(Led.topRight);
        this.bottomLeft = new Led(Led.bottomLeft);
        this.bottomRight = new Led(Led.bottomRight);

        this.timer = new Timer(0);
    }

    public void blinkLeft() {
        this.side = 0;
        this.isInit = false;
        this.blinkersOff();
    }

    public void blinkRight() {
        this.side = 2;
        this.isInit = false;
        this.blinkersOff();
    }

    private void blinkersOff() {
        this.topLeft.off();
        this.topRight.off();
        this.bottomLeft.off();
        this.bottomRight.off();
        this.isOn = false;
    }

    public void update() {
        if (this.isInit == false) {
            this.timer.setInterval(750);
            this.isInit = true;
        }

        if (this.timer.timeout()) {
            if (this.isOn) {
                this.blinkersOff();
            } else {
                if (this.side == 0) {
                    this.topLeft.setColor(Color.YELLOW);
                    this.bottomLeft.setColor(Color.YELLOW);
                } else {
                    this.topRight.setColor(Color.YELLOW);
                    this.bottomRight.setColor(Color.YELLOW);
                }
                this.isOn = true;
            }

            this.timer.mark();
        }
    }
}
