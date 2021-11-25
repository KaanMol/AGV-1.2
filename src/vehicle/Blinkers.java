package vehicle;

import TI.Timer;
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
    private boolean shouldBeOn = false;
    private boolean isOn = false;
    private boolean isInit = true;

    private Direction side = Direction.NEUTRAL;

    public Blinkers() {
        this.topLeft = new Led(Led.topLeft);
        this.topRight = new Led(Led.topRight);
        this.bottomLeft = new Led(Led.bottomLeft);
        this.bottomRight = new Led(Led.bottomRight);

        this.blinkersOff();

        this.timer = new Timer(0);
    }

    public void setBlinker(Direction side) {
        if (this.side == side) return;

        if (side == Direction.NEUTRAL) {
            this.shouldBeOn = false;
            return;
        }

        this.side = side;
        this.isInit = false;
        this.shouldBeOn = true;

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
        if (this.shouldBeOn == false) return;

        if (this.isInit == false) {
            this.timer.setInterval(750);
            this.isInit = true;
        }

        if (this.timer.timeout()) {
            if (this.isOn) {
                this.blinkersOff();
            } else {
                System.out.println(this.side);
                if (this.side == Direction.LEFT) {
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
