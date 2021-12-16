package hardware;

import TI.BoeBot;
import TI.PinMode;
import common.Config;


public class  UltraSonic {
    private int inputPin;
    private int outputPin;

    public UltraSonic(int inputpin, int outputpin){
        this.outputPin = outputpin;
        this.inputPin = inputpin;
        BoeBot.setMode(this.inputPin, PinMode.Input);
        BoeBot.setMode(this.outputPin, PinMode.Output);
    }

    public int Readings(){
        BoeBot.digitalWrite(outputPin, true);
        BoeBot.digitalWrite(outputPin, false);
        int pulse = BoeBot.pulseIn(inputPin, true, 10000);
        System.out.println("Pulse: " + pulse);
        return pulse;


    }
}
