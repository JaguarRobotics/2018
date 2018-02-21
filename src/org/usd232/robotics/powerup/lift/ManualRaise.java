package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

/**
 * The command to raise the lift manually.
 * 
 * @author Brian, Matthew
 * @since 2018
 * @version 2018
 */
public class ManualRaise extends CommandBase {
    /**
     * The Logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            IO.helpRaiseSolenoid.set(true);
            LOG.info("Manually Raising Lift");
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            liftSubsystem.raiseScissor();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
            return !liftSubsystem.getTopLimitSwitch();
        }, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void end() {
        LOG.catchAll(()-> {
            liftSubsystem.stopScissor();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void interrupted() {
        LOG.catchAll(()-> {
            end();
        });
    }
}
