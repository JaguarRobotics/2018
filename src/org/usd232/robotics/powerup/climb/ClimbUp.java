package org.usd232.robotics.powerup.climb;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import edu.wpi.first.wpilibj.Relay;

/**
 * Makes The Robot Climb Up VIA A Winch
 *
 * @author Brian Parks
 * @since 2018
 * @version 2018
 */
public class ClimbUp extends CommandBase {
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
        IO.winchRelay.set(Relay.Value.kForward);
    }

    @Override
    protected void end() {
        IO.winchRelay.set(Relay.Value.kOff);
    }

    @Override
    protected void interrupted() {
        IO.winchRelay.set(Relay.Value.kOff);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
