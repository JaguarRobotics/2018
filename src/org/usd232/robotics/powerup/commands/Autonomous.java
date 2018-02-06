package org.usd232.robotics.powerup.commands;

import org.usd232.robotics.powerup.drive.MachineLearningDriveStraight;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The class that sets up the routes to run in autonomous
 * 
 * @author Brian, Cody
 * @since Always
 * @version 2018
 */
public class Autonomous extends CommandGroup {
    private static final double SPEED = 0.7;

    /**
     * This is what runs when you call autonomous
     * 
     * @since Always
     * @version 2018
     */
    public Autonomous() {
        addSequential(new MachineLearningDriveStraight(SPEED, 14 * 12));
    }
}
