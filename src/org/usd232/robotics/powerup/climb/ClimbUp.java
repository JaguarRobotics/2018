package org.usd232.robotics.powerup.climb;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

/**
 * Makes the robot climb up via a winch.
 *
 * @author Brian Parks
 * @since 2018
 * @version 2018
 */
public class ClimbUp extends CommandBase {
    /**
     * The Logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();
    /**
     * The speed to climb up at.
     * 
     * @since 2018
     */
    private static double speed = 1;
    /**
     * Makes the robot climb up given the value of the trigger.
     * 
     * @since 2018
     */
    public ClimbUp(double axisValue) {
    	speed = axisValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            liftSubsystem.climbUp(speed);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void end() {
        LOG.catchAll(()-> {
            liftSubsystem.stopClimbing();
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFinished() {
        return false;
    }
}
