package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.log.Logger;

/**
 * Drops cube
 * 
 * @author Evan
 * @version 2018
 * @since 2018
 */
public class DropCube extends IntakeCommandBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG  = new Logger();
    private static final long   TIME = 200;

    @Override
    public void doIt() {
        intakeSubsystem.dropCube();
        LOG.info("Dropped Cube");
    }

    public DropCube() {
        super(TIME);
    }
}
