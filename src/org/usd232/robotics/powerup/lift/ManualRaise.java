package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LiftSubsystem;

/**
 * The command to raise the lift
 * 
 * @author Brian, Matthew
 * @since 2018
 * @version 2018
 */
public class ManualRaise extends CommandBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();

    /**
     * Raises the lift
     * 
     * @param raiseValue
     *            the value the robot the lift to
     * @since 2018
     * @version 2018
     */
    public ManualRaise() {
    }

    /**
     * What happens on initialize, does nothing
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            IO.helpRaiseSolenoid.set(true);
            LOG.info("Manually Raising Lift");
        });
    }

    /**
     * What happens while the command is running, moves the motor
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            LiftSubsystem.raiseScissor();
        });
    }

    /**
     * Checks if it's done
     * 
     * @return false
     * @since 2018
     * @version 2018
     */
    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
            return !IO.topLimitSwitch.get();
        }, true);
    }

    /**
     * Turns off the motor when the command ends
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void end() {
        LOG.catchAll(()-> {
            LiftSubsystem.stopScissor();
            IO.helpRaiseSolenoid.set(false);
        });
    }

    /**
     * Runs end if the command is interrupted
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void interrupted() {
        LOG.catchAll(()-> {
            end();
        });
    }
}
