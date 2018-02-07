package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.commands.CommandBase;

public class LocationSubsystem extends SubsystemBase {
	private static final double WIDTH = 18;
	private static final double CENTER_OF_MASS = 0.5;
	private double angleOffset;
	private double lastS1;
	private double lastS2;
	private double lastTheta;
	private double x;
	private double y;
	private double theta;

	public LocationSubsystem() {
		reset();
	}

	@Override
	protected void initDefaultCommand() {
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAngle() {
		return theta;
	}

	public void reset() {
		CommandBase.driveSubsystem.resetEncoders(true, true);
		angleOffset = gyro.getAngle() * Math.PI / 180 - Math.PI / 2;
		x = 0;
		y = 0;
	}

	public void updateValues() {
		double s1 = CommandBase.driveSubsystem.getDistanceInInches(-CommandBase.driveSubsystem.getEncoderLeft());
		double s2 = CommandBase.driveSubsystem.getDistanceInInches(CommandBase.driveSubsystem.getEncoderRight());
		theta = gyro.getAngle() * Math.PI / 180 - angleOffset;
		double ds1 = s1 - lastS1;
		double ds2 = s2 - lastS2;
		double dtheta = theta - lastTheta;
		lastS1 = s1;
		lastS2 = s2;
		lastTheta = theta;
		double xPart;
		double yPart;
		if (dtheta == 0) {
			xPart = 0;
			yPart = (ds1 + ds2) / 2;
		} else {
			double coefficient = ((ds1 + ds2) / dtheta - WIDTH) / 2 + WIDTH * CENTER_OF_MASS;
			xPart = -coefficient * Math.sin(dtheta);
			yPart = coefficient * (1 - Math.cos(dtheta));
		}
		double sin = Math.sin(theta);
		double cos = Math.cos(theta);
		x += xPart * sin + yPart * cos;
		y += xPart * cos + yPart * sin;
	}
	/**
	 * Gets the angle based on where the encoders have gone.
	 */
	public void getEncoderAngle() {
	    
	}
}
