package edu.jaguarbots.powerup.commands.intake;

import edu.jaguarbots.powerup.commands.CommandBase;

/**
 * Drops cube
 * @author Evan
 * @version 2018
 * @since 2018
 */
public class DropCube extends CommandBase {
    public DropCube() {
        requires(intakeSubsystem);
    }

    @Override
    protected void initialize() {
    }

     @Override
    protected void execute() {
    	intakeSubsystem.intakeGrabberOut();
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
