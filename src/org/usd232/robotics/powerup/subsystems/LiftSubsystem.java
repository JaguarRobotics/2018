package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.log.Logger;
import edu.wpi.first.wpilibj.Relay;

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
    private static final Logger LOG             = new Logger();
    public static StepPositions currentPosition = StepPositions.Bottom;

    @Override
    protected void initDefaultCommand() {
    }

    public double getPotentiometerValue() {
        double value = scissorPotentiometer.get();
        LOG.info("Potentiometer Value Is At " + value);
        return value;
    }

    public enum StepPositions {
        Scale, Switch, Bottom, Climb
    }

    public static void raiseScissor() {
        LiftSubsystem.liftRelay.set(Relay.Value.kForward);
    }

    public static void lowerScissor() {
        LiftSubsystem.liftRelay.set(Relay.Value.kReverse);
    }
    public static void stopScissor() {
        LiftSubsystem.liftRelay.set(Relay.Value.kOff);
    }
}
