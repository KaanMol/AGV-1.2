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
    private SerialPort serialPort = new SerialPort(PORT);
    //Bluetooth bluetooth = new Bluetooth();

//    public void forwardButton() {
//        bluetooth.writeByte(WirelessConfig.forward);
//    }

    public void openPort() {

        try {
            serialPort.openPort(); // Open the serial connection
            serialPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void closePort() {
        try {
            serialPort.closePort();
        }
        catch (SerialPortException e) {
            e.printStackTrace();
        }

    }


    public void forwardbutton() {
        try {
            serialPort.writeString("w");
        }
        catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void leftButton() {
        try {
            serialPort.writeString("a");
        }
        catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void rightButton() {
        try {
            serialPort.writeString("d");
        }
        catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

//    public void backwardButton() {
//        try {
//            serialPort.writeString("s");
//        }
//        catch (SerialPortException e) {
//            e.printStackTrace();
//        }
//    }

    public void neutralButton() {
        try {
            //writing neutral character
            serialPort.writeString("q");
        }
        catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void emergencyButton() {
        try {
            //writing emergencyStop character
            serialPort.writeString("q");
        }
        catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void gripperButton() {
        try {
            //writing gripper character
            serialPort.writeString("e");
        }
        catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

}
