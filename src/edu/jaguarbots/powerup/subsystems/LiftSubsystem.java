package edu.jaguarbots.powerup.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
/**
 * The subsystem for the Lift
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class LiftSubsystem extends SubsystemBase{
    /**
     * The Potentiometer that gives us the data
     */
    private static Potentiometer potentiometer = new AnalogPotentiometer(POTENTIOMETER_PORT, 360, 0);
//    TODO Add An offset for the potentiometer
    /**
     * Motor for the lift of the robot
     */
    public static SpeedController liftMotor = motor(LIFT_MOTOR_PORT, LIFT_MOTOR_TYPE);
    @Override
    protected void initDefaultCommand() {
    }
    public double getPotentiometerValue() {
	double value = potentiometer.get();
	return value;
    }
}
