package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.calibration.CalibratorData;
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
        LOG.info("Lifting Lift to " + stepValue);
        switch (LiftSubsystem.currentPosition) {
            case Climb:
                stepValue = 500000000;
                break;
            case Scale:
                stepValue = CalibratorData.getLiftClimbTop();
                LiftSubsystem.currentPosition = LiftSubsystem.StepPositions.Climb;
                break;
            case Switch:
                stepValue = CalibratorData.getLiftScale();
                LiftSubsystem.currentPosition = LiftSubsystem.StepPositions.Scale;
                break;
            case Bottom:
                stepValue = CalibratorData.getLiftSwitch();
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
        } else if (IO.bottomLimitSwitch.get()) {
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
