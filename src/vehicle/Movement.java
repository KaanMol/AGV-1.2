package vehicle;

import TI.BoeBot;
import TI.Timer;
import enums.Direction;
import hardware.Motor;
import interfaces.MovementUpdater;
import interfaces.Updatable;
import common.Config;

public class Movement implements interfaces.hardware.Movement, Updatable {
    private Motor rightServo;
    private Motor leftServo;
    private Direction currentHeading = Direction.NEUTRAL;
    private int leftMotorSpeed = 0;
    private int rightMotorSpeed = 0;
    private MovementUpdater callback;
//    private boolean isAccelerating = false;
    private String manoeuvre = "NONE";
    private Timer timer;
    private Timer accelerationTimer;

    private boolean directionChanged = false;

    private int step = 0;

    public Movement(MovementUpdater callback) {
        this.callback = callback;
        this.timer = new Timer(0);
        this.accelerationTimer = new Timer(Config.accelerationSpeedStep);
        this.rightServo = new Motor(Config.rightServoPin);
        this.leftServo = new Motor(Config.leftServoPin);
    }

    public Direction getHeading() {
        return this.currentHeading;
    }


    public void forward() {
        this.setAcceleratingDirection(Direction.FORWARD, 0, 0);
    }

    public void backward() {
        this.setAcceleratingDirection(Direction.BACKWARD, 0, 0);
    }

    public void turnRight() {
//        this.isAccelerating = false;
        this.setDirection(Direction.RIGHT, 25, 25);
    }

    public void turnLeft() {
//        this.isAccelerating = false;
        this.setDirection(Direction.LEFT, -25, -25);
    }

    public void neutral() {
        this.leftServo.setSpeed(1500);
        this.rightServo.setSpeed(1500);
        this.setDirection(Direction.NEUTRAL, 0, 0);
    }

    public void setAcceleratingDirection(Direction direction, int leftSpeed, int rightSpeed) {
//        this.isAccelerating = true;
        this.accelerationTimer.mark();
        this.setDirection(direction, leftSpeed, rightSpeed);
    }

    public void setDirection(Direction direction, int leftSpeed, int rightSpeed) {
        if (this.currentHeading == direction) {
            return;
        }

        this.directionChanged = true;
        this.currentHeading = direction;
        this.leftMotorSpeed = leftSpeed;
        this.rightMotorSpeed = rightSpeed;
    }

    public void setManoeuvre(String manoeuvre) {
        if (this.manoeuvre.equals(manoeuvre)) return;

        this.manoeuvre = manoeuvre;
        this.step = 0;
    }

    public void manoeuvreExecute() {
        if (this.step == 0) {
            this.timer.setInterval(2000);
            this.neutral();
            this.step++;
        }

        if (this.timer.timeout()) {
            switch (this.step) {
                case 1:
                    this.backward();
                    this.timer.setInterval(2000);
                    break;

                case 2:
                    if (this.manoeuvre.equals("RIGHT")) {
                        this.turnLeft();
                    } else {
                        this.turnRight();
                    }

                    this.timer.setInterval(1500);
                    break;

                case 3:
                    this.forward();
                    this.manoeuvre = "NONE";
                    break;
            }

            this.step++;
            this.timer.mark();
            System.out.println(this.currentHeading);
        }
    }

    public void update() {

        if (this.manoeuvre.equals("NONE") == false) {
            this.manoeuvreExecute();
        }

        if (this.currentHeading == Direction.FORWARD || this.currentHeading == Direction.BACKWARD) {
            if (this.accelerationTimer.timeout()) {

                if (this.leftMotorSpeed < 100 && this.rightMotorSpeed > -100) {
                    this.leftMotorSpeed += Config.accelerationStep;
                    this.rightMotorSpeed -= Config.accelerationStep;
//                    System.out.println(this.rightMotorSpeed);
//                    System.out.println(this.leftMotorSpeed);
                }
            }
        }

        if (this.getHeading() == Direction.BACKWARD) {

            this.rightServo.setSpeed(1500 + (this.rightMotorSpeed * -1));
            this.leftServo.setSpeed(1500 + (this.leftMotorSpeed * -1));
        } else {
            this.rightServo.setSpeed(1500 + this.rightMotorSpeed);
            this.leftServo.setSpeed(1500 + this.leftMotorSpeed);
        }

        this.callback.onMovementUpdate(this.getHeading());
    }
}
