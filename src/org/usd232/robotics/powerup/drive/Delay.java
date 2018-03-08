package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

/**
 * Delays the robot for a specified amount of time.
 * 
 * @author Zach, Brian
 * @since 2018
 * @version 2018
 */
public class Delay extends CommandBase {
    /**
     * The logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();
    /**
     * The amount of milliseconds to wait.
     * 
     * @since 2018
     * @version 2018
     */
    private final long          millis;
    /**
     * The start time.
     * 
     * @since 2018
     * @version 2018
     */
    private long                startTime;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            LOG.enter("initialize");
            startTime = System.currentTimeMillis();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
            return System.currentTimeMillis() >= startTime + millis;
        }, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void end() {
        LOG.catchAll(()-> {
            LOG.enter("end");
        });
    }

    /**
     * Delays the robot for a specified amount of time.
     * @param millis Amount of miliseconds to have the robot pause for.
     */
    public Delay(long millis) {
        this.millis = millis;
    }
}
