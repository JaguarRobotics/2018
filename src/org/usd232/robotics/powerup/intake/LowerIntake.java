package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.commands.CommandBase;

/**
 * Lowers cube
 * @author Evan
 * @version 2018
 * @since 2018
 */
public class LowerIntake extends CommandBase {
    public LowerIntake() {
        requires(intakeSubsystem);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    	intakeSubsystem.intakeGrabberDown();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
    
}
