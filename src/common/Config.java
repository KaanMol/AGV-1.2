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
     * The GPIO pin where the gripper is connected to
     */
    public static final int gripperPin = 14;

    /**
     * The GPIO pin where the left whisker is connected to
     */
    public static final int leftWhiskerPin = 8;

    /**
     * The GPIO pin where the right whisker is connected to
     */
    public static final int rightWhiskerPin = 11;

    /**
     * The ADC pin where the left line follower is connected to
     */
    public static final int leftLineFollowerPin = 2;

    /**
     * The ADC pin where the middle line follower is connected to
     */
    public static final int middleLineFollowerPin = 1;

    /**
     * The ADC pin where the right line follower is connected to
     */
    public static final int rightLineFollowerPin = 0;

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

    /**
     * The number that the ir remote sends for moving forward
     */
    public static final int InfraredPin = 7;

    /**
     * The number that the ir remote sends for moving forward
     */
    public static final int remoteForward = 216;

    /**
     * The number that the ir remote sends for moving backward
     */
    public static final int remoteBackward = 217;

    /**
     * The number that the ir remote sends for turning left
     */
    public static final int remoteLeft = 147;

    /**
     * The number that the ir remote sends for turning right
     */
    public static final int remoteRight = 146;

    /**
     * The number that the ir remote sends for turning the wheels off
     */
    public static final int remoteNeutral = 223;

    /**
     * The number that the ir remote sends for emergency stop
     */
    public static final int remoteEmergencyStop = 149;

    /**
     * The number that the ir remote sends for emergency stop
     */
    public static final int remoteControlTransfer = 151;

    public static final int remoteGripper = 219;

    public static final int remoteContinue = 218;


    /**
     * Bluetooth module
     */
    public static final int baudrate = 115200;

    /**
     * GPIO pin the UltraSonic output is connected to
     */
    public static final int UltraSonicOutputPin = 3;

    /**
     * GPIO pin the UltraSonic input is connected to
     */
    public static final int UltraSonicInputPin = 4;
}
