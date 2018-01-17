package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.commands.CommandBase;

/**
 * Grabs cube
 * @author Evan
 * @version 2018
 * @since 2018
 */
public class GrabCube extends CommandBase {
    public GrabCube() {
        requires(intakeSubsystem);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    	intakeSubsystem.intakeGrabberIn();
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
