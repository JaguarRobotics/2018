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

    public double getPotentiometerValue() {
        return scissorPotentiometer.get();
    }

    public void raiseScissor() {
        liftRelay.set(Relay.Value.kReverse);
    }

    public void lowerScissor() {
        if (counter % (onTime + offTime) >= offTime) {
            liftRelay.set(Value.kForward);
        } else {
            stopScissor();
        }
        counter++;
    }

    public void stopScissor() {
        liftRelay.set(Relay.Value.kOff);
        IO.helpRaiseSolenoid.set(false);
    }

    public void climbUp() {
        climbRelay.set(Value.kForward);
    }

    public void climbDown() {
        climbRelay.set(Value.kReverse);
    }

    public void stopClimbing() {
        climbRelay.set(Value.kOff);
    }

    public boolean getBottomSwitch() {
        return bottomLimitSwitch.get();
    }

    public boolean getTopLimitSwitch() {
        return topLimitSwitch.get();
    }
}
