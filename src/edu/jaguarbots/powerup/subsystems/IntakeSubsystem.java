package edu.jaguarbots.powerup.subsystems;

import edu.jaguarbots.powerup.RobotMap;

public class IntakeSubsystem extends SubsystemBase {
	
	private Solenoid grabSol = new Solenoid(RobotMap.solIntakeGrab);
	private Solenoid liftSol = new Solenoid(RobotMap.solIntakeLift);
	
	public IntakeSubsystem() {
		
	
	}
	
	public void intakeGrabberIn() {
		grabSol.set(true);
	}
	
	public void intakeGrabberOut() {
		grabSol.set(false);
	}
	
	public void intakeGrabberUp() {
		liftSol.set(true);
	}
	
	public void intakeGrabberDown() {
		liftSol.set(false);
	}
	
    @Override
    protected void initDefaultCommand() {
    	
    }
}
