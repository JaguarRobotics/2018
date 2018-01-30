package org.usd232.robotics.powerup.commands;

import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.RobotMap.Alliance;
import org.usd232.robotics.powerup.RobotMap.StartingPosition;
import org.usd232.robotics.powerup.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The Code to run during the autonomous section of a match.
 * 
 * @author Brian, Cody
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
		StartingPosition pos = (StartingPosition) Robot.positionChooser.getSelected();
		Alliance alliance = (Alliance) Robot.allianceChooser.getSelected();
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		char[] sides = gameData.toCharArray();
		if (alliance.equals(Alliance.Blue)) {
			if (sides[0] == 'L') {
				if (sides[0] == 'L') {

				}
				if (sides[0] == 'R') {

				}
			}
			if (sides[0] == 'R') {
				if (sides[0] == 'L') {

				}
				if (sides[0] == 'R') {

				}
			}
		} else if(alliance.equals(Alliance.Red)) {
			if (sides[0] == 'L') {
				if (sides[0] == 'L') {

				}
				if (sides[0] == 'R') {

				}
			}
			if (sides[0] == 'R') {
				if (sides[0] == 'L') {

				}
				if (sides[0] == 'R') {

				}
			}
		}
	}
}