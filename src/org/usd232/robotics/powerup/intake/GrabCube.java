package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.log.Logger;

/**
 * Grabs a cube.
 * 
 * @author Brian, Zach, Evan
 * @version 2018
 * @since 2018
 */
public class GrabCube extends IntakeCommandBase {
    /**
     * The Logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG  = new Logger();
    /**
     * The time value that it takes the robot to do the command.
     * 
     * @since 2018
     * @version 2018
     */
    private static final long   TIME = 200;

    /**
     * Runs the grab cube command.
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void doIt() {
        LOG.catchAll(()-> {
            intakeSubsystem.grabCube();
            LOG.info("Cube Grabbed");
        });
    }

    /**
     * Runs the grab cube command.
     * 
     * @since 2018
     * @version 2018
     */
    public GrabCube() {
        super(TIME);
    }
}
