package org.usd232.robotics.powerup.commands;

import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.RobotMap;
import org.usd232.robotics.powerup.RobotMap.Alliance;
import org.usd232.robotics.powerup.RobotMap.StartingPosition;
import org.usd232.robotics.powerup.drive.GyroTurn;
import org.usd232.robotics.powerup.drive.MachineLearningDriveStraight;
import org.usd232.robotics.powerup.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.DriverStation;
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
    @SuppressWarnings("unused")
    public Autonomous() {
        double ROBOT_LENGTH = 24.5;
        int positiveSide = -1;// Change to -1 if wired backwards
        DriveSubsystem ds = CommandBase.driveSubsystem;
        StartingPosition pos = (StartingPosition) Robot.positionChooser.getSelected();
        Alliance alliance = (Alliance) Robot.allianceChooser.getSelected();
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        char[] sides = gameData.toCharArray();
        System.out.println(sides.length);
        if (alliance.equals(Alliance.Red)) {
            if (pos.equals(RobotMap.StartingPosition.One)) {
                if (sides[1] == 'L') {
                    addSequential(new MachineLearningDriveStraight(SPEED, 300 - ROBOT_LENGTH));
                }
                if (sides[1] == 'R') {
                    addSequential(new MachineLearningDriveStraight(SPEED, 206 - ROBOT_LENGTH));
                    addSequential(new GyroTurn(90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 132));
                    addSequential(new GyroTurn(90 * -positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 32));
                }
            }
            if (pos.equals(RobotMap.StartingPosition.Two)) {
                if (sides[1] == 'L') {
                    addSequential(new MachineLearningDriveStraight(SPEED, 60 - ROBOT_LENGTH));
                    addSequential(new GyroTurn(-90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 64));
                    addSequential(new GyroTurn(90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 160));
                }
                if (sides[1] == 'R') {
                    addSequential(new MachineLearningDriveStraight(SPEED, 60 - ROBOT_LENGTH));
                    addSequential(new GyroTurn(90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 90));
                    addSequential(new GyroTurn(-90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 184));
                }
            }
            if (pos.equals(RobotMap.StartingPosition.Three)) {
                if (sides[1] == 'L') {
                    addSequential(new MachineLearningDriveStraight(SPEED, 206 - ROBOT_LENGTH));
                    addSequential(new GyroTurn(-90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 132));
                    addSequential(new GyroTurn(90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 32));
                }
                if (sides[1] == 'R') {
                    addSequential(new MachineLearningDriveStraight(SPEED, 300 - ROBOT_LENGTH));
                }
            }
        }
        // Complete guess, probably not accurate
        if (alliance.equals(Alliance.Blue)) {
            if (pos.equals(RobotMap.StartingPosition.Three)) {
                if (sides[1] == 'L') {
                    addSequential(new MachineLearningDriveStraight(SPEED, 300 - ROBOT_LENGTH));
                }
                if (sides[1] == 'R') {
                    addSequential(new MachineLearningDriveStraight(SPEED, 206 - ROBOT_LENGTH));
                    addSequential(new GyroTurn(90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 132));
                    addSequential(new GyroTurn(-90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 32));
                }
            }
            if (pos.equals(RobotMap.StartingPosition.Two)) {
                if (sides[1] == 'L') {
                    addSequential(new MachineLearningDriveStraight(SPEED, 60 - ROBOT_LENGTH));
                    addSequential(new GyroTurn(90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 64));
                    addSequential(new GyroTurn(-90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 160));
                }
                if (sides[1] == 'R') {
                    addSequential(new MachineLearningDriveStraight(SPEED, 60 - ROBOT_LENGTH));
                    addSequential(new GyroTurn(-90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 90));
                    addSequential(new GyroTurn(90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 184));
                }
            }
            if (pos.equals(RobotMap.StartingPosition.One)) {
                if (sides[1] == 'L') {
                    addSequential(new MachineLearningDriveStraight(SPEED, 206 - ROBOT_LENGTH));
                    addSequential(new GyroTurn(-90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 132));
                    addSequential(new GyroTurn(90 * positiveSide));
                    addSequential(new MachineLearningDriveStraight(SPEED, 32));
                }
                if (sides[1] == 'R') {
                    addSequential(new MachineLearningDriveStraight(SPEED, 300 - ROBOT_LENGTH));
                }
            }
        }
    }
}
