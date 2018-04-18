package org.usd232.robotics.powerup;

import org.usd232.robotics.powerup.log.Logger;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
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
    public static final Logger          DO_NOT_USE_THIS_LOGGER = new Logger();
    /**
     * The left drive motor.
     * 
     * @since 2018
     * @version 2018
     */
    public static final SpeedController leftDriveMotor         = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->IOFactory.motor(LEFT_DRIVE_MOTOR_PORT, LEFT_DRIVE_MOTOR_TYPE));
    /**
     * The right drive motor.
     * 
     * @since 2018
     * @version 2018
     */
    public static final SpeedController rightDriveMotor        = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->IOFactory.motor(RIGHT_DRIVE_MOTOR_PORT, RIGHT_DRIVE_MOTOR_TYPE));
    /**
     * The motor for the climbing winch.
     * 
     * @since 2018
     * @version 2018
     */
    public static final Victor          climbMotor             = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->new Victor(WINCH_MOTOR_PORT));
    /**
     * The relay for the scissor lift.
     * 
     * @since 2018
     * @version 2018
     */
    public static final Relay           liftRelay              = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->new Relay(LIFT_RELAY_PORT));
    /**
     * The encoder on the left drive motor.
     * 
     * @since 2018
     * @version 2018
     */
    public static final Encoder         leftDriveEncoder       = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->new OversampledEncoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B));
    /**
     * The encoder on the right drive motor.
     * 
     * @since 2018
     * @version 2018
     */
    public static final Encoder         rightDriveEncoder      = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->new OversampledEncoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B));
    /**
     * The gyroscope in the SPI port on the RIO.
     * 
     * @since 2018
     * @version 2018
     */
    public static final Gyro            gyro                   = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->new ADXRS450_Gyro());
    /**
     * The potentiometer that measures the height of the scissor lift.
     * 
     * @since 2018
     * @version 2018
     */
    public static final Potentiometer   scissorPotentiometer   = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->new AnalogPotentiometer(POTENTIOMETER_PORT, 360, 0));
    /**
     * Grabs and releases cube.
     * 
     * @since 2018
     * @version 2018
     */
    public static final Solenoid        grabSolenoid           = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->new Solenoid(INTAKE_GRAB_SOLENOID));
    /**
     * Raises and lowers power cube.
     * 
     * @since 2018
     * @version 2018
     */
    public static final Solenoid        raiseIntakeSolenoid    = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->new Solenoid(INTAKE_LIFT_SOLENOID));
    /**
     * The solenoid that helps the scissor lift with an extra boost to get up.
     * 
     * @since 2018
     * @version 2018
     */
    public static final Solenoid        helpRaiseSolenoid      = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->new Solenoid(RAISE_HELPER_SOLENOID_PORT));
    /**
     * The limit switch that says if the Scissor lift is in bottom position
     * 
     * @since 2018
     * @version 2018
     */
    // Limit Switches
    public static final DigitalInput    bottomLimitSwitch      = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->new DigitalInput(BOTTOM_LIMIT_SWITCH_PORT));
    /**
     * The limit switch that says if the Scissor lift is in top position
     * 
     * @since 2018
     * @version 2018
     */
    public static final DigitalInput    topLimitSwitch         = DO_NOT_USE_THIS_LOGGER
                    .catchAll(()->new DigitalInput(TOP_LIMIT_SWITCH_PORT));
}
