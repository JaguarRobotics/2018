package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.log.Logger;

/**
 * Raises cube
 * 
 * @author Evan
 * @version 2018
 * @since 2018
 */
public class RaiseIntake extends IntakeCommandBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG  = new Logger();
    private static final long   TIME = 800;

    @Override
    protected void doIt() {
        intakeSubsystem.raiseIntake();
        LOG.info("Intake Raised");
    }

    public RaiseIntake() {
        super(TIME);
    }
}
