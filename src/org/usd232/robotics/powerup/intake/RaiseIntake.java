package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.log.Logger;

/**
 * Raises the intake.
 * 
 * @author Zach, Brian, Evan
 * @version 2018
 * @since 2018
 */
public class RaiseIntake extends IntakeCommandBase {
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
    private static final long   TIME = 800;

    /**
     * Raises the intake of the robot.
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void doIt() {
        intakeSubsystem.raiseIntake();
        LOG.info("Intake Raised");
    }

    /**
     * Raises the intake of the robot.
     * 
     * @since 2018
     * @version 2018
     */
    public RaiseIntake() {
        super(TIME);
    }
}
