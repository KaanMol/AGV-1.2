package hardware;

import TI.Servo;

public class Motor {
    private Servo motor;

    public Motor(int outputPin) {
        this.motor = new Servo(outputPin);
    }

    public void setSpeed(int targetSpeed) {
//        1500 + targetSpeed * 2
        this.motor.update(targetSpeed);
    }
}
