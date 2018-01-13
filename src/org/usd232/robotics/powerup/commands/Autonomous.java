package org.usd232.robotics.powerup.commands;

import org.usd232.robotics.powerup.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The Code to run during the autonomous section of a match.
 * 
 * @author Brian, Nathan, Cody
 * @since Always
 * @version 2018
 */
public class Autonomous extends CommandGroup {
    /**
     * This is what runs when you call autonomous
     * 
     * @since Always
     * @version 2018
     */
    @SuppressWarnings("unused")
    public Autonomous() {
        DriveSubsystem ds = CommandBase.driveSubsystem;
    }
}