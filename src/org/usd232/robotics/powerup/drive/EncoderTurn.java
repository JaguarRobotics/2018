package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.commands.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turns the robot using the values on the encoders
 * 
 * @author Zach Deibert, Brian Parks, Kyle K
 * @since 2017
 * @version 2018
 */
public class EncoderTurn extends CommandBase {
    /**
     * The speed at which to turn
     * 
     * @since 2017
     * @version 2018
     */
    private final double speed;
    /**
     * Target arclength
     * 
     * @since 2017
     * @version 2018
     */
    private double       arclength;
    /**
     * The width of the wheel base
     * 
     * @since 2017
     * @version 2018
     */
    private final double WHEELBASE_WIDTH = 18.0;

    @Override
    /**
     * Resets the encoders and starts them again when the command runs
     * 
     * @since 2017
     * @version 2018
     */
    protected void initialize() {
        driveSubsystem.resetEncoders(true, true);
        driveSubsystem.startEncoders();
    }

    @Override
    /**
     * turns the robot whichever way you tell it
     * 
     * @since 2017
     * @version 2018
     */
    protected void execute() {
        SmartDashboard.putNumber("EncoderLeft", CommandBase.driveSubsystem.getEncoderLeft());
        SmartDashboard.putNumber("EncoderRight", CommandBase.driveSubsystem.getEncoderRight());
        driveSubsystem.driveTank(-speed, speed);
    }

    @Override
    /**
     * Determines if the robot has turned far enough
     * 
     * @since 2017
     * @version 2018
     */
    protected boolean isFinished() {
        double left = Math.abs(driveSubsystem.getEncoderLeft());
        double right = Math.abs(driveSubsystem.getEncoderRight());
        return Math.max(left, right) >= arclength;
    }

    @Override
    /**
     * Stops the robot
     * 
     * @since 2017
     * @version 2018
     */
    protected void end() {
        driveSubsystem.driveTank(0, 0);
    }

    @Override
    /**
     * If the command is interrupted it runs the end method to stop the robot
     * 
     * @since 2017
     * @version 2018
     */
    protected void interrupted() {
        end();
    }

    /**
     * Default constructor
     * 
     * @param angle
     *            The angle to turn in radians (positive is to the left, negative is to the right)
     * @param speed
     *            The speed at which to turn
     * @since 2017
     * @version 2018
     */
    public EncoderTurn(double angle, double speed) {
        requires(driveSubsystem);
        this.speed = (angle < 0) ? -1 * Math.abs(speed) : Math.abs(speed);
        this.arclength = Math.abs(angle * WHEELBASE_WIDTH / 2);
    }

    /**
     * Turns the robot at a speed of 0.7
     * 
     * @param angle
     *            The angle to turn in radians (positive is to the left, negative is to the right)
     * @since 2017
     * @version 2018
     */
    public EncoderTurn(double angle) {
        this(angle, 0.7);
    }
}
