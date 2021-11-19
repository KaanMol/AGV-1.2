import TI.BoeBot;
import TI.PinMode;

public class Servo {

    private int outputPin;
    private int currentSpeed = 1500;
    private int targetSpeed = 0;
    private boolean reverse = false;
    private TI.Servo servo;

    public Servo(int outputPin, boolean reverse) {
        this.outputPin = outputPin;
        this.reverse = reverse;

        BoeBot.setMode(this.outputPin, PinMode.PWM);
        this.servo = new TI.Servo(this.outputPin);
    }

    public void setSpeed(int targetSpeed) {
        this.targetSpeed = targetSpeed;
        this.servo.update(1500);
    }

    public void start() {
        this.servo.start();
    }

    public void stop() {
        this.servo.stop();
    }

    public void update(){

    }
}
