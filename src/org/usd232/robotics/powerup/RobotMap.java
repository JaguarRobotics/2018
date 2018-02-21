package org.usd232.robotics.powerup;

/**
 * Maps the ports and other values we need for the robot where we can change stuff if wiring gets messed up
 * 
 * @since 2017
 * @version 2018
 */
public interface RobotMap {
    // Motors and Encoders
    /**
     * The port for the left motor.
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     LEFT_DRIVE_MOTOR_PORT      = 0;
    /**
     * The type of the left motor.
     * 
     * @since 2017
     * @version 2018
     */
    public static final MotorID LEFT_DRIVE_MOTOR_TYPE      = MotorID.Talon;
    /**
     * The port for the right motor.
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     RIGHT_DRIVE_MOTOR_PORT     = 1;
    /**
     * The type of the right motor.
     * 
     * @since 2017
     * @version 2018
     */
    public static final MotorID RIGHT_DRIVE_MOTOR_TYPE     = MotorID.Talon;
    // Digital I/Os
    /**
     * The A channel for the left encoder port number.
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     LEFT_ENCODER_CHANNEL_A     = 0;
    /**
     * The B channel for the left encoder port number.
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     LEFT_ENCODER_CHANNEL_B     = 1;
    /**
     * The A channel for the right encoder port number.
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     RIGHT_ENCODER_CHANNEL_A    = 2;
    /**
     * The B channel for the right encoder port number.
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     RIGHT_ENCODER_CHANNEL_B    = 3;
    // Relays
    /**
     * The port for the lift relay.
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     LIFT_RELAY_PORT            = 0;
    /**
     * The port for the Winch Relay.
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     WINCH_RELAY_PORT           = 1;
    // Gyro and Potentiometer
    /**
     * The port for the potentiometer.
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     POTENTIOMETER_PORT         = 0;
    /**
     * Gyroscope port.
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     GYROSCOPE_PORT             = 1;
    // Controls
    /**
     * The port for the left joystick.
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     LEFT_JOYSTICK_PORT         = 0;
    /**
     * The port for the right joystick.
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     RIGHT_JOYSTICK_PORT        = 1;
    /**
     * The port for the manipulator.
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     MANIPULATOR_JOYSTICK_PORT  = 2;
    /**
     * The port for the calibration controller.
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     CALIBRATION_JOYSTICK_PORT  = 5;
    // Solenoids
    /**
     * The port for the solenoid that grabs the cube.
     * 
     * @since 2018
     */
    public static final int     INTAKE_GRAB_SOLENOID       = 0;
    /**
     * The port for the solenoid that lifts the intake system.
     * 
     * @since 2018
     */
    public static final int     INTAKE_LIFT_SOLENOID       = 1;
    /**
     * The port for the solenoid that helps raise the scissor lift.
     * 
     * @since 2017
     * @version 2018
     */
    public static final int     RAISE_HELPER_SOLENOID_PORT = 2;
    // Limit Switches
    /**
     * The port for the Limit Switch that detects bottom position.
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     BOTTOM_LIMIT_SWITCH_PORT   = 5;
    /**
     * The port for the Limit Switch that detects top position.
     * 
     * @since 2018
     * @version 2018
     */
    public static final int     TOP_LIMIT_SWITCH_PORT      = 4;

    /**
     * The enum constants if the robot is in calibration mode.
     * 
     * @since 2018
     * @version 2018
     */
    public enum CalibrationMode {
        Calibrating, NotCalibrating
    }

    /**
     * enum constants for what our alliance is
     * 
     * @since 2017
     * @version 2018
     */
    public enum Alliance {
        Red, Blue
    }

    /**
     * enum constants for our robot starting position
     * 
     * @since 2018
     * @version 2018
     */
    public enum StartingPosition {
        One, Two, Three
    }
}
