package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.commands.CommandBase;

public class LowerIntake extends CommandBase {
    public LowerIntake() {
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
