package org.usd232.robotics.powerup.climb;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import edu.wpi.first.wpilibj.Relay;

/**
 * Makes The Robot Climb Down VIA A Winch
 *
 * @author Brian Parks
 * @since 2018
 * @version 2018
 */
public class ClimbDown extends CommandBase {
    private static final Logger LOG = new Logger();

    /**
     * Makes Robot Climb Down With The Winch
     * 
     * @since 2018
     * @version 2018
     */
    public ClimbDown() {
    }

    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            LOG.info("Running Climb Down Execute");
            IO.winchRelay.set(Relay.Value.kReverse);
            IO.winchRelay.set(Relay.Value.kForward);
        });
    }

    @Override
    protected void end() {
        LOG.catchAll(()-> {
            IO.winchRelay.stopMotor();
        });
    }

    @Override
    protected void interrupted() {
        LOG.catchAll(()-> {
            IO.winchRelay.stopMotor();
        });
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
