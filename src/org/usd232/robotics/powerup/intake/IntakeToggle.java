package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.subsystems.IntakeSubsystem;

/**
 * Toggles Grab and Drop Cube commands.
 * 
 * @author Brian
 * @version 2018
 * @since 2018
 */
public class IntakeToggle extends CommandBase {
    public IntakeToggle() {
        requires(intakeSubsystem);
    }

    @Override
    protected void initialize() {
        if (IntakeSubsystem.intakeDown == true) {
            new RaiseIntake();
        } else {
            new LowerIntake();
        }
    }

    @Override
    protected void execute() {
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
