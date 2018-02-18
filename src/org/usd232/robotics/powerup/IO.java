package org.usd232.robotics.powerup;

import org.usd232.robotics.powerup.log.Logger;
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
    public static final Logger          this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class = new Logger();
    // Motors and Encoders
    /**
     * The left drive motor
     * 
     * @since 2018
     * @version 2018
     */
    public static final SpeedController leftDriveMotor                                                                  = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->IOFactory.motor(LEFT_DRIVE_MOTOR_PORT, LEFT_DRIVE_MOTOR_TYPE));
    /**
     * The right drive motor
     * 
     * @since 2018
     * @version 2018
     */
    public static final SpeedController rightDriveMotor                                                                 = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->IOFactory.motor(RIGHT_DRIVE_MOTOR_PORT, RIGHT_DRIVE_MOTOR_TYPE));
    /**
     * The encoder on the left drive motor
     * 
     * @since 2018
     * @version 2018
     */
    public static final Encoder         leftDriveEncoder                                                                = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->new OversampledEncoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B));
    /**
     * The encoder on the right drive motor
     * 
     * @since 2018
     * @version 2018
     */
    public static final Encoder         rightDriveEncoder                                                               = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->new OversampledEncoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B));
    // Gyros and Potentiometers
    /**
     * The analog gyroscope
     * 
     * @since 2018
     * @version 2018
     */
    public static final Gyro            gyro                                                                            = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->new ADXRS450_Gyro());
    /**
     * The potentiometer that measures the height of the scissor lift
     * 
     * @since 2018
     * @version 2018
     */
    public static final Potentiometer   scissorPotentiometer                                                            = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->new AnalogPotentiometer(POTENTIOMETER_PORT, 360, 0));
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
    public static final Relay           winchRelay                                                                      = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->new Relay(WINCH_RELAY_PORT));
    public static final Relay           liftRelay                                                                       = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->new Relay(LIFT_RELAY_PORT));
    /**
     * Grabs and releases cube
     * 
     * @since 2018
     * @version 2018
     */
    // Solenoids
    public static final Solenoid        grabSolenoid                                                                    = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->new Solenoid(INTAKE_GRAB_SOLENOID));
    /**
     * Raises and lowers power cube
     * 
     * @since 2018
     * @version 2018
     */
    public static final Solenoid        liftSolenoid                                                                    = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->new Solenoid(INTAKE_LIFT_SOLENOID));
    /**
     * The solenoid to shift gears
     * 
     * @since 2018
     * @version 2018
     */
    public static final Solenoid        gearShiftSolenoid                                                               = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->new Solenoid(SOLENOID_GEAR_SHIFT_PORT));
    /**
     * The solenoid to shift gears
     * 
     * @since 2018
     * @version 2018
     */
    public static final Solenoid        helpRaiseSolenoid                                                               = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->new Solenoid(RAISE_HELPER_SOLENOID_PORT));
    /**
     * The limit switch that says if the Scissor lift is in bottom position
     * 
     * @since 2018
     * @version 2018
     */
    // Limit Switches
    public static final DigitalInput    bottomLimitSwitch                                                               = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->new DigitalInput(BOTTOM_LIMIT_SWITCH_PORT));
    /**
     * The limit switch that says if the Scissor lift is in top position
     * 
     * @since 2018
     * @version 2018
     */
    public static final DigitalInput    topLimitSwitch                                                                  = this_is_a_private_variable_but_really_not_so_do_not_use_it_outside_the_IO_class
                    .catchAll(()->new DigitalInput(TOP_LIMIT_SWITCH_PORT));
}
