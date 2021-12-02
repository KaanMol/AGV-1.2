package common;

import enums.Direction;

public class Config {
    public static final int rightServoPin = 12;
    public static final int leftServoPin = 13;
    public static final int leftWhiskerPin = 8;
    public static final int rightWhiskerPin = 11;
    public static final int buzzerPin = 2;
    public static final int buzzerDutyCycle = 254;

    public static final int emergencyStopButtonPin = 1;

    /**
     * Amount of ms before the driving system accelerates further.
     */
    public static final int accelerationSpeedStep = 150;

    /**
     * Acceleration steps of the driving steps
     */
    public static final int accelerationStep = 10;

    public static final int buzzerDelay = 500;

    public static final Direction defaultDirection = Direction.FORWARD;
}
