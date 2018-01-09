package edu.jaguarbots.powerup;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into to a variable name. This provides flexibility changing wiring, makes checking the wiring easier and significantly reduces the number of magic numbers floating around.
 * 
 * @since 2017
 */
public interface RobotMap {
    // PWMs
    // Need to add motor types
    public static final int LEFT_DRIVE_MOTOR_PORT = 0;
    public static final MotorID LEFT_DRIVE_MOTOR_TYPE = null;
    public static final int RIGHT_DRIVE_MOTOR_PORT = 1;
    public static final MotorID RIGHT_DRIVE_MOTOR_TYPE = null;
    public static final int CLIMBER_MOTOR_PORT = 2;
    public static final MotorID CLIMBER_MOTOR_TYPE = null;
    // Digital I/Os
    public static final int LEFT_ENCODER_CHANNEL_A = 0;
    public static final int LEFT_ENCODER_CHANNEL_B = 1;
    public static final int RIGHT_ENCODER_CHANNEL_A = 2;
    public static final int RIGHT_ENCODER_CHANNEL_B = 3;
    // Joysticks
    public static final int LEFT_JOYSTICK_PORT = 1;
    public static final int RIGHT_JOYSTICK_PORT = 2;
    public static final int MANIPULATOR_JOYSTICK_PORT = 3;
    // Robot Design
    public static final double ROBOT_WIDTH = 16;
    public static final int SOLENOID_GEAR_SHIFT_PORT = 1;
    public static final int SOLENOID_GEAR_DOOR_PORT = 2;

}
