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
public class GoToLevel extends CommandBase {

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
    private int                 counter   = 0;
    private int                 onTime    = 5;
    private int                 offTime   = 5;
    private int              threshold = 5;
    private double targetPotentiometerValue;
    private boolean lowering = false;
    private boolean manualFinished = false;

    /**
     * Lowers the lift of the robot to specified potentiometer value
     * 
     * @param lowerValue
     *            the value the robot the lift to
     * @since 2018
     * @version 2018
     */
    public GoToLevel(LiftSubsystem.StepPositions target) {
    	LOG.info("Lowering to the height of the " + target);
        requires(liftSubsystem);
        switch (target) {
	    	case Bottom:
	    		targetPotentiometerValue = Robot.calibratorData.getLiftBottom();
	    		break;
	    	case Switch:
	    		targetPotentiometerValue = Robot.calibratorData.getLiftSwitch();
	    		break;
	    	case Scale:
	    		targetPotentiometerValue = Robot.calibratorData.getLiftScale();
	    		break;
        }
    }

    /**
     * What happens on initialize, does nothing
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void initialize() {
    	double currentPotentiometerValue = liftSubsystem.getPotentiometerValue();
    	if(targetPotentiometerValue < currentPotentiometerValue) {
    		liftSubsystem.raiseScissor();
    	} else if(targetPotentiometerValue > currentPotentiometerValue) {
    		liftSubsystem.lowerScissor();
    		lowering = true;
    	} else end();
    	LOG.info("Lowering Lift to potentiometer value of " + targetPotentiometerValue);
    }

    /**
     * What happens while the command is running, moves the motor
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void execute() {
    	if(lowering) {
	        if (counter % (onTime + offTime) >= offTime) {
	            LiftSubsystem.lowerScissor();
	        } else {
	            LiftSubsystem.liftRelay.set(Relay.Value.kOff);
	        }
	        counter++;
    	}
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
    	return Math.abs(liftSubsystem.getPotentiometerValue() - targetPotentiometerValue) < threshold || manualFinished;
    }

    /**
     * Turns off the motor when the command ends
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void end() {
    	manualFinished = true;
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
