package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.commands.CommandBase;

public class LocationSubsystem extends SubsystemBase {
    private static final double WIDTH = 18;
    private static final double CENTER_OF_MASS = 0.5;
    private final double angleOffset;
    private double lastS1;
    private double lastS2;
    private double lastTheta;
    private double x;
    private double y;
    private double theta;

    public LocationSubsystem() {
        CommandBase.driveSubsystem.resetEncoders(true, true);
        angleOffset = gyro.getAngle();
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

    public void updateValues() {
        double s1 = CommandBase.driveSubsystem.getDistanceInInches(CommandBase.driveSubsystem.getEncoderLeft());
        double s2 = CommandBase.driveSubsystem.getDistanceInInches(CommandBase.driveSubsystem.getEncoderRight());
        theta = getAngle() - angleOffset;
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
            xPart = coefficient * Math.sin(dtheta);
            yPart = coefficient * (1 - Math.cos(dtheta));
        }
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);
        x += xPart * cos + yPart * sin;
        y += xPart * sin + yPart * cos;
    }
}
