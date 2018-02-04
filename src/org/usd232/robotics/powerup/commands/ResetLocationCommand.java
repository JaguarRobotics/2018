package org.usd232.robotics.powerup.commands;

public class ResetLocationCommand extends CommandBase {
	private static final double MINIMUM = 0.00000001;

	@Override
	protected void execute() {
		locationSubsystem.reset();
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(locationSubsystem.getX()) < MINIMUM && Math.abs(locationSubsystem.getY()) < MINIMUM
				&& Math.abs(locationSubsystem.getAngle()) < MINIMUM;
	}

	public ResetLocationCommand() {
		requires(locationSubsystem);
	}
}
