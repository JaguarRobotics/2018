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
    public void grabCube() {
        // grabSolenoid.set(true);
    }

    /**
     * Releases cube
     * 
     * @since 2018
     * @version 2018
     */
    public void dropCube() {
         // grabSolenoid.set(false);
    }

    /**
     * Raises power cube
     * 
     * @since 2018
     * @version 2018
     */
    public void raiseIntake() {
        // liftSolenoid.set(true);
    }

    /**
     * Lowers power cube
     * 
     * @since 2018
     * @version 2018
     */
    public void lowerIntake() {
        // liftSolenoid.set(false);
    }

    @Override
    protected void initDefaultCommand() {
    }
}
