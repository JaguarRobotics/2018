package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LiftSubsystem;

/**
 * The command to raise the lift based on hitting the limit switch
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class RaiseToSwitch extends CommandBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG       = new Logger();

    /**
     * Raises the lift of the robot to its limit switch at the max height
     * 
     * @since 2018
     * @version 2018
     */
    public RaiseToSwitch() {
    }

    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            IO.helpRaiseSolenoid.set(true);
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
     * Returns true when the limit switch is tripped
     * 
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
