package edu.jaguarbots.powerup.commands.intake;

import edu.jaguarbots.powerup.commands.CommandBase;

public class GrabCube extends CommandBase {
    public GrabCube() {
	requires(intakeSubsystem);
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
