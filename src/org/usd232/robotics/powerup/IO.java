package org.usd232.robotics.powerup;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 * This class contains all I/O initialization. This class is the ONLY class allowed to initialize I/O devices (motors,
 * encoders, sensors, etc.).
 * 
 * @author Zach Deibert, Brian Parks
 * @since 2018
 * @version 2018
 */
public interface IO extends RobotMap {
    // Motors and Encoders
    /**
     * The left drive motor
     * 
     * @since 2018
     * @version 2018
     */
    public static final SpeedController leftDriveMotor       = IOFactory.motor(LEFT_DRIVE_MOTOR_PORT,
                    LEFT_DRIVE_MOTOR_TYPE);
    /**
     * The right drive motor
     * 
     * @since 2018
     * @version 2018
     */
    public static final SpeedController rightDriveMotor      = IOFactory.motor(RIGHT_DRIVE_MOTOR_PORT,
                    RIGHT_DRIVE_MOTOR_TYPE);
    /**
     * The encoder on the left drive motor
     * 
     * @since 2018
     * @version 2018
     */
    public static final Encoder         leftDriveEncoder     = new OversampledEncoder(LEFT_ENCODER_CHANNEL_A,
                    LEFT_ENCODER_CHANNEL_B);
    /**
     * The encoder on the right drive motor
     * 
     * @since 2018
     * @version 2018
     */
    public static final Encoder         rightDriveEncoder    = new OversampledEncoder(RIGHT_ENCODER_CHANNEL_A,
                    RIGHT_ENCODER_CHANNEL_B);
    // Gyros and Potentiometers
    /**
     * The analog gyroscope
     * 
     * @since 2018
     * @version 2018
     */
    public static final Gyro            gyro                 = new ADXRS450_Gyro();
    /**
     * The potentiometer that measures the height of the scissor lift
     * 
     * @since 2018
     * @version 2018
     */
    public static final Potentiometer   scissorPotentiometer = new AnalogPotentiometer(POTENTIOMETER_PORT, 360, 0);
    /**
     * The motor for the lift of the robot
     * 
     * @since 2018
     * @version 2018
     */
    // Relays
    /**
     * The Winch For The Climber
     * 
     * @since 2018
     * @version 2018
     */
    public static final Relay           winchRelay           = new Relay(WINCH_RELAY_PORT);
    public static final Relay           liftRelay            = new Relay(LIFT_RELAY_PORT);
    /**
     * Grabs and releases cube
     * 
     * @since 2018
     * @version 2018
     */
    // Solenoids
    public static final Solenoid        grabSolenoid         = new Solenoid(INTAKE_GRAB_SOLENOID);
    /**
     * Raises and lowers power cube
     * 
     * @since 2018
     * @version 2018
     */
    public static final Solenoid        liftSolenoid         = new Solenoid(INTAKE_LIFT_SOLENOID);
    /**
     * The solenoid to shift gears
     * 
     * @since 2018
     * @version 2018
     */
    public static final Solenoid        gearShiftSolenoid    = new Solenoid(SOLENOID_GEAR_SHIFT_PORT);
    /**
     * The solenoid to shift gears
     * 
     * @since 2018
     * @version 2018
     */
    public static final Solenoid        helpRaiseSolenoid    = new Solenoid(RAISE_HELPER_SOLENOID_PORT);
    /**
     * The limit switch that says if the Scissor lift is in bottom position
     * 
     * @since 2018
     * @version 2018
     */
    // Limit Switches
    public static final DigitalInput    bottomLimitSwitch    = new DigitalInput(BOTTOM_LIMIT_SWITCH_PORT);
    /**
     * The limit switch that says if the Scissor lift is in top position
     * 
     * @since 2018
     * @version 2018
     */
    public static final DigitalInput    topLimitSwitch       = new DigitalInput(TOP_LIMIT_SWITCH_PORT);
}
