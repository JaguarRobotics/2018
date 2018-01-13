package edu.jaguarbots.powerup;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 * This class contains all I/O initialization. This class is the ONLY class allowed to initialize I/O devices (motors,
 * encoders, sensors, etc.).
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public interface IO extends RobotMap {
    /**
     * The left drive motor
     * 
     * @since 2018
     */
    public static final SpeedController leftDriveMotor       = IOFactory.motor(LEFT_DRIVE_MOTOR_PORT,
                    LEFT_DRIVE_MOTOR_TYPE);
    /**
     * The right drive motor
     * 
     * @since 2018
     */
    public static final SpeedController rightDriveMotor      = IOFactory.motor(RIGHT_DRIVE_MOTOR_PORT,
                    RIGHT_DRIVE_MOTOR_TYPE);
    /**
     * The encoder on the left drive motor
     * 
     * @since 2018
     */
    public static final Encoder         leftDriveEncoder     = new OversampledEncoder(LEFT_ENCODER_CHANNEL_A,
                    LEFT_ENCODER_CHANNEL_B);
    /**
     * The encoder on the right drive motor
     * 
     * @since 2018
     */
    public static final Encoder         rightDriveEncoder    = new OversampledEncoder(RIGHT_ENCODER_CHANNEL_A,
                    RIGHT_ENCODER_CHANNEL_B);
    /**
     * The solenoid to shift gears
     */
    public static final Solenoid        gearShiftSolenoid    = new Solenoid(SOLENOID_GEAR_SHIFT_PORT);
    /**
     * The potentiometer that measures the height of the scissor lift
     */
    public static final Potentiometer   scissorPotentiometer = new AnalogPotentiometer(POTENTIOMETER_PORT, 360, 0);
    /**
     * The motor for the lift of the robot
     */
    public static final SpeedController liftMotor            = IOFactory.motor(LIFT_MOTOR_PORT, LIFT_MOTOR_TYPE);
    
    /**
	 * Grabs and releases cube
	 * @since 2018
	 * @version 2018
	 */
	public static final Solenoid grabSol = new Solenoid(RobotMap.solIntakeGrab);
	
	/**
	 * Raises and lowers power cube
	 * @since 2018
	 * @version 2018
	 */
	public static final Solenoid liftSol = new Solenoid(RobotMap.solIntakeLift);
}
