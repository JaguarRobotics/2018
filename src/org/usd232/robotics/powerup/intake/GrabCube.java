package org.usd232.robotics.powerup.intake;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.IntakeSubsystem;

/**
 * Grabs cube
 * 
 * @author Evan
 * @version 2018
 * @since 2018
 */
public class GrabCube extends CommandBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();

    public GrabCube() {
        requires(intakeSubsystem);
    }

    @Override
    protected void initialize() {
        intakeSubsystem.grabCube();
        LOG.info("Cube Grabbed");
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        IntakeSubsystem.intakeOpenPosition = false;
    }

    @Override
    protected void interrupted() {
    }
}
