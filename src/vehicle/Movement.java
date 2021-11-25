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
    private Timer timer1;
    private Timer timer2;
    private Timer timer3;
    private Timer timer4;
    private Timer timer5;
    private boolean isInit;
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

    public void forwardSlow() {
        this.isAccelerating = true;
        this.rightMotorSpeed = 0;
        this.leftMotorSpeed = 0;
        this.timer.setInterval(250);
        this.currentHeading = Direction.FORWARD;
    }

    public void forward() {
        this.currentHeading = Direction.FORWARD;
        this.rightServo.setSpeed(1300);
        this.leftServo.setSpeed(1700);
    }

    public void backwards() {
        this.currentHeading = Direction.BACKWARD;
        this.rightServo.setSpeed(1700);
        this.leftServo.setSpeed(1300);
    }

    public void turnRight() {
        //System.out.println("working");
        this.currentHeading = Direction.RIGHT;
        this.leftServo.setSpeed(1600);
        this.rightServo.setSpeed(1600);
    }

    public void turnLeft() {
        this.currentHeading = Direction.LEFT;
        this.leftServo.setSpeed(1400);
        this.rightServo.setSpeed(1400);
    }

    public void neutral() {
        this.currentHeading = Direction.NEUTRAL;
        this.leftServo.setSpeed(1500);
        this.rightServo.setSpeed(1500);
    }

    public void setManoeuvre(String manoeuvre) {
        if (this.manoeuvre.equals(manoeuvre)) return;

        this.manoeuvre = manoeuvre;
        this.step = 0;
        this.isInit = false;
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



//        if(bs == 0 || bs==2)
//        {
//
//            this.rightServo.setSpeed(1500);
//            this.leftServo.setSpeed(1500);
//             BoeBot.wait(500);
//
//            while(!timer.timeout()){
//                this.backwards();
//
//            }
//
//
//                this.rightServo.setSpeed(1500);
//                this.leftServo.setSpeed(1500);
//                BoeBot.wait(500);
//
//                timer.setInterval(550);
//                while(!timer.timeout()){
//                    this.turnRight();
//                }
//            this.rightServo.setSpeed(1500);
//            this.leftServo.setSpeed(1500);
//            timer.setInterval(2000);
//                BoeBot.wait(500);
//
//
//                while(!timer.timeout()){
//                    System.out.println("h");
//                    this.instantForward();
//            }
//            this.rightServo.setSpeed(1500);
//            this.leftServo.setSpeed(1500);
//            BoeBot.wait(500);
//            timer.setInterval(550);
//            while(!timer.timeout()){
//                this.turnLeft();
//            }
//            this.rightServo.setSpeed(1500);
//            this.leftServo.setSpeed(1500);
//            BoeBot.wait(500);
//           this.forward();
//
//
//
////            this.forward();
////            this.turnLeft();
////            this.forward();
////            this.turnLeft();
////            this.forward();
////            this.turnRight();
//        }
//        else if(bs==1){
//            this.rightServo.setSpeed(1500);
//            this.leftServo.setSpeed(1500);
//            BoeBot.wait(500);
//
//            while(!timer.timeout()){
//                this.backwards();
//
//            }
//
//
//            this.rightServo.setSpeed(1500);
//            this.leftServo.setSpeed(1500);
//            BoeBot.wait(500);
//
//            timer.setInterval(550);
//            while(!timer.timeout()){
//                this.turnLeft();
//            }
//            this.rightServo.setSpeed(1500);
//            this.leftServo.setSpeed(1500);
//            timer.setInterval(2000);
//            BoeBot.wait(500);
//
//
//            while(!timer.timeout()){
//                this.instantForward();
//            }
//            this.rightServo.setSpeed(1500);
//            this.leftServo.setSpeed(1500);
//            BoeBot.wait(500);
//            timer.setInterval(550);
//            while(!timer.timeout()){
//                this.turnRight();
//            }
//            this.rightServo.setSpeed(1500);
//            this.leftServo.setSpeed(1500);
//            BoeBot.wait(500);
//            this.forward();
//
//        }
//
//
    }

    public void update() {
        if (this.manoeuvre.equals("NONE") == false) {

            this.manoeuvreExecute();

        }


        this.callback.onMovementUpdate(this.getHeading());

//        if (this.timer.timeout() == false) {
//            return;
//        }
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
//        if (this.isAccelerating) {
//            System.out.println(this.leftMotorSpeed);
//            if (this.leftMotorSpeed < 200 && this.rightMotorSpeed > -200) {
//                this.leftMotorSpeed += 10;
//                this.rightMotorSpeed -= 10;
//            } else {
//                this.isAccelerating = false;
//            }
//
//
//
//
//            this.rightServo.setSpeed(1500 + this.rightMotorSpeed);
//            this.leftServo.setSpeed(1500 + this.leftMotorSpeed);
//        }

//        this.timer.mark();
    }
}
