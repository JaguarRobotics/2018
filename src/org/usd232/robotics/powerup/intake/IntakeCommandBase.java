package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

public abstract class IntakeCommandBase extends CommandBase {
    private static final Logger LOG = new Logger();
    private long                endTime;
    private long                timeout;
    
    protected abstract void doIt();

    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            endTime = System.currentTimeMillis() + timeout;
            doIt();
        });
    }

    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
            return System.currentTimeMillis() > endTime;
        }, true);
    }

    public IntakeCommandBase(long timeout) {
        requires(intakeSubsystem);
        this.timeout = timeout;
    }
}
