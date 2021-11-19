public class Movement {

    private Servo rightServo;
    private Servo leftServo;

    Movement() {
        this.rightServo = new Servo(Config.rightServoPin, false);
        this.leftServo = new Servo(Config.leftServoPin, false);
    }

    public void forward() {
        this.rightServo.setSpeed(1300);
        this.leftServo.setSpeed(1700);
        this.leftServo.start();
        this.rightServo.start();
    }

    public void backwards() {
        this.leftServo.setSpeed(1300);
        this.rightServo.setSpeed(1700);
        this.leftServo.start();
        this.rightServo.start();
    }

    public void turnRight() {
        this.leftServo.setSpeed(1300);
        this.rightServo.setSpeed(1700);
        this.leftServo.start();
        this.rightServo.start();
    }

}
