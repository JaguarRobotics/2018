package edu.jaguarbots.powerup.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

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
    /**
     * The potentiometer value for the switch
     */
    private static final double SWITCH_POTENTIOMETER_VALUE = 0;
//  TODO Add potentiometer value for switch
    /**
     * The potentiometer value for the scale
     */
    private static final double SCALE_POTENTIOMETER_VALUE = 0;
//  TODO Add potentiometer value for scale
    @Override
    protected void initDefaultCommand() {
    }
    public double getPotentiometerValue() {
	double value = potentiometer.get();
	return value;
    }
}
