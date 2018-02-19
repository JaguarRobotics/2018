package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.log.Logger;

/**
 * Grabs cube
 * 
 * @author Evan
 * @version 2018
 * @since 2018
 */
public class GrabCube extends IntakeCommandBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG  = new Logger();
    private static final long   TIME = 200;

    @Override
    protected void doIt() {
        LOG.catchAll(()-> {
            intakeSubsystem.grabCube();
            LOG.info("Cube Grabbed");
        });
    }

    public GrabCube() {
        super(TIME);
    }
}
