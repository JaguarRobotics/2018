package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

/**
 * The command to lower the lift.
 * 
 * @author Brian, Alex Whipple
 * @since 2018
 * @version 2018
 */
public class Lower extends CommandBase {
    /**
     * The Logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG        = new Logger();
    /**
     * Value that we are lowering to.
     * 
     * @since 2018
     * @version 2018
     */
    private double              lowerValue = 0;

    /**
     * Lowers the lift of the robot to specified potentiometer value
     * 
     * @param lowerValue
     *            the value the robot the lift to
     * @since 2018
     * @version 2018
     */
    public Lower(double lowerValue) {
        this.lowerValue = lowerValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            LOG.info("Lowering Lift to " + lowerValue);
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
            if (liftSubsystem.getPotentiometerValue() <= lowerValue) {
                return true;
            } else if (!liftSubsystem.getBottomSwitch()) {
                return true;
            } else {
                return false;
            }
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
