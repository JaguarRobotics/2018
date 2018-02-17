package org.usd232.robotics.powerup.climb;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import edu.wpi.first.wpilibj.Relay;

/**
 * Makes The Robot Climb Up VIA A Winch
 *
 * @author Brian Parks
 * @since 2018
 * @version 2018
 */
public class ClimbUp extends CommandBase {
    private static final Logger LOG = new Logger();

    /**
     * Makes Robot Climb Up With The Winch
     * 
     * @since 2018
     * @version 2018
     */
    public ClimbUp() {
    }

    @Override
    protected void execute() {
        LOG.info("Running Climb Up Execute");
        IO.winchRelay.set(Relay.Value.kForward);
        IO.winchRelay.set(Relay.Value.kReverse);
    }

    @Override
    protected void end() {
        IO.winchRelay.stopMotor();
    }

    @Override
    protected void interrupted() {
        IO.winchRelay.stopMotor();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
