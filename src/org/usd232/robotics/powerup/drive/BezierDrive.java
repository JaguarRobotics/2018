package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.autonomous.BezierCurve;
import org.usd232.robotics.autonomous.ISpeedFunction;
import org.usd232.robotics.autonomous.Point;

/**
 * Drives in along a Bezier curve
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public class BezierDrive extends PiecewiseDrive {
    /**
     * Evaluates a bezier curve into an array of pieces
     * 
     * @param curve
     *            The curve to evaluate
     * @param pieces
     *            The number of pieces to evaluate it into
     * @return An array of points that makes up the pieces
     * @since 2018
     */
    private static Point[] evaluate(BezierCurve curve, int pieces) {
        if (pieces < 1) {
            throw new IllegalArgumentException("Curve must have at least one piece");
        }
        Point[] points = new Point[pieces + 1];
        double diff = 1.0 / (double) pieces;
        for (int i = 0; i <= pieces; ++i) {
            points[i] = curve.evaluate(diff * i);
        }
        return points;
    }

    /**
     * Default constructor
     * 
     * @param curve
     *            The curve to drive
     * @param accuracy
     *            The number of inches in distance that the robot must be within a point for it to be considered at that
     *            point
     * @param pieces
     *            The number of pieces to segment the curve into
     * @param speedFunc
     *            The speed that the robot should travel in
     * @since 2018
     */
    public BezierDrive(BezierCurve curve, double accuracy, int pieces, ISpeedFunction speedFunc) {
        super(evaluate(curve, pieces), accuracy, speedFunc);
    }
}
