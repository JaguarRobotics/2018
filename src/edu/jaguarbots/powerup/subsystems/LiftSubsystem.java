package edu.jaguarbots.powerup.subsystems;

/**
 * The subsystem for the Lift
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class LiftSubsystem extends SubsystemBase {
    @Override
    protected void initDefaultCommand() {
    }

    public double getPotentiometerValue() {
        double value = scissorPotentiometer.get();
        return value;
    }
}
