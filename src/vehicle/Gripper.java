package vehicle;

import TI.Timer;
import common.Config;
import interfaces.Updatable;

public class Gripper implements Updatable {
    private hardware.Gripper gripper;
    private Timer timer;
    boolean isClosing = false;
    int currentSpeed = 1750;

    public Gripper() {
        this.gripper = new hardware.Gripper(Config.gripperPin);
        this.timer = new Timer(1000);
    }

    public void open() {
        this.isClosing = false;
    }

    public void close() {
        this.isClosing = true;
    }

    public void update() {
        if (this.timer.timeout() == false) {
            return;
        }

        if (this.isClosing == true && this.currentSpeed > 1150) {
            this.currentSpeed -= 10;
        } else if (this.isClosing == false && this.currentSpeed < 1750) {
            this.currentSpeed += 10;
        }

        this.gripper.grab(this.currentSpeed);
    }
}
