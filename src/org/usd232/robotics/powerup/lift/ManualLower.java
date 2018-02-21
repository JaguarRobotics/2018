package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

/**
 * The command to lower the lift manually.
 * 
 * @author Brian, Alex Whipple
 * @since 2018
 * @version 2018
 */
public class ManualLower extends CommandBase {
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
            LOG.info("Manually lowering lift");
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            liftSubsystem.lowerScissor();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
            if (!liftSubsystem.getBottomSwitch()) {
                return true;
            }
            return false;
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
