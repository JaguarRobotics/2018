package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;

/**
 * Turns the robot using the values on the gyro
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class GyroTurn extends CommandBase {
    /**
     * The speed at which to turn
     * 
     * @since 2018
     * @version 2018
     */
    private final double speed;
    /**
     * Target arclength
     * 
     * @since 2018
     * @version 2018
     */
    private double       arclength;
    /**
     * The width of the wheel base
     * 
     * @since 2018
     * @version 2018
     */
    private final double WHEELBASE_WIDTH = 18.0;

    @Override
    /**
     * Resets the gyro and starts them again when the command runs
     * 
     * @since 2018
     * @version 2018
     */
    protected void initialize() {
        IO.gyro.reset();
    }

    @Override
    /**
     * turns the robot whichever way you tell it
     * 
     * @since 2018
     * @version 2018
     */
    protected void execute() {
        driveSubsystem.driveTank(-speed, speed);
    }

    @Override
    /**
     * Determines if the robot has turned far enough
     * 
     * @since 2018
     * @version 2018
     */
    protected boolean isFinished() {
        double gyroValue = Math.abs(IO.gyro.getAngle());
        System.out.println("Gyro says " + IO.gyro.getAngle() + " And we should be going to " + arclength);
        return Math.abs(gyroValue) >= Math.abs(arclength);
    }

    @Override
    /**
     * Stops the robot
     * 
     * @since 2018
     * @version 2018
     */
    protected void end() {
        driveSubsystem.driveTank(0, 0);
    }

    @Override
    /**
     * If the command is interrupted it runs the end method to stop the robot
     * 
     * @since 2018
     * @version 2018
     */
    protected void interrupted() {
        end();
    }

    /**
     * Default constructor
     * 
     * @param angle
     *            The angle to turn in degrees (positive is to the left, negative is to the right)
     * @param speed
     *            The speed at which to turn
     * @since 2018
     * @version 2018
     */
    public GyroTurn(double angle, double speed) {
        requires(driveSubsystem);
        this.speed = (angle < 0) ? -1 * Math.abs(speed) : Math.abs(speed);
        this.arclength = angle;
    }

    /**
     * Turns the robot at a speed of 0.7
     * 
     * @param angle
     *            The angle to turn in degrees (positive is to the left, negative is to the right)
     * @since 2018
     * @version 2018
     */
    public GyroTurn(double angle) {
        this(angle, 0.6);
    }
}
