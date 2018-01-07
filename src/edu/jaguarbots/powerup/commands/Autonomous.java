package edu.jaguarbots.powerup.commands;

import edu.jaguarbots.powerup.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The Code to run during the autonomous section of a match.
 * 
 * @author Brian, Nathan, Cody
 */
public class Autonomous extends CommandGroup {
    /**
     * This is the default autonomous, anything you put in here will end up running as auto in testing
     */
    @SuppressWarnings("unused")
    public Autonomous() {
        DriveSubsystem ds = CommandBase.driveSubsystem;
        boolean testing = false;
        if (testing) {
        }
    }

    /**
     * Selects autonomous route based on defense to cross, position in, and what goal to shoot in. See a picture below
     * <html><img src="https://puu.sh/tWete/63e013aebf.png"></img></html>
     * 
     * @param position
     *            enum: Left, Middle, or Right
     * @param middlePosition
     *            enum: Left, Right
     * @param doughnuts
     *            enum: Yes, No
     */
}
