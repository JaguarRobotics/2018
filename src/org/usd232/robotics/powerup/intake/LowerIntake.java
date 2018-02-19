package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.log.Logger;

/**
 * Lowers cube
 * 
 * @author Evan
 * @version 2018
 * @since 2018
 */
public class LowerIntake extends IntakeCommandBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG  = new Logger();
    public static final long    TIME = 800;

    @Override
    protected void doIt() {
        intakeSubsystem.lowerIntake();
        LOG.info("Intake Lowered");
    }

    public LowerIntake() {
        super(TIME);
    }
}
