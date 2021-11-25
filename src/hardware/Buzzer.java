package hardware;

import TI.BoeBot;
import TI.PWM;
import TI.PinMode;
import common.Config;

public class Buzzer {
    private PWM buzzer;

    /**
     *
     * @param outputPin - The pin which is used for the output of the circuit
     */
    public Buzzer(int outputPin) {
        this.buzzer = new PWM(outputPin, Config.buzzerDutyCycle);
    }

    /**
     * Starts playing a tone
     */
    public void start() {
        this.buzzer.start();
    }

    /**
     * Stops playing a tone
     */
    public void stop() {
        this.buzzer.stop();
    }
}