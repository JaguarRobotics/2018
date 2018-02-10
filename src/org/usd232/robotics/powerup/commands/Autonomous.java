package org.usd232.robotics.powerup.commands;

import org.usd232.robotics.powerup.drive.DriveForward;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The class that sets up the routes to run in autonomous
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public class Autonomous extends CommandGroup {
    /**
     * Sets up this {@link CommandGroup} to run the autonomous commands
     * 
     * @since 2018
     * @version 2018
     */
    public Autonomous() {
        addSequential(new DriveForward(
                        t->Math.min(0.8, 9.323308271 * t * t * t - 18.42105263 * t * t + 8.897744361 * t + 0.6),
                        22 * 12, 0.1, 0.01));
    }
}
