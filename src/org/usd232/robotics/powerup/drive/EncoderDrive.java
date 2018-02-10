package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives the robot based on a distance gotten from encoders.
 * 
 * @author Jack, Zach Deibert, Brian Parks, Kyle K
 * @since 2016
 * @version 2018
 */
public class EncoderDrive extends CommandBase {
    /**
     * The cutoff value of the motor where the robot will no longer move
     * 
     * @since 2017
     * @version 2018
     */
    private static final double CUTOFF_VALUE = 0.4;
    /**
     * The distance to travel
     * 
     * @since 2016
     * @version 2018
     */
    private double              distance;
    /**
     * The speed to travel at (between 0 and 1)
     * 
     * @since 2016
     * @version 2018
     */
    private double              speed;

    /**
     * Drives a certain distance at a speed of .7
     * 
     * @param distance
     *            to drive in inches.
     * @since 2016
     * @version 2018
     */
    public EncoderDrive(double distance) {
        this(distance, (distance < 0) ? -0.7 : 0.7);
    }

    /**
     * Drives a certain distance at a certain speed.
     * 
     * @param distance
     *            to travel in inches.
     * @param speed
     *            to run motors at.
     * @since 2016
     * @version 2018
     */
    public EncoderDrive(double distance, double speed) {
        requires(driveSubsystem);
        this.distance = distance;
        this.speed = (distance < 0) ? -1 * Math.abs(speed) : Math.abs(speed);
    }

    /**
     * Gets the distance the encoders have gone
     * 
     * @return returns a double equal to encoder ticks traveled (left+right)
     * @since 2016
     * @version 2018
     */
    private double distanceTraveled() {
        return (Math.abs(driveSubsystem.getEncoderLeft()) + Math.abs(driveSubsystem.getEncoderRight())) / 2;
    }

    /**
     * When called resets and restarts the encoders
     * 
     * @since 2016
     * @version 2018
     */
    @Override
    protected void initialize() {
        DriveSubsystem.counter = 0;
        driveSubsystem.resetEncoders(true, true);
        driveSubsystem.startEncoders();
        //System.out.println("Initial gyro value: " + IO.gyro.getAngle());
    }

    /**
     * Drives the robot calculating if one motor is stronger than another
     * 
     * @since 2016
     * @version 2018
     */
    @Override
    protected void execute() {
        boolean correctMotors = true;
        double[] powers = driveSubsystem.getMotorPowers(0);
        //System.out.println("Left Motor: " + powers[0] + " Right Motor " + powers[1]);
        double adjSpeed = Math.min(((distance - distanceTraveled()) / distance) * (1 - CUTOFF_VALUE) + CUTOFF_VALUE,
                        speed);
        if (correctMotors) {
            driveSubsystem.driveTank(adjSpeed * powers[0], adjSpeed * powers[1]);
        } else {
            driveSubsystem.driveTank(speed, speed);
            SmartDashboard.putNumber("EncoderLeft", CommandBase.driveSubsystem.getEncoderLeft());
            SmartDashboard.putNumber("EncoderRight", CommandBase.driveSubsystem.getEncoderRight());
        }
    }

    /**
     * Determines if the robot has gone the disance that it is supposed to
     * 
     * @return true if robot has gone required distance
     * @since 2016
     * @version 2018
     */
    @Override
    protected boolean isFinished() {
        boolean isFinished = false;
        if (speed > 0) {
            isFinished = distanceTraveled() >= distance;
        } else {
            isFinished = distanceTraveled() <= distance;
        }
        return isFinished;
    }

    @Override
    /**
     * Stops the robot when isFinished returns true
     * 
     * @since 2016
     * @version 2018
     */
    protected void end() {
        driveSubsystem.driveTank(0, 0);
    }

    /**
     * What runs if the command gets interrupted
     * 
     * @since 2016
     * @version 2018
     */
    @Override
    protected void interrupted() {
        end();
    }
}
