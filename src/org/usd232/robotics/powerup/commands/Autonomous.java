package org.usd232.robotics.powerup.commands;

import org.usd232.robotics.powerup.ISpeedFunction;
import org.usd232.robotics.powerup.drive.Delay;
import org.usd232.robotics.powerup.drive.DriveForward;
import org.usd232.robotics.powerup.drive.DriveTurn;
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
        ISpeedFunction speed = t->Math.min(0.8, 9.323308271 * t * t * t - 18.42105263 * t * t + 8.897744361 * t + 0.6);
        for (int i = 0; i < 400; ++i) {
            addSequential(new DriveForward(speed, 48, 0.1, 0.01));
            addSequential(new Delay(1000));
            addSequential(new DriveTurn(speed, Math.PI / 2));
            addSequential(new Delay(1000));
        }
    }
}
