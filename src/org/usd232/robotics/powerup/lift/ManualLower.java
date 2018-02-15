package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj.Relay;

/**
 * The command to lower the lift
 * 
 * @author Brian, Alex Whipple
 * @since 2018
 * @version 2018
 */
public class ManualLower extends CommandBase {
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
     * Lowers the lift
     * 
     * @param lowerValue
     *            the value the robot the lift to
     * @since 2018
     * @version 2018
     */
    public ManualLower() {
        requires(liftSubsystem);
    }

    /**
     * What happens on initialize, does nothing
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void initialize() {
        LOG.info("Manually Lowering Lift");
    }

    /**
     * What happens while the command is running, moves the motor
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void execute() {
        if (counter % (onTime + offTime) >= offTime) {
            LiftSubsystem.lowerScissor();
        } else {
            LiftSubsystem.liftRelay.set(Relay.Value.kOff);
        }
        counter++;
    }

    /**
     * Checks if it's done
     * 
     * @return returns false
     * @since 2018
     * @version 2018
     */
    @Override
    protected boolean isFinished() {
        if (IO.bottomLimitSwitch.get()) {
            return true;
        }
        return false;
    }

    /**
     * Turns off the motor when the command ends
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void end() {
        LiftSubsystem.stopScissor();
    }

    /**
     * Runs end if the command is interrupted
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void interrupted() {
        end();
    }
}
