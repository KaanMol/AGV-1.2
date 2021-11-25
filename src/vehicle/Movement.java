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
    private boolean isAccelerating = false;
    private String manoeuvre = "NONE";
    private Timer timer;

    private boolean directionChanged = false;
    private int step = 0;

    //0 = stop
    //500 = backwards
    //2000 = turn right/left
    //2550 = forward
    //4550 = turn left/right
    //3550 = forward
    public Movement(MovementUpdater callback) {
        this.callback = callback;
        this.timer = new Timer(0);
        this.rightServo = new Motor(Config.rightServoPin);
        this.leftServo = new Motor(Config.leftServoPin);
    }

    public Direction getHeading() {
        return this.currentHeading;
    }

    public void instantForward() {
        this.setDirection(Direction.FORWARD, 200, -200);
        this.timer.setInterval(250);
    }

    public void forward() {
        this.setAcceleratingDirection(Direction.FORWARD, 0, 0);
    }

    public void backwards() {
        this.setDirection(Direction.BACKWARD, 200, -200);
    }

    public void turnRight() {
        this.setDirection(Direction.RIGHT, 200, -200);
    }

    public void turnLeft() {
        this.setDirection(Direction.LEFT, -100, -100);
    }

    public void neutral() {
        this.leftServo.setSpeed(1500);
        this.rightServo.setSpeed(1500);
        this.setDirection(Direction.NEUTRAL, 0, 0);
    }

    public void setAcceleratingDirection(Direction direction, int leftSpeed, int rightSpeed) {
        this.isAccelerating = true;
        this.timer.setInterval(Config.accelerationSpeedStep);
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
//        System.out.println("EXEC " + this.manoeuvre);
        //0-500 = stop
        //500-2000 = backwards
        //2000-2550 = turn right/left
        //2550 = forward
        //4550 = turn left/right
        //3550 = forward
        if (this.step == 0) {
            this.timer.setInterval(500);
            this.neutral();
            this.step++;
        }

        if (this.timer.timeout()) {
            switch (this.step) {
                case 1:
                    this.backwards();
                    this.timer.setInterval(1500);
                    break;

                case 2:
                    if (this.manoeuvre.equals("RIGHT")) {
                        this.turnLeft();
                    } else {
                        this.turnRight();
                    }

                    this.timer.setInterval(550);
                    break;

                case 3:
                    this.forward();
                    this.timer.setInterval(2000);
                    break;
                case 4:
                    if (this.manoeuvre.equals("RIGHT")) {
                        this.turnRight();
                    } else {
                        this.turnLeft();
                    }
                    this.timer.setInterval(550);
                    break;
                case 5:
                    this.forward();
                    this.manoeuvre = "NONE";
            }

            this.step++;
            System.out.println(this.currentHeading);
        }
    }

    public void update() {
        this.callback.onMovementUpdate(this.getHeading());

        if (this.manoeuvre.equals("NONE") == false) {
            this.manoeuvreExecute();
        }

//        if (this.currentHeading == Movement.FORWARD) {
//            this.rightMotorSpeed = -200;
//            this.leftMotorSpeed = 200;
//        } else if (this.currentHeading == Movement.BACKWARD) {
//            this.rightMotorSpeed = 200;
//            this.leftMotorSpeed = -200;
//        } else if (this.currentHeading == Movement.RIGHT) {
//            this.rightMotorSpeed = 100;
//            this.leftMotorSpeed = 100;
//        } else if (this.currentHeading == Movement.LEFT) {
//            this.rightMotorSpeed = -100;
//            this.leftMotorSpeed = -100;
//        } else {
//            this.rightMotorSpeed = 0;
//            this.leftMotorSpeed = 0;
//        }

        if (this.isAccelerating) {
//            System.out.println("Accelerate");
            if (this.timer.timeout()) {
//                System.out.println(this.leftMotorSpeed);
                if (this.leftMotorSpeed < 200 && this.rightMotorSpeed > -200) {
                    this.leftMotorSpeed += Config.accelerationStep;
                    this.rightMotorSpeed -= Config.accelerationStep;
                } else {
                    this.isAccelerating = false;
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


//        this.timer.mark();
    }
}
