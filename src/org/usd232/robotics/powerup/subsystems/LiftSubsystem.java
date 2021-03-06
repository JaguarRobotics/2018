package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.IO;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

/**
 * The subsystem for the Lift
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class LiftSubsystem extends SubsystemBase {
    private int counter                  = 0;
    private int onTime                   = 5;
    private int offTime                  = 5;
    /**
     * Static variable for the amount of things that have been calibrated for the POT
     * 
     * @since 2018
     * @version 2018
     */
    public int  amountOfThingsCalibrated = 0;

    @Override
    protected void initDefaultCommand() {
    }

    /**
     * Gets the potentiometer value.
     * 
     * @return THe potentiometer value.
     * @since 2018
     */
    public double getPotentiometerValue() {
        return scissorPotentiometer.get();
    }

    /**
     * Raises the scissor lift.
     * 
     * @since 2018
     */
    public void raiseScissor() {
    	liftMotor.set(Value.kOn);
    	liftMotor.set(Value.kReverse);
    }

    /**
     * Lowers the scissor lift.
     * 
     * @since 2018
     */
    public void lowerScissor() {
    	liftMotor.set(Value.kOn);
        if (counter % (onTime + offTime) >= offTime) {
        	liftMotor.set(Value.kForward);
        } else {
            stopScissor();
        }
        counter++;
    }

    /**
     * Stops the scissor lift.
     * 
     * @since 2018
     */
    public void stopScissor() {
    	liftMotor.set(Value.kOff);
        IO.helpRaiseSolenoid.set(false);
    }

    /**
     * Makes the robot climb up.
     * 
     * @param speed
     *            The speed to move the motor at
     * @since 2018
     */
    public void climbUp(double speed) {
        climbMotor.set(speed);
    }

    /**
     * Makes the robot climb down
     * 
     * @param speed
     *            The speed to move the motor at
     * @since 2018
     */
    public void climbDown(double speed) {
        climbMotor.set(speed);
    }

    /**
     * Stops the robot from climbing
     */
    public void stopClimbing() {
        climbMotor.set(0);
    }

    /**
     * Gets the bottom limit switch.
     * 
     * @return the bottom limit switch value
     * @since 2018
     */
    public boolean getBottomSwitch() {
        return bottomLimitSwitch.get();
    }

    /**
     * Gets the top limit switch.
     * 
     * @return the top limit switch value
     * @since 2018
     */
    public boolean getTopLimitSwitch() {
        return topLimitSwitch.get();
    }
}
