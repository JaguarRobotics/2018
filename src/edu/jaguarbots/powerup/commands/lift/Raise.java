package edu.jaguarbots.powerup.commands.lift;

import edu.jaguarbots.powerup.commands.CommandBase;

public class Raise extends CommandBase {
    public Raise() {
	requires(liftSubsystem);
    }
    @Override
    protected void initialize() {
    }
    @Override
    protected void execute() {
    }
    @Override
    protected boolean isFinished() {
	return false;
    }
    @Override
    protected void end() {
    }
    @Override
    protected void interrupted() {
    }
}
