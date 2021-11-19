import TI.BoeBot;

public class Buzzer {
    private int outputPin;
    private int frequency;
    private int time;

    /**
     *
     * @param outputPin
     * @param frequency
     * @param time
     */


    public Buzzer(int outputPin, int frequency, int time) {
        this.frequency = frequency;
        this.outputPin = outputPin;
        this.time = time;

    }

    /**
     *
     * Get variable values from 'outputPin, frequency, time'
     * Call freqOut method
     */

    public void start(){
        BoeBot.freqOut(this.outputPin, this.frequency, this.time);

    }

    /**
     *
     * @param outputPin
     * @param frequency
     * @param time
     */
    public void update(int outputPin, int frequency, int time){
        this.frequency = frequency;
        this.outputPin = outputPin;
        this.time = time;

    }
}