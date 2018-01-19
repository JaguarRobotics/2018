package org.usd232.robotics.powerup.subsystems;

/**
 * Handles manipulation of power cube
 * 
 * @author Evan
 * @since 2018
 * @version 2018
 */
public class IntakeSubsystem extends SubsystemBase {
    public IntakeSubsystem() {
    }

    /**
     * Grabs cube
     * 
     * @since 2018
     * @version 2018
     */
    public void intakeGrabberIn() {
//        grabSolenoid.set(true);
    }

    /**
     * Releases cube
     * 
     * @since 2018
     * @version 2018
     */
    public void intakeGrabberOut() {
//        grabSolenoid.set(false);
    }

    /**
     * Raises power cube
     * 
     * @since 2018
     * @version 2018
     */
    public void intakeGrabberUp() {
//        liftSolenoid.set(true);
    }

    /**
     * Lowers power cube
     * 
     * @since 2018
     * @version 2018
     */
    public void intakeGrabberDown() {
//        liftSolenoid.set(false);
    }

    @Override
    protected void initDefaultCommand() {
    }
}
