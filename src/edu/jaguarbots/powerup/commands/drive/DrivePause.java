package edu.jaguarbots.powerup.commands.drive;

import edu.jaguarbots.powerup.commands.CommandBase;

/**
 * Pauses the specified number of milliseconds (only used in Auto)
 * 
 * @author Brian Parks
 * @since 2017
 * @version 2018
 */
public class DrivePause extends CommandBase {
    /**
     * Time to wait in milliseconds
     * 
     * @since 2017
     * @version 2018
     */
    private long waitTime;
    /**
     * Time in milliseconds that this command was initialized
     * 
     * @since 2017
     * @version 2018
     */
    private long startTime;
    /**
     * Pauses the robot for the specified number of milliseconds
     * 
     * @since 2017
     * @version 2018
     */
    public DrivePause(long waitTime) {
	requires(driveSubsystem);
	this.waitTime = waitTime;
    }
    /**
     * Gets the current system time when the command starts
     * 
     * @since 2017
     * @version 2018
     */
    @Override
    protected void initialize() {
	startTime = System.currentTimeMillis();
    }
    /**
     * Does nothing since thats what a pause is
     * 
     * @since 2017
     * @version 2018
     */
    @Override
    protected void execute() {
    }
    /**
     * Determines if the amount of time is up for the pause
     * 
     * @since 2017
     * @version 2018
     */
    @Override
    protected boolean isFinished() {
	long now = System.currentTimeMillis();
	return (now - startTime) > waitTime;
    }
    /**
     * What the robot does when the command is over
     * 
     * @since 2017
     * @version 2018
     */
    @Override
    protected void end() {
    }
    /**
     * What the robot does if the command gets interrupted
     * 
     * @since 2017
     * @version 2018
     */
    @Override
    protected void interrupted() {
    }
}
