package configuration;

import enums.Direction;

public class Movement {
    /**
     * The GPIO pin where the right servo motor is connected to
     */
    public static final int RIGHT_SERVO_PIN = 12;

    /**
     * The GPIO pin where the left servo motor is connected to
     */
    public static final int LEFT_SERVO_PIN = 13;

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
     * The default direction of the vehicle
     */
    public static final Direction DEFAULT_DIRECTION = Direction.FORWARD;
}
