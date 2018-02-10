package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.log.Logger;

/**
 * The subsystem for the Lift
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class LiftSubsystem extends SubsystemBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();
    @Override
    protected void initDefaultCommand() {
    }

    public double getPotentiometerValue() {
        double value = scissorPotentiometer.get();
        LOG.info("Potentiometer Value Is At " + value);
        return value;
    }
}
