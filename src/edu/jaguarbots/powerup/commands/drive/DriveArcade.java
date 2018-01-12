package edu.jaguarbots.powerup.commands.drive;

import edu.jaguarbots.powerup.commands.CommandBase;

/**
 * Drives the robot in teleop based on one joystick
 * 
 * @author unknown
 * @since 2017
 * @version 2018
 */
public class DriveArcade extends CommandBase {
    /**
     * Drives the robot in teleop based on one joystick
     */
    public DriveArcade() {
	requires(driveSubsystem);
    }
    /**
     * left motor power to pass to the driveSubsystem.driveTank().
     * 
     * @since 2017
     * @version 2018
     */
    double left;
    /**
     * right motor power to pass to the driveSubsystem.driveTank().
     * 
     * @since 2017
     * @version 2018
     */
    double right;
    /**
     * the percentage of the x axis push amount on the joystick
     * 
     * @since 2017
     * @version 2018
     */
    double stickX;
    /**
     * the percentage of the y axis push amount on the joystick
     * 
     * @since 2017
     * @version 2018
     */
    double stickY;
    @Override
    /**
     * What happens when you initalize DriveArcade
     * 
     * @since 2017
     * @version 2018
     */
    protected void initialize() {
    }
    /**
     * What happens while isFinished is returning false
     * 
     * @since 2017
     * @version 2018
     */
    @Override
    protected void execute() {
	stickX = oi.Joystick1.getX();
	stickY = oi.Joystick1.getY();
	left = stickY + ((stickX < 0) ? stickX : 0);
	right = stickY - ((stickX > 0) ? stickX : 0);
	driveSubsystem.driveTank(-left, -right);
    }
    /**
     * Determines whether or not the DriveArcade is done
     * 
     * @since 2017
     * @version 2018
     * @return always false since driving is never done
     */
    @Override
    protected boolean isFinished() {
	return false;
    }
    /**
     * What happens when isFinished returns true which it never will for this
     * 
     * @since 2017
     * @version 2018
     */
    @Override
    protected void end() {
    }
    /**
     * What happens when the code is interrupted
     * 
     * @since 2017
     * @version 2018
     */
    @Override
    protected void interrupted() {
    }
}
