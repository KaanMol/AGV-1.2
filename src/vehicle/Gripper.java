package vehicle;

import TI.Timer;
import common.Config;
import enums.Direction;
import enums.Route;
import interfaces.Updatable;

public class Gripper implements Updatable {
    private hardware.Gripper gripper;
    private Timer timer;
    boolean isClosing = false;
    int currentSpeed = 1750;
    private Movement movement;

    public Gripper(Movement movement) {
        this.movement = movement;
        this.gripper = new hardware.Gripper(Config.gripperPin);
        this.timer = new Timer(25);
    }

    public hardware.Gripper getGripper(){
        return this.gripper;
    }

    public void toggle() {
        this.isClosing = !this.isClosing;
    }

    public boolean gripperStatus() {
        return this.isClosing;
    }

    public boolean isGripperClosed(){
        if(this.currentSpeed <= 1150){
            return true;
        }
        else {
            return false;
        }
    }

    public void update() {
        if (this.timer.timeout() == false) {
            return;
        }

        if (this.isClosing == true) {
            if(this.currentSpeed > 1150) {
                this.currentSpeed -= 10;
            }
//            else{
//                if(this.movement.getHeading() == Direction.NEUTRAL)
//                {
//                    this.movement.forward();
//                }
//            }
        } else if (this.isClosing == false && this.currentSpeed < 1750) {
            this.currentSpeed += 10;
        }

        this.gripper.grab(this.currentSpeed);
    }
}
