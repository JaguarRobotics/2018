package org.usd232.robotics.powerup.subsystems;

import edu.wpi.first.wpilibj.Relay;

/**
 * The subsystem for the Lift
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class LiftSubsystem extends SubsystemBase {
    public static StepPositions currentPosition = StepPositions.Bottom;

    @Override
    protected void initDefaultCommand() {
    }

    public double getPotentiometerValue() {
        return scissorPotentiometer.get();
    }

    public enum StepPositions {
        Bottom, Switch, Scale
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
