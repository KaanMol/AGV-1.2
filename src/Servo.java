public class Servo {

    private int outputPin;
    private int currentSpeed = 0;
    private int targetSpeed = 0;
    private boolean reverse = false;

    Servo(int outputPin, boolean reverse)
    {
        this.outputPin = outputPin;
        this.reverse = reverse;
    }


}
