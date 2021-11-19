package hardware;

import TI.BoeBot;
import TI.PinMode;

public class Servo {
    private TI.Servo servo;

    public Servo(int outputPin) {
        this.servo = new TI.Servo(outputPin);
    }

    public void setSpeed(int targetSpeed) {
        this.servo.update(targetSpeed);
    }
}
