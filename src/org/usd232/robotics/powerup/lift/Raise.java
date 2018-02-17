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
public class Raise extends CommandBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG        = new Logger();
    /**
     * Value that we are raising to
     * 
     * @since 2018
     * @version 2018
     */
    private double              raiseValue = 0;

    /**
     * Raises the lift of the robot to specified potentiometer value
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
     * What happens on initialize, does nothing
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void initialize() {
        IO.helpRaiseSolenoid.set(true);
        LOG.info("Raising Lift To " + raiseValue);
    }

    /**
     * What happens while the command is running, moves the motor
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void execute() {
        LiftSubsystem.raiseScissor();
    }

    /**
     * Checks if the potentiometer value has reached the target value
     * 
     * @return true if potentiometer value is greater than or equal to targetValue
     * @since 2018
     * @version 2018
     */
    @Override
    protected boolean isFinished() {
        if (liftSubsystem.getPotentiometerValue() <= raiseValue) {
            LOG.info("Stop For POT");
            return true;
        } else if (!IO.topLimitSwitch.get()) {
            LOG.info("Stop For Switch");
            return true;
        } else {
            return false;
        }
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
        IO.helpRaiseSolenoid.set(false);
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
