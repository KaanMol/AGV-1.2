package hardware;

import TI.PWM;

public class Buzzer {
    private PWM buzzer;

    /**
     *
     * @param outputPin - The pin which is used for the output of the circuit
     */
    public Buzzer(int outputPin, int dutyCycle) {
        this.buzzer = new PWM(outputPin, dutyCycle);
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