package GUI;

import common.Config;
import common.WirelessConfig;
import hardware.Bluetooth;
import interfaces.MovementUpdater;
import jssc.SerialPort;
import jssc.SerialPortException;
import vehicle.Movement;
import TI.SerialConnection;

public class Commands {
    private static final String PORT = "COM4";
    //Bluetooth bluetooth = new Bluetooth();

//    public void forwardButton() {
//        bluetooth.writeByte(WirelessConfig.forward);
//    }

    public void openPort() {

    }

    public void closePort() {
        
    }


    public void forwardbutton() {

        SerialPort serialPort = new SerialPort(PORT);
        try {
            System.out.println(serialPort.openPort()); // Open the serial connection
            serialPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.writeString("w");
            //Boebot doesn't return
//            byte[] buffer = serialPort.readBytes(10); // Fixed buffer length
//            for (int i = 0; i < 10; i++)
//                System.out.print(buffer[i] + "-");
            //Needs to be seperated
            //serialPort.closePort();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
}
