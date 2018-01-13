package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.commands.CommandBase;

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
