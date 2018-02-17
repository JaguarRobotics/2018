package org.usd232.robotics.powerup.climb;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import edu.wpi.first.wpilibj.Relay;

/**
 * Stops The Robot Climb
 *
 * @author Brian Parks
 * @since 2018
 * @version 2018
 */
public class ClimbStop extends CommandBase {
    private static final Logger LOG = new Logger();

    /**
     * Makes Robot Climb Up With The Winch
     * 
     * @since 2018
     * @version 2018
     */
    public ClimbStop() {
    }

    @Override
    protected void execute() {
        LOG.info("Running Climb Stop Execute");
        IO.winchRelay.set(Relay.Value.kOff);
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
