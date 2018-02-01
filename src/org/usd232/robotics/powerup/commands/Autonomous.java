package org.usd232.robotics.powerup.commands;

import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.RobotMap;
import org.usd232.robotics.powerup.RobotMap.Alliance;
import org.usd232.robotics.powerup.RobotMap.StartingPosition;
import org.usd232.robotics.powerup.drive.EncoderDrive;
import org.usd232.robotics.powerup.drive.EncoderTurn;
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
        int positiveSide = 1;// Change to -1 if wired backwards
        DriveSubsystem ds = CommandBase.driveSubsystem;
        StartingPosition pos = (StartingPosition) Robot.positionChooser.getSelected();
        Alliance alliance = (Alliance) Robot.allianceChooser.getSelected();
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        char[] sides = gameData.toCharArray();
        if (alliance.equals(Alliance.Red)) {
            if (pos.equals(RobotMap.StartingPosition.One)) {
                if (sides[1] == 'L') {
                    addSequential(new EncoderDrive(300));
                }
                if (sides[1] == 'R') {
                    addSequential(new EncoderDrive(206));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * 90)));
                    addSequential(new EncoderDrive(132));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * -90)));
                    addSequential(new EncoderDrive(32));
                }
            }
            if (pos.equals(RobotMap.StartingPosition.Two)) {
                if (sides[1] == 'L') {
                    addSequential(new EncoderDrive(60));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * -90)));
                    addSequential(new EncoderDrive(64));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * 90)));
                    addSequential(new EncoderDrive(160));
                }
                if (sides[1] == 'R') {
                    addSequential(new EncoderDrive(60));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * 90)));
                    addSequential(new EncoderDrive(90));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * -90)));
                    addSequential(new EncoderDrive(184));
                }
            }
            if (pos.equals(RobotMap.StartingPosition.Three)) {
                if (sides[1] == 'L') {
                    addSequential(new EncoderDrive(206));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * -90)));
                    addSequential(new EncoderDrive(132));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * 90)));
                    addSequential(new EncoderDrive(32));
                }
                if (sides[1] == 'R') {
                    addSequential(new EncoderDrive(300));
                }
            }
        }
        // Complete guess, probably not accurate
        if (alliance.equals(Alliance.Blue)) {
            if (pos.equals(RobotMap.StartingPosition.Three)) {
                if (sides[1] == 'L') {
                    addSequential(new EncoderDrive(300));
                }
                if (sides[1] == 'R') {
                    addSequential(new EncoderDrive(206));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * 90)));
                    addSequential(new EncoderDrive(132));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * -90)));
                    addSequential(new EncoderDrive(32));
                }
            }
            if (pos.equals(RobotMap.StartingPosition.Two)) {
                if (sides[1] == 'L') {
                    addSequential(new EncoderDrive(60));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * 90)));
                    addSequential(new EncoderDrive(64));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * -90)));
                    addSequential(new EncoderDrive(160));
                }
                if (sides[1] == 'R') {
                    addSequential(new EncoderDrive(60));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * -90)));
                    addSequential(new EncoderDrive(90));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * 90)));
                    addSequential(new EncoderDrive(184));
                }
            }
            if (pos.equals(RobotMap.StartingPosition.One)) {
                if (sides[1] == 'L') {
                    addSequential(new EncoderDrive(206));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * -90)));
                    addSequential(new EncoderDrive(132));
                    addSequential(new EncoderTurn(ds.getRadiansFromDegrees(positiveSide * 90)));
                    addSequential(new EncoderDrive(32));
                }
                if (sides[1] == 'R') {
                    addSequential(new EncoderDrive(300));
                }
            }
        }
    }
}
