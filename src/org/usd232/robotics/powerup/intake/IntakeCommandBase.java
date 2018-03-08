package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

public abstract class IntakeCommandBase extends CommandBase {
    /**
     * The Logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();
    /**
     * The end time of the command.
     * 
     * @since 2018
     * @version 2018
     */
    private long                endTime;
    /**
     * The timeout of the command.
     * 
     * @since 2018
     * @version 2018
     */
    private long                timeout;

    /**
     * The method to do.
     * 
     * @since 2018
     * @version 2018
     */
    protected abstract void doIt();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            endTime = System.currentTimeMillis() + timeout;
            doIt();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
            return System.currentTimeMillis() > endTime;
        }, true);
    }
    /**
     * The intake command base.
     * @param timeout the timeout value of the command
     */
    public IntakeCommandBase(long timeout) {
        requires(intakeSubsystem);
        this.timeout = timeout;
    }
}
