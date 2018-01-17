package org.usd232.robotics.powerup;

/**
 * Maps the ports and other values we need for the robot where we can change stuff if wiring gets messed up
 * 
 * @since 2017
 * @version 2018
 */
public interface RobotMap {
    // PWMs
    // Need to add motor types
    /**
     * The port for the left motor
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     LEFT_DRIVE_MOTOR_PORT      = 0;
    /**
     * The type of the left motor
     * 
     * @since 2017
     * @version 2018
     */
    public static final MotorID LEFT_DRIVE_MOTOR_TYPE      = MotorID.Talon;
    /**
     * The port for the right motor
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     RIGHT_DRIVE_MOTOR_PORT     = 1;
    /**
     * The type of the right motor
     * 
     * @since 2017
     * @version 2018
     */
    public static final MotorID RIGHT_DRIVE_MOTOR_TYPE     = MotorID.Talon;
    // Digital I/Os
    /**
     * The A channel for the left encoder port number
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     LEFT_ENCODER_CHANNEL_A     = 0;
    /**
     * The B channel for the left encoder port number
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     LEFT_ENCODER_CHANNEL_B     = 1;
    /**
     * The A channel for the right encoder port number
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     RIGHT_ENCODER_CHANNEL_A    = 2;
    /**
     * The B channel for the right encoder port number
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     RIGHT_ENCODER_CHANNEL_B    = 3;
//    Relay
    /**
     * The port for the lift relay
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     LIFT_RELAY_PORT            = 0;
    /**
     * The port for the whinch relay
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     WHINCH_RELAY_PORT            = 1;
    // Analog Ports
    /**
     * The port for the potentiometer
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     POTENTIOMETER_PORT         = 0;
    /**
     * Gyroscope port
     * 
     * @since 2018
     * @version 2018
     */
    public static final int GYROSCOPE_PORT = 1;
    // Controls
    /**
     * The port for the left joystick
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     LEFT_JOYSTICK_PORT         = 1;
    /**
     * The port for the right joystick
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     RIGHT_JOYSTICK_PORT        = 2;
    /**
     * The port for the manipulator
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     MANIPULATOR_JOYSTICK_PORT  = 3;
    // Robot Design
    /**
     * The width of the robot
     * 
     * @since 2017
     * @version 2018
     */
    public static final double  ROBOT_WIDTH                = 16;
    /**
     * The port for the solenoid that shifts gears
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     SOLENOID_GEAR_SHIFT_PORT   = 1;
    // Cutoff Values for potentiometers
    /**
     * The potentiometer value for the height of the portal
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     PORTAL_POTENTIOMETER_VALUE = 0;
    /**
     * The potentiometer value for the height of the scale
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     SCALE_POTENTIOMETER_VALUE  = 0;
    /**
     * The potentiometer value for the height of the switch
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     SWITCH_POTENTIOMETER_VALUE = 0;
    /**
     * The port for the solenoid that grabs the cube
     * 
     * @since 2018
     */
    public static final int     INTAKE_GRAB_SOLENOID       = 0;
    /**
     * The port for the solenoid that lifts the intake system
     * 
     * @since 2018
     */
    public static final int     INTAKE_LIFT_SOLENOID       = 0;

    /**
     * The enum constants for the gears that the robot could be in
     * 
     * @since 2017
     * @version 2018
     */
    public enum Gear {
        Low, High
    }
}
