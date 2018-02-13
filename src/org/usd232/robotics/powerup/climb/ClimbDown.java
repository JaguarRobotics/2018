package org.usd232.robotics.powerup.climb;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import edu.wpi.first.wpilibj.Relay;

/**
 * Makes The Robot Climb Down VIA A Winch
 *
 * @author Brian Parks
 * @since 2018
 * @version 2018
 */
public class ClimbDown extends CommandBase {
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
        IO.winchRelay.set(Relay.Value.kReverse);
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
