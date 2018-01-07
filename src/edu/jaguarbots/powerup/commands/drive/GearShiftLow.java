package edu.jaguarbots.powerup.commands.drive;

import edu.jagbots.powerup.Robot.Gear;
import edu.jaguarbots.powerup.commands.CommandBase;
import edu.jaguarbots.powerup.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Shifts the robot into low gear.
 *
 * @author Max K
 * @since 2017
 */
public class GearShiftLow extends CommandBase {
    /**
     * Shifts the robot into low gear.
     */
    public GearShiftLow() {
	requires(driveSubsystem);
    }
    @Override
    protected void initialize() {
	DriveSubsystem.gearShiftLow();
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
	SmartDashboard.putString("Gear", Gear.Low.toString());
    }
    @Override
    protected void interrupted() {
    }
}
