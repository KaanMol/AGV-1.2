import TI.BoeBot;
import TI.PinMode;
import interfaces.MovementUpdater;
import interfaces.Updatable;
import vehicle.Movement;

public class RobotMain {

    public static void main(String[] args) {

//        boolean state = true;
//        BoeBot.setMode(0, PinMode.Output);
//
//        while (true) {
//            state = !state;
//            BoeBot.digitalWrite(0, state);
//            BoeBot.wait(250);
//        }

        Ed ed = new Ed();
        ed.updater();

        //Movement movement = new Movement();

    }


}