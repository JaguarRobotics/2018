package edu.jaguarbots.powerup.commands.intake;

import edu.jaguarbots.powerup.commands.CommandBase;

public class RaiseIntake extends CommandBase {
    public RaiseIntake() {
        requires(intakeSubsystem);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    	intakeSubsystem.intakeGrabberUp();
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
