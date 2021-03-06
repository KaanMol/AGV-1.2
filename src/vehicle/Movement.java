package vehicle;

import TI.Timer;
import enums.Direction;
import enums.Manoeuvre;
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
    private Manoeuvre manoeuvre = Manoeuvre.NONE;
    private Timer timer;
    private Timer accelerationTimer;
    private boolean isDecelerating = false;
    private boolean isPaused = false;
    private Direction lastDirection = Direction.NEUTRAL;
    private boolean isLookingForBread;

    private int step = 0;

    /**
     * Constructor
     *
     * @param callback - is called to give direction changes
     */
    public Movement(MovementUpdater callback) {
        this.callback = callback;
        this.timer = new Timer(0);
        this.accelerationTimer = new Timer(Config.accelerationSpeedStep);
        this.rightServo = new Motor(Config.rightServoPin);
        this.leftServo = new Motor(Config.leftServoPin);
        this.isLookingForBread = false;
    }

    /**
     * Get the current direction of the vehicle
     *
     * @return direction
     */
    public Direction getHeading() {
        return this.currentHeading;
    }

    /**
     * Sets the direction of the vehicle to forward
     * The vehicle will slowly accelerate
     */
    public void forward() {
        this.setAcceleratingDirection(Direction.FORWARD, 0, 0);
    }

    /**
     * Sets the direction of the vehicle to backward
     * The vehicle will slowly accelerate
     */
    public void backward() {
        this.setAcceleratingDirection(Direction.BACKWARD, 0, 0);
    }

    public void brake() {
        this.setDeceleratingDirection();
    }

    public void pause() {
        if (this.isPaused == true) {
            return;
        }

        this.lastDirection = this.currentHeading;
        this.isPaused = true;


        if (this.currentHeading == Direction.LEFT || this.currentHeading == Direction.RIGHT) {
            this.neutral();
        } else {
            this.setDeceleratingDirection();
        }

    }

    public void play() {
        this.isPaused = false;
        if (this.lastDirection == Direction.LEFT ) {
            this.turnLeft();
        } else if (this.lastDirection == Direction.RIGHT) {
            this.turnRight();
        } else {
            this.setAcceleratingDirection(this.lastDirection, 0, 0);
        }

    }

    /**
     * Sets the direction of the vehicle to right
     */
    public void turnRight() {
        this.setDirection(Direction.RIGHT, 20, 20);
    }

    /**
     * Sets the direction of the vehicle to left
     */
    public void turnLeft() {
        this.setDirection(Direction.LEFT, -20, -20);
    }

    /**
     * Sets the direction of the vehicle to neutral
     * In this case the vehicle stops
     */
    public void neutral() {
        this.rightServo.setSpeed(1500);
        this.leftServo.setSpeed(1500);
        this.setDirection(Direction.NEUTRAL, 0, 0);
    }

//    public void setLookingForBread(boolean isLookingForBread) {
//        this.isLookingForBread = isLookingForBread;
//    }

    private void setDeceleratingDirection() {
        this.isDecelerating = true;
        this.accelerationTimer.mark();
    }

    /**
     * Sets the direction of the vehicle for a given direction and base speeds and slowly accelerates the action
     *
     * @param direction  - The direction the vehicle should be heading to
     * @param leftSpeed  - Left motor speed
     * @param rightSpeed - Right motor speed
     */
    private void setAcceleratingDirection(Direction direction, int leftSpeed, int rightSpeed) {
        this.accelerationTimer.mark();
        this.setDirection(direction, leftSpeed, rightSpeed);
    }

    /**
     * Sets the direction of the vehicle for a given direction and base speeds and instantly accelerates the action
     *
     * @param direction  - The direction the vehicle should be heading to
     * @param leftSpeed  - Left motor speed
     * @param rightSpeed - Right motor speed
     */
    private void setDirection(Direction direction, int leftSpeed, int rightSpeed) {
        if (this.currentHeading == direction) {
            return;
        }

        this.currentHeading = direction;
        this.leftMotorSpeed = leftSpeed;
        this.rightMotorSpeed = rightSpeed;
    }

    /**
     * Set manouvre to right or left
     *
     * @param manoeuvre - The side the vehicle should make the manouevre to
     */
    public void setManoeuvre(Manoeuvre manoeuvre) {
        if (this.manoeuvre.equals(manoeuvre)) {
            return;
        }

        this.manoeuvre = manoeuvre;
        this.step = 0;
    }

    /**
     * Handles the manoeuvre per step
     * Sets the timers and executes the steps on time basis.
     */
    private void handleManoeuvre() {
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
                    if (this.manoeuvre == Manoeuvre.RIGHT) {
                        this.turnLeft();
                    } else {
                        this.turnRight();
                    }

                    this.timer.setInterval(1500);
                    break;

                case 3:
                    this.forward();
                    this.manoeuvre = Manoeuvre.NONE;
                    break;
            }

            this.step++;
            this.timer.mark();
        }
    }

    /**
     * Sets motor speed and handles manoeuvre by a timer.
     */
    public void update() {
        if (this.manoeuvre != Manoeuvre.NONE) {
            this.handleManoeuvre();
        }

        if (this.currentHeading == Direction.FORWARD || this.currentHeading == Direction.BACKWARD) {
            if (this.accelerationTimer.timeout()) {
                if (this.leftMotorSpeed < 30 && this.rightMotorSpeed > -30 && this.isDecelerating == false) {
                    this.leftMotorSpeed += Config.accelerationStep;
                    this.rightMotorSpeed -= Config.accelerationStep;
                } else if (this.leftMotorSpeed != 0 && this.rightMotorSpeed != 0 && this.isDecelerating == true) {
                    this.leftMotorSpeed -= Config.accelerationStep;
                    this.rightMotorSpeed += Config.accelerationStep;
                } else if (this.leftMotorSpeed == 0 && this.rightMotorSpeed == 0 && this.isDecelerating == true) {
                    this.currentHeading = Direction.NEUTRAL;
                    this.isDecelerating = false;
                }
            }
        }

//        if(this.isLookingForBread){
//
//        }

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