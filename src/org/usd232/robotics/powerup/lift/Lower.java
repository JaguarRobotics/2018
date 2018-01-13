package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj.Relay;

/**
 * The command to lower the lift
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class Lower extends CommandBase {
    /**
     * Value that we are lowering to
     * 
     * @since 2018
     * @version 2018
     */
    private double lowerValue = 0;

    /**
     * Lowers the lift of the robot to specified potentiometer value
     * 
     * @param lowerValue
     *            the value the robot the lift to
     * @since 2018
     * @version 2018
     */
    public Lower(double lowerValue) {
        requires(liftSubsystem);
        this.lowerValue = lowerValue;
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
        LiftSubsystem.liftRelay.set(Relay.Value.kOn);
    }

    /**
     * Checks if the potentiometer value has reached the target value
     * 
     * @return true if potentiometer value is lower than or equal to targetValue
     * @since 2018
     * @version 2018
     */
    @Override
    protected boolean isFinished() {
        if (liftSubsystem.getPotentiometerValue() <= lowerValue) {
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
        LiftSubsystem.liftRelay.set(Relay.Value.kReverse);
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
