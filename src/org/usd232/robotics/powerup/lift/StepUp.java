package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LiftSubsystem;

/**
 * The command to lift the lift to a specific step
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class StepUp extends CommandBase {
    public class Canceler extends CommandBase {
        @Override
        protected boolean isFinished() {
            return true;
        }

        @Override
        protected void execute() {
            if (initTime + 500 > System.currentTimeMillis()) {
                cancel();
            }
        }
    }

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
    private long                initTime;

    /**
     * Lowers the lift of the robot to specified potentiometer value
     * 
     * @param lowerValue
     *            the value the robot the lift to
     * @since 2018
     * @version 2018
     */
    public StepUp() {
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
        initTime = System.currentTimeMillis();
        LOG.info("Lifting Lift to " + stepValue);
        switch (LiftSubsystem.currentPosition) {
            case Climb:
                stepValue = 500000000;
                break;
            case Scale:
                stepValue = Robot.calibratorData.getLiftClimbTop();
                LiftSubsystem.currentPosition = LiftSubsystem.StepPositions.Climb;
                break;
            case Switch:
                stepValue = Robot.calibratorData.getLiftScale();
                LiftSubsystem.currentPosition = LiftSubsystem.StepPositions.Scale;
                break;
            case Bottom:
                stepValue = Robot.calibratorData.getLiftSwitch();
                LiftSubsystem.currentPosition = LiftSubsystem.StepPositions.Switch;
                break;
        }
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
        IO.helpRaiseSolenoid.set(true);
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
        if (liftSubsystem.getPotentiometerValue() <= stepValue) {
            return true;
        } else if (!IO.topLimitSwitch.get()) {
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
        IO.helpRaiseSolenoid.set(false);
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
