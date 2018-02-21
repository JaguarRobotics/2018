package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj.Relay;

/**
 * The command to Lower the lift based on hitting the limit switch
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class LowerToBottom extends CommandBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG     = new Logger();
    private int                 counter = 0;
    private int                 onTime  = 5;
    private int                 offTime = 5;

    /**
     * Lowers the lift of the robot to its limit switch at the bottom height
     * 
     * @since 2018
     * @version 2018
     */
    public LowerToBottom() {
    }

    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
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
            if (counter % (onTime + offTime) >= offTime) {
                LiftSubsystem.lowerScissor();
            } else {
                LiftSubsystem.liftRelay.set(Relay.Value.kOff);
            }
            counter++;
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
            if (IO.scissorPotentiometer.get() <= Robot.calibratorData.getLiftBottom()) {
                return true;
            }
            return !IO.bottomLimitSwitch.get();
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
