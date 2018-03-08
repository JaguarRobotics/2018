package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.log.Logger;

/**
 * Drops a cube.
 * 
 * @author Zach, Brian, Evan
 * @version 2018
 * @since 2018
 */
public class DropCube extends IntakeCommandBase {
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
     * Runs the drop cube command.
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    public void doIt() {
        intakeSubsystem.dropCube();
        LOG.info("Dropped Cube");
    }

    /**
     * Runs the drop cube command.
     * 
     * @since 2018
     * @version 2018
     */
    public DropCube() {
        super(TIME);
    }
}
