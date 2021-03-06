package hardware;

import TI.Servo;

public class Gripper {
    private Servo gripper;

    public Servo getGripper(){
        return this.gripper;
    }

    public Gripper(int outputPin) {
        this.gripper = new Servo(outputPin);
    }

    public void grab(int targetspeed) {
        this.gripper.update(targetspeed);
    }
}
