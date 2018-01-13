package edu.jaguarbots.powerup.commands.intake;

import edu.jaguarbots.powerup.commands.CommandBase;

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
