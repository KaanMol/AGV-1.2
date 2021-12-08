package vehicle;

import enums.WirelessCommands;
import hardware.Bluetooth;
import interfaces.Updatable;
import interfaces.WirelessUpdater;

public class WirelessConnection implements Updatable {

    private Bluetooth bluetooth;
    private WirelessUpdater callback;

    public WirelessConnection(WirelessUpdater callback) {
        this.callback = callback;
        this.bluetooth =  new Bluetooth();
    }

    public void update() {
        if (this.bluetooth.available() > 0) {
            int data = bluetooth.readByte();
            bluetooth.writeByte(data); // Echo data
            callback.onWirelessUpdate(data);
            System.out.println("Received: " + data);
        }
    }
}
