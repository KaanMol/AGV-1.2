import TI.BoeBot;

public class Buzzer {
    private int outputPin;
    private int frequency;
    private int time;

    public Buzzer(int outputPin, int frequency, int time) {
        this.frequency = frequency;
        this.outputPin = outputPin;
        this.time = time; //time in milliseconds
    }

    public void start(){
        BoeBot.freqOut(this.outputPin, this.frequency, this.time);
        //turn buzzer on for specific amount of time
    }
    public void update(int outputPin, int frequency, int time){
        this.frequency = frequency;
        this.outputPin = outputPin;
        this.time = time;
        //change the data of buzzer
    }
}