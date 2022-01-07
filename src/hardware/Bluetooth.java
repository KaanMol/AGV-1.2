package hardware;

import TI.SerialConnection;

public class Bluetooth {
    private SerialConnection serial;

    public Bluetooth(int baudRate) {
        this.serial = new SerialConnection(baudRate);
    }

    /**
     * Returns if data is available
     * In the case there is no data available, the return value will be -1
     *
     * @return an int that indicates if data is available
     */
    public int available() {
        return this.serial.available();
    }

    /**
     * Returns the received byte of data
     * @return received byte
     */
    public int readByte() {
        return this.serial.readByte();
    }

    /**
     * Writes a byte to the serial buffer
     * @param data a byte that is sent
     */
    public void writeByte(int data) {
        this.serial.writeByte(data);
    }
}
