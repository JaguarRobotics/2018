package org.usd232.robotics.powerup.subsystems;

/**
 * Handles manipulation of power cube
 * 
 * @author Evan, Brian
 * @since 2018
 * @version 2018
 */
public class IntakeSubsystem extends SubsystemBase {
    public IntakeSubsystem() {
    }

    /**
     * Drop cube
     * 
     * @since 2018
     * @version 2018
     */
    public void dropCube() {
        grabSolenoid.set(true);
    }

    /**
     * Grabs cube
     * 
     * @since 2018
     * @version 2018
     */
    public void grabCube() {
        grabSolenoid.set(false);
    }

    /**
     * Lowers power cube
     * 
     * @since 2018
     * @version 2018
     */
    public void lowerIntake() {
        raiseIntakeSolenoid.set(true);
    }

    /**
     * Raises power cube
     * 
     * @since 2018
     * @version 2018
     */
    public void raiseIntake() {
        raiseIntakeSolenoid.set(false);
    }

    @Override
    protected void initDefaultCommand() {
    }
}
