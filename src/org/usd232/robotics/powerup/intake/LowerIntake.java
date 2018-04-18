package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.log.Logger;

/**
 * Lowers the intake.
 * 
 * @author Zach, Brian, Evan
 * @version 2018
 * @since 2018
 */
public class LowerIntake extends IntakeCommandBase {
    /**
     * The Logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG  = new Logger();
    /**
     * The time it takes for the command to run.
     * 
     * @since 2018
     * @version 2018
     */
    public static final long    TIME = 800;

    /**
     * Lowers the intake.
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void doIt() {
        intakeSubsystem.lowerIntake();
        LOG.info("Intake Lowered");
    }

    /**
     * Command to Lower the intake.
     * 
     * @since 2018
     * @version 2018
     */
    public LowerIntake() {
        super(TIME);
    }
}
