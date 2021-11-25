package vehicle;

import TI.BoeBot;
import TI.Timer;
import hardware.Motor;
import interfaces.MovementUpdater;
import interfaces.Updatable;
import common.Config;

import java.util.ArrayList;

public class Movement implements interfaces.hardware.Movement, Updatable {
    public static final int NEUTRAL = 0;
    public static final int FORWARD = 1;
    public static final int RIGHT = 2;
    public static final int BACKWARD = 3;
    public static final int LEFT = 4;

    private Motor rightServo;
    private Motor leftServo;
    private int currentHeading = 0;
    private int leftMotorSpeed = 0;
    private int rightMotorSpeed = 0;
    private MovementUpdater callback;
    private boolean isAccelerating = false;
    private Timer timer;

    public Movement(MovementUpdater callback) {
        this.callback = callback;
        this.timer = new Timer(0);
        this.rightServo = new Motor(Config.rightServoPin);
        this.leftServo = new Motor(Config.leftServoPin);
    }

    public int getHeading() {
        return this.currentHeading;
    }

    public void forward() {
        this.isAccelerating = true;
        this.rightMotorSpeed = 0;
        this.leftMotorSpeed = 0;
        this.timer.setInterval(250);
        this.currentHeading = Movement.FORWARD;
    }

    public void instantForward() {
        this.rightServo.setSpeed(1300);
        this.leftServo.setSpeed(1700);
    }

    public void backwards() {
        this.currentHeading = Movement.BACKWARD;
        this.rightServo.setSpeed(1700);
        this.leftServo.setSpeed(1300);
    }

    public void turnRight() {
        //System.out.println("working");
        this.currentHeading = Movement.RIGHT;
        this.leftServo.setSpeed(1600);
        this.rightServo.setSpeed(1600);
    }

    public void turnLeft() {
        this.currentHeading = Movement.BACKWARD;
        this.leftServo.setSpeed(1400);
        this.rightServo.setSpeed(1400);
    }

    public void passingObstacleRight(int bs) {


        timer.setInterval(2000);

        if(bs == 0 || bs==2)
        {

            this.rightServo.setSpeed(1500);
            this.leftServo.setSpeed(1500);
             BoeBot.wait(500);

            while(!timer.timeout()){
                this.backwards();

            }


                this.rightServo.setSpeed(1500);
                this.leftServo.setSpeed(1500);
                BoeBot.wait(500);

                timer.setInterval(550);
                while(!timer.timeout()){
                    this.turnRight();
                }
            this.rightServo.setSpeed(1500);
            this.leftServo.setSpeed(1500);
            timer.setInterval(2000);
                BoeBot.wait(500);


                while(!timer.timeout()){
                    System.out.println("h");
                    this.instantForward();
            }
            this.rightServo.setSpeed(1500);
            this.leftServo.setSpeed(1500);
            BoeBot.wait(500);
            timer.setInterval(550);
            while(!timer.timeout()){
                this.turnLeft();
            }
            this.rightServo.setSpeed(1500);
            this.leftServo.setSpeed(1500);
            BoeBot.wait(500);
           this.forward();



//            this.forward();
//            this.turnLeft();
//            this.forward();
//            this.turnLeft();
//            this.forward();
//            this.turnRight();
        }
        else if(bs==1){
            this.rightServo.setSpeed(1500);
            this.leftServo.setSpeed(1500);
            BoeBot.wait(500);

            while(!timer.timeout()){
                this.backwards();

            }


            this.rightServo.setSpeed(1500);
            this.leftServo.setSpeed(1500);
            BoeBot.wait(500);

            timer.setInterval(550);
            while(!timer.timeout()){
                this.turnLeft();
            }
            this.rightServo.setSpeed(1500);
            this.leftServo.setSpeed(1500);
            timer.setInterval(2000);
            BoeBot.wait(500);


            while(!timer.timeout()){
                this.instantForward();
            }
            this.rightServo.setSpeed(1500);
            this.leftServo.setSpeed(1500);
            BoeBot.wait(500);
            timer.setInterval(550);
            while(!timer.timeout()){
                this.turnRight();
            }
            this.rightServo.setSpeed(1500);
            this.leftServo.setSpeed(1500);
            BoeBot.wait(500);
            this.forward();

        }


    }

    public void update() {
        if (this.timer.timeout() == false) {
            return;
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
            System.out.println(this.leftMotorSpeed);
            if (this.leftMotorSpeed < 200 && this.rightMotorSpeed > -200) {
                this.leftMotorSpeed += 10;
                this.rightMotorSpeed -= 10;
            } else {
                this.isAccelerating = false;
            }




            this.rightServo.setSpeed(1500 + this.rightMotorSpeed);
            this.leftServo.setSpeed(1500 + this.leftMotorSpeed);
        }

        this.timer.mark();
    }
}
