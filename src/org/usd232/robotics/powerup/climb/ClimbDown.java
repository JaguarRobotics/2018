package org.usd232.robotics.powerup.climb;

import java.util.function.Supplier;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

/**
 * Makes the robot climb down via a winch.
 *
 * @author Brian Parks
 * @since 2018
 * @version 2018
 */
public class ClimbDown extends CommandBase {
    /**
     * The Logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();
    /**
     * The speed to climb down at.
     * 
     * @since 2018
     */
    private Supplier<Double>    speed;

    /**
     * Makes the robot climb down given the value of the trigger.
     * 
     * @since 2018
     */
    public ClimbDown(Supplier<Double> axisValue) {
        speed = axisValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            liftSubsystem.climbDown(speed.get());
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
