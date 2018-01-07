package edu.jaguarbots.powerup.commands.drive;

import edu.jagbots.powerup.Robot.Gear;
import edu.jaguarbots.powerup.commands.CommandBase;
import edu.jaguarbots.powerup.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Shifts the robot into high gear.
 *
 * @author Max K
 * @since 2017
 */
public class GearShiftHigh extends CommandBase {
    /**
     * Shifts the robot into high gear.
     */
    public GearShiftHigh() {
	requires(driveSubsystem);
    }
    @Override
    protected void initialize() {
	DriveSubsystem.gearShiftHigh();
    }
    @Override
    protected void execute() {
    }
    @Override
    protected boolean isFinished() {
	return true;
    }
    @Override
    protected void end() {
	SmartDashboard.putString("Gear", Gear.High.toString());
    }
    @Override
    protected void interrupted() {
    }
}
