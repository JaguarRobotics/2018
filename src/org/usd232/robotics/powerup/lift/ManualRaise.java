package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj.Relay;

/**
 * The command to raise the lift
 * 
 * @author Brian, Matthew
 * @since 2018
 * @version 2018
 */
public class ManualRaise extends CommandBase {
    /**
     * Raises the lift
     * 
     * @param raiseValue
     *            the value the robot the lift to
     * @since 2018
     * @version 2018
     */
    public ManualRaise() {
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
    }

    /**
     * What happens while the command is running, moves the motor
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void execute() {
        LiftSubsystem.liftRelay.set(Relay.Value.kForward);
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
        LiftSubsystem.liftRelay.set(Relay.Value.kOff);
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
