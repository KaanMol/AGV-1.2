package common;

import enums.Direction;

public class Config {
    /**
     * The GPIO pin where the right servo motor is connected to
     */
    public static final int rightServoPin = 12;

    /**
     * The GPIO pin where the left servo motor is connected to
     */
    public static final int leftServoPin = 13;

    /**
     * The GPIO pin where the left whisker is connected to
     */
    public static final int leftWhiskerPin = 8;

    /**
     * The GPIO pin where the right whisker is connected to
     */
    public static final int rightWhiskerPin = 11;

    /**
     * The GPIO pin where the buzzer is connected to
     */
    public static final int buzzerPin = 2;

    /**
     * The Duty Cycle of the buzzer
     */
    public static final int buzzerDutyCycle = 254;

    /**
     * The GPIO Pin where the emergency button is connected to
     */
    public static final int emergencyStopButtonPin = 1;

    /**
     * Amount of milliseconds before the driving system accelerates further.
     */
    public static final int accelerationSpeedStep = 150;

    /**
     * The number that is used to increment per step for acceleration.
     * So if the speed step is 150ms and the step is 10, every 150ms the acceleration is incremented with 10
     */
    public static final int accelerationStep = 10;

    /**
     * The delay of the buzzer in milliseconds
     */
    public static final int buzzerDelay = 500;

    /**
     * The default direction of the vehicle
     */
    public static final Direction defaultDirection = Direction.FORWARD;
}
