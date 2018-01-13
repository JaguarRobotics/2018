package edu.jaguarbots.powerup.subsystems;

import edu.jaguarbots.powerup.RobotMap;

/**
 * Handles manipulation of power cube
 * @author Evan
 * @since 2018
 * @version 2018
 *
 */
public class IntakeSubsystem extends SubsystemBase {
	
	public IntakeSubsystem() {
		
	
	}
	
	/**
	 * Grabs cube
	 * @since 2018
	 * @version 2018
	 */
	public void intakeGrabberIn() {
		grabSol.set(true);
	}
	
	/**
	 * Releases cube
	 * @since 2018
	 * @version 2018
	 */
	public void intakeGrabberOut() {
		grabSol.set(false);
	}
	
	/**
	 * Raises power cube
	 * @since 2018
	 * @version 2018
	 */
	public void intakeGrabberUp() {
		liftSol.set(true);
	}
	
	/**
	 * Lowers power cube
	 * @since 2018
	 * @version 2018
	 */
	public void intakeGrabberDown() {
		liftSol.set(false);
	}
	
    @Override
    protected void initDefaultCommand() {
    	
    }
}
