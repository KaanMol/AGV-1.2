package common;

import enums.Direction;

public class Config {
    /**
     * Servo
     */
    public static final int rightServoPin = 12;
    public static final int leftServoPin = 13;
    public static final int accelerationSpeedStep = 150;
    public static final int accelerationStep = 10;
    public static final Direction defaultDirection = Direction.FORWARD;

    /**
     * Gripper
     */
    public static final int gripperPin = 14;

    /**
     * Whisker
     */
    public static final int leftWhiskerPin = 8;
    public static final int rightWhiskerPin = 11;

    /**
     * LineFollower
     */
    public static final int leftLineFollowerPin = 2;
    public static final int middleLineFollowerPin = 1;
    public static final int rightLineFollowerPin = 0;

    /**
     * Buzzer
     */
    public static final int buzzerPin = 2;
    public static final int buzzerDutyCycle = 254;
    public static final int buzzerDelay = 500;
    /**
     * EmergencyStop
     */
    public static final int emergencyStopButtonPin = 1;

    /**
     * Bluetooth module
     */
    public static final int baudrate = 115200;

    /**
     * Ultrasonic
     */
    public static final int bottomUltraSonicOutputPin = 3;
    public static final int bottomUltraSonicInputPin = 4;
    public static final int topUltraSonicOutputPin = 5;
    public static final int topUltraSonicInputPin = 6;
}
