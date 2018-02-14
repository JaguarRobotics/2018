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
public class CubeToggle extends CommandBase {
    public CubeToggle() {
        requires(intakeSubsystem);
    }

    @Override
    protected void initialize() {
        if (IntakeSubsystem.intakeOpenPosition == true) {
            new GrabCube();
        } else {
            new DropCube();
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
