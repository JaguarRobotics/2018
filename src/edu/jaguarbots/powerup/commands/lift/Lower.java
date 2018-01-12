package edu.jaguarbots.powerup.commands.lift;

import edu.jaguarbots.powerup.commands.CommandBase;
import edu.jaguarbots.powerup.subsystems.LiftSubsystem;
public class Lower extends CommandBase {
    /**
     * Value that we are lowering to
     */
    private static double lowerValue = 0;
    /**
     * Lowers the lift of the robot to specified potentiometer value
     * @param lowerValue
     */
    public Lower(double lowerValue) {
	Lower.lowerValue = lowerValue;
	requires(liftSubsystem);
    }
    @Override
    protected void initialize() {
    }
    @Override
    protected void execute() {
	LiftSubsystem.liftMotor.set(-1);
    }
    @Override
    protected boolean isFinished() {
	if(liftSubsystem.getPotentiometerValue() <= lowerValue) {
	    return true;
	} else {
	return false;
	}
    }
    @Override
    protected void end() {
	LiftSubsystem.liftMotor.set(0);
    }
    @Override
    protected void interrupted() {
	LiftSubsystem.liftMotor.set(0);
    }
}
