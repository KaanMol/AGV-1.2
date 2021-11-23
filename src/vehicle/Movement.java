package vehicle;

import hardware.Motor;
import interfaces.Updatable;
import common.Config;

import java.util.ArrayList;

public class Movement implements interfaces.hardware.Movement, Updatable {

    private Motor rightServo;
    private Motor leftServo;

    public Movement() {
        this.rightServo = new Motor(Config.rightServoPin);
        this.leftServo = new Motor(Config.leftServoPin);
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

    public void turnLeft() {

    }

    public void update() {

    }

//    public getTestResults(ArrayList<String> expected) {
//        return this.
//    }
}
