package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj.Relay;

/**
 * The command to lower the lift to a specific step
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class StepDown extends CommandBase {

    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG       = new Logger();
    /**
     * Value that we are lowering to
     * 
     * @since 2018
     * @version 2018
     */
    private double              stepValue = 0;
    private int                 counter   = 0;
    private int                 onTime    = 5;
    private int                 offTime   = 5;

    /**
     * Lowers the lift of the robot to specified potentiometer value
     * 
     * @param lowerValue
     *            the value the robot the lift to
     * @since 2018
     * @version 2018
     */
    public StepDown() {
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
        switch (LiftSubsystem.currentPosition) {
            case Scale:
                stepValue = Robot.calibratorData.getLiftSwitch();
                LiftSubsystem.currentPosition = LiftSubsystem.StepPositions.Switch;
                break;
            case Switch:
                stepValue = Robot.calibratorData.getLiftBottom();
                LiftSubsystem.currentPosition = LiftSubsystem.StepPositions.Bottom;
                break;
            case Bottom:
                stepValue = -500000000;
                break;
        }
        LOG.info("Lowering Lift to " + stepValue);
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
     * Checks if the potentiometer value has reached the target value
     * 
     * @return true if potentiometer value is lower than or equal to targetValue
     * @since 2018
     * @version 2018
     */
    @Override
    protected boolean isFinished() {
        if (liftSubsystem.getPotentiometerValue() >= stepValue) {
            return true;
        } else if (!IO.bottomLimitSwitch.get()) {
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