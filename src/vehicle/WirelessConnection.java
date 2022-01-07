package vehicle;

import configuration.Wireless;
import hardware.Bluetooth;
import interfaces.Updatable;
import interfaces.WirelessUpdater;

public class WirelessConnection implements Updatable {

    private Bluetooth bluetooth;
    private WirelessUpdater callback;

    /**
     * Constructor
     * @param callback is used to send the data from the wireless connection
     */
    public WirelessConnection(WirelessUpdater callback) {
        this.callback = callback;
        this.bluetooth =  new Bluetooth(Wireless.BAUDRATE);
    }

    /**
     * Checks when wireless conection is available and calls the callback and gives the data
     */
    public void update() {
        if (this.bluetooth.available() > 0) {
            int data = bluetooth.readByte();
            bluetooth.writeByte(data); // Echo data
            callback.onWirelessUpdate(data);
        }
    }
}
