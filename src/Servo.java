public class Servo {

    private int outputPin;
    private int currentSpeed = 0;
    private int targetSpeed = 0;
    private boolean reverse = false;

    public Servo(int outputPin, boolean reverse) {
        this.outputPin = outputPin;
        this.reverse = reverse;
    }

    public void setSpeed(int targetSpeed) {

    }

    public void stop() {

    }

    public void update(){

    }
}
