package edu.jaguarbots.powerup.commands.lift;

import edu.jaguarbots.powerup.commands.CommandBase;
import edu.jaguarbots.powerup.subsystems.LiftSubsystem;
public class Raise extends CommandBase {
    /**
     * Value that we are raising to
     */
    private static double raiseValue = 0;
    /**
     * Raises the lift of the robot to specified potentiometer value
     * @param raiseValue
     */
    public Raise(double raiseValue) {
	Raise.raiseValue = raiseValue;
	requires(liftSubsystem);
    }
    @Override
    protected void initialize() {
    }
    @Override
    protected void execute() {
	LiftSubsystem.liftMotor.set(1);
    }
    @Override
    protected boolean isFinished() {
	if(liftSubsystem.getPotentiometerValue() >= raiseValue) {
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
