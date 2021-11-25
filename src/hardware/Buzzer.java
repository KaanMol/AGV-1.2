package hardware;

import TI.BoeBot;

public class Buzzer {
    private int outputPin;
    private int frequency;
    private int milliseconds;

    /**
     *
     * @param outputPin - The pin which is used for the output of the circuit
     * @param frequency - The frequency of the tone of the buzzer
     * @param milliseconds - The amount of milliseconds the buzzer will play a tone
     */
    public Buzzer(int outputPin, int frequency, int milliseconds) {
        this.frequency = frequency;
        this.outputPin = outputPin;
        this.milliseconds = milliseconds;

    }

    /**
     *
     * Starts playing a tone
     */
    public void start() {
        BoeBot.freqOut(this.outputPin, this.frequency, this.milliseconds);

    }

    /**
     *
     * @param outputPin - The pin which is used for the output of the circuit
     * @param frequency - The frequency of the tone of the buzzer
     * @param milliseconds - The amount of milliseconds the buzzer will play a tone
     */
    public void update(int outputPin, int frequency, int milliseconds) {
        this.frequency = frequency;
        this.outputPin = outputPin;
        this.milliseconds = milliseconds;

    }
}