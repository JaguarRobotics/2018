package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.commands.CommandBase;

public class TestDrive extends CommandBase {
	private final double speed;
	
	@Override
	protected void execute() {
		driveSubsystem.driveTank(speed, speed);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
	public TestDrive(double speed) {
		this.speed = speed;
		requires(driveSubsystem);
	}
}
