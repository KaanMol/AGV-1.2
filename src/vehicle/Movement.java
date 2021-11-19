package vehicle;

import hardware.Servo;
import interfaces.Updatable;
import common.Config;

public class Movement implements Updatable {

    private Servo rightServo;
    private Servo leftServo;

    public Movement() {
        this.rightServo = new Servo(Config.rightServoPin);
        this.leftServo = new Servo(Config.leftServoPin);
    }

    public void forward() {
        this.rightServo.setSpeed(1300);
        this.leftServo.setSpeed(1700);
    }

    public void backwards() {
        this.leftServo.setSpeed(1300);
        this.rightServo.setSpeed(1700);
    }

    public void turnRight() {
        this.leftServo.setSpeed(1300);
        this.rightServo.setSpeed(1700);
    }

    public void update() {

    }

}
