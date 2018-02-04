package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.autonomous.BezierCurve;
import org.usd232.robotics.autonomous.Point;

public class MachineLearningBezierDrive extends MachineLearningDrive {
    private final BezierCurve curve;
    private final double      width;
    private final double      height;

    @Override
    protected Point calculateFormula(double t) {
        Point pt = curve.evaluate(t);
        return new Point(pt.getX() * width, pt.getY() * height);
    }

    @Override
    protected boolean isFinished() {
        Point a = closestCurvePointTo(locationSubsystem.getX(), locationSubsystem.getY());
        Point b = calculateFormula(1);
        return a.getX() == b.getX() && a.getY() == b.getY();
    }

    public MachineLearningBezierDrive(BezierCurve curve, double width, double height) {
        this.curve = curve;
        this.width = width;
        this.height = height;
    }
}
