package org.usd232.robotics.powerup.subsystems;

/**
 * Handles manipulation of power cube
 * 
 * @author Evan
 * @since 2018
 * @version 2018
 */
public class IntakeSubsystem extends SubsystemBase {
    public static boolean intakeOpenPosition = true;
    public static boolean intakeDown = true;
    public IntakeSubsystem() {
    }

    /**
     * Grabs cube
     * 
     * @since 2018
     * @version 2018
     */
    public void grabCube() {
        grabSolenoid.set(true);
        intakeOpenPosition = false;
    }

    /**
     * Releases cube
     * 
     * @since 2018
     * @version 2018
     */
    public void dropCube() {
        grabSolenoid.set(false);
        intakeOpenPosition = true;
    }

    /**
     * Raises power cube
     * 
     * @since 2018
     * @version 2018
     */
    public void raiseIntake() {
        liftSolenoid.set(true);
        intakeDown = false;
    }

    /**
     * Lowers power cube
     * 
     * @since 2018
     * @version 2018
     */
    public void lowerIntake() {
        liftSolenoid.set(false);
        intakeDown = false;
    }
    @Override
    protected void initDefaultCommand() {
    }
}
