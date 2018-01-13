package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Shifts the robot into low gear.
 *
 * @author Max K
 * @since 2017
 * @version 2018
 */
public class GearShiftLow extends CommandBase {
    /**
     * Shifts the robot into low gear.
     * 
     * @since 2017
     * @version 2018
     */
    public GearShiftLow() {
        requires(driveSubsystem);
    }

    @Override
    /**
     * Sets the robot to low gear
     * 
     * @since 2017
     * @version 2018
     */
    protected void initialize() {
        DriveSubsystem.gearShiftLow();
    }

    @Override
    /**
     * Runs while command is running, does nothing
     * 
     * @since 2017
     * @version 2018
     */
    protected void execute() {
    }

    @Override
    /**
     * Determines if the command is done which is instantly true
     * 
     * @return true since it is instant
     * @since 2017
     * @version 2018
     */
    protected boolean isFinished() {
        return true;
    }

    @Override
    /**
     * Runs once the robot is in high gear, updates smart dashboard
     * 
     * @since 2017
     * @version 2018
     */
    protected void end() {
        SmartDashboard.putString("Gear", Gear.Low.toString());
    }

    @Override
    /**
     * Runs if the command gets interrupted, does nothing
     * 
     * @since 2017
     * @version 2018
     */
    protected void interrupted() {
    }
}
