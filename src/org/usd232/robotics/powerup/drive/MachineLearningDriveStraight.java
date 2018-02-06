package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.autonomous.Point;

public class MachineLearningDriveStraight extends MachineLearningDrive {
    private double distance;
    private double startX;
    private double startY;
    private double angle;

    @Override
    protected Point calculateFormula(double t) {
        return new Point(startX + distance * Math.cos(angle) * t, startY + distance * Math.sin(angle) * t);
    }

    @Override
    protected Point closestCurvePointTo(double x, double y) {
        double dx = x - startX;
        double dy = y - startY;
        double distance = (Math.PI / 2 - angle + Math.atan2(dy, dx)) * Math.sqrt(dx * dx + dy * dy);
        return new Point(startX + distance * Math.cos(angle), startY + distance * Math.sin(angle));
    }

    @Override
    protected void initialize() {
        startX = locationSubsystem.getX();
        startY = locationSubsystem.getY();
        angle = locationSubsystem.getAngle();
        super.initialize();
    }

    @Override
    protected boolean isFinished() {
        double x = locationSubsystem.getX() - startX;
        double y = locationSubsystem.getY() - startY;
        double linearDistanceTraveled = x * Math.sin(angle) + y * Math.cos(angle);
        return linearDistanceTraveled >= distance;
    }

    public MachineLearningDriveStraight(double speed, double inches) {
        super(speed);
        distance = inches;
    }
}
