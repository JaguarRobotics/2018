package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

/**
 * The command to raise the lift.
 * 
 * @author Brian, Matthew
 * @since 2018
 * @version 2018
 */
public class Raise extends CommandBase {
    /**
     * The Logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG        = new Logger();
    /**
     * Value that we are raising to.
     * 
     * @since 2018
     * @version 2018
     */
    private double              raiseValue = 0;

    /**
     * Raises the lift of the robot to specified potentiometer value.
     * 
     * @param raiseValue
     *            the value the robot the lift to
     * @since 2018
     * @version 2018
     */
    public Raise(double raiseValue) {
        this.raiseValue = raiseValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            IO.helpRaiseSolenoid.set(true);
            LOG.info("Raising Lift To " + raiseValue);
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
            if (liftSubsystem.getPotentiometerValue() >= raiseValue) {
                LOG.info("Stop For POT");
                return true;
            } else if (!liftSubsystem.getTopLimitSwitch()) {
                LOG.info("Stop For Switch");
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
