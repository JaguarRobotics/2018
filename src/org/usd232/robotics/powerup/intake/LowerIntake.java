package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

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
        LOG.catchAll(()-> {
            intakeSubsystem.lowerIntake();
            LOG.info("Intake Lowered");
        });
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
