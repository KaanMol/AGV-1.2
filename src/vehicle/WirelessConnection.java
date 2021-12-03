package vehicle;

import hardware.Bluetooth;
import interfaces.Updatable;

public class WirelessConnection implements Updatable {

    private Bluetooth bluetooth;

    public WirelessConnection() {
        this.bluetooth =  new Bluetooth();
    }

    public void update() {
        if (bluetooth.available() > 0) {
            int data = bluetooth.readByte();
            bluetooth.writeByte(data); // Echo data
            System.out.println("Received: " + data);
        }
    }
}
