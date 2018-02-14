package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.IntakeSubsystem;

/**
 * Lowers cube
 * 
 * @author Evan
 * @version 2018
 * @since 2018
 */
public class LowerIntake extends CommandBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();

    public LowerIntake() {
        requires(intakeSubsystem);
    }

    @Override
    protected void initialize() {
        intakeSubsystem.lowerIntake();
        LOG.info("Intake Lowered");
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
        IntakeSubsystem.intakeDown = true;
    }

    @Override
    protected void interrupted() {
    }
}
