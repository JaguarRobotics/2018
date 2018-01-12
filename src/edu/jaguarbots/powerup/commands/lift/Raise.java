package edu.jaguarbots.powerup.commands.lift;

import edu.jaguarbots.powerup.commands.CommandBase;
import edu.jaguarbots.powerup.subsystems.LiftSubsystem;

/**
 * The command to raise the lift
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class Raise extends CommandBase {
    /**
     * Value that we are raising to
     * 
     * @since 2018
     * @version 2018
     */
    private double raiseValue = 0;

    /**
     * Raises the lift of the robot to specified potentiometer value
     * 
     * @param raiseValue
     *            the value the robot the lift to
     * @since 2018
     * @version 2018
     */
    public Raise(double raiseValue) {
        requires(liftSubsystem);
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
    }

    /**
     * What happens while the command is running, moves the motor
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void execute() {
        LiftSubsystem.liftMotor.set(1);
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
        if (liftSubsystem.getPotentiometerValue() >= raiseValue) {
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
        LiftSubsystem.liftMotor.set(0);
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
