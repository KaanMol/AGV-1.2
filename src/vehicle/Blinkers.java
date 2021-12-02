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
//    private boolean shouldBeOn = false;
    private boolean isOn;
    private boolean isSet;

    private Direction direction;

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

    public Blinkers() {
        this.initialize();
    }

    public void start(Direction direction) {
        if (this.direction == direction) {
            return;
        }

        this.direction = direction;
        this.isOn = true;
        this.isSet = true;
        this.timer.setInterval(750);

        this.on();
    }

    private void on() {
        if (this.direction == Direction.LEFT) {
            this.topLeft.setColor(Color.YELLOW);
            this.bottomLeft.setColor(Color.YELLOW);
        } else {
            this.topRight.setColor(Color.YELLOW);
            this.bottomRight.setColor(Color.YELLOW);
        }

//        this.isOn = true;
    }

    private void off() {
        this.topLeft.off();
        this.topRight.off();
        this.bottomLeft.off();
        this.bottomRight.off();

    }

    public void stop() {
        this.isOn = false;
        this.isSet = false;
        this.direction = Direction.NEUTRAL;
        this.off();
    }

    public void update() {
        if (this.timer.timeout() == false || this.isSet == false) {
            return;
        }

        System.out.println("Do something!");

        if (this.isOn == false) {
            this.on();
        } else {
            this.off();
        }

        this.isOn = !this.isOn;

        this.timer.mark();
//        if (this.shouldBeOn == false) return;
//
//        if (this.isInit == false) {
//            this.timer.setInterval(750);
//            this.isInit = true;
//            this.blinkersOff();
//        }
//
//        if (this.timer.timeout()) {
//            if (this.isOn) {
//                this.blinkersOff();
//            } else {
//                System.out.println(this.side);
//                if (this.side == Direction.LEFT) {
//                    this.topLeft.setColor(Color.YELLOW);
//                    this.bottomLeft.setColor(Color.YELLOW);
//                } else {
//                    this.topRight.setColor(Color.YELLOW);
//                    this.bottomRight.setColor(Color.YELLOW);
//                }
//
//                this.isOn = true;
//            }
//
//            this.timer.mark();
//        }
    }
}
