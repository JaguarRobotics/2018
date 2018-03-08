package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

/**
 * The command to Lower the lift based on hitting the limit switch.
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class LowerToBottom extends CommandBase {
    /**
     * The Logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();

    /**
     * Lowers the lift of the robot to its limit switch at the bottom height.
     * 
     * @since 2018
     * @version 2018
     */
    public LowerToBottom() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
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
            if (IO.scissorPotentiometer.get() <= Robot.calibratorData.getLiftBottom()) {
                return true;
            }
            return !IO.bottomLimitSwitch.get();
        }, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void end() {
        LOG.catchAll(()-> {
            liftSubsystem.stopScissor();
            IO.helpRaiseSolenoid.set(false);
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
