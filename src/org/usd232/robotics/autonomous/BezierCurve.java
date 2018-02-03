package org.usd232.robotics.autonomous;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Represents and calculates points along a Bezier curve.
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public class BezierCurve implements IAutonomousStepParameter {
    /**
     * A list of all control points for this curve
     * 
     * @since 2018
     */
    private List<Point> controlPoints;

    /**
     * Gets a list of all control points for this curve
     * 
     * @return The control points
     * @since 2018
     */
    public Collection<Point> getControlPoints() {
        return Collections.unmodifiableCollection(controlPoints);
    }

    /**
     * Adds a control point to this curve at the end (sets the final point on the curve).
     * 
     * @param controlPoint
     *            The new control point
     * @since 2018
     */
    public void addControlPoint(Point controlPoint) {
        if (controlPoint == null) {
            throw new NullPointerException("controlPoint cannot be null.");
        }
        controlPoints.add(controlPoint);
    }

    /**
     * Removes a control point from this curve.
     * 
     * @param controlPoint
     *            The point to remove
     * @since 2018
     */
    public void removeControlPoint(Point controlPoint) {
        if (controlPoint == null) {
            throw new NullPointerException("controlPoint cannot be null.");
        }
        if (!controlPoints.remove(controlPoint)) {
            throw new IllegalArgumentException("controlPoint is not a part of this curve.");
        }
    }

    /**
     * Evaluates the curve at a given time.
     * 
     * @param controlPoints
     *            An array of all the control points for the curve
     * @param t
     *            The time to evaluate at normalized to [0, 1].
     * @param start
     *            The lower bound of the subcurve to evaluate
     * @param end
     *            The upper bound of the subcurve to evaluate
     * @return The value of the curve at the given time
     * @since 2018
     */
    private static Point evaluate(Point[] controlPoints, double t, int start, int end) {
        if (start == end) {
            return controlPoints[start];
        } else {
        	//pi point initial
            Point pi = evaluate(controlPoints, t, start, end - 1);
            //pf point final
            Point pf = evaluate(controlPoints, t, start + 1, end);
            double changeX = (pf.getX()-pi.getX());
            double changeY = (pf.getY()-pi.getY());
            //System.out.printf("(%f, %f) -> (%f, %f)\n", pi.getX(), pi.getY(), pf.getX(), pf.getY());
            return new Point(pi.getX() + changeX * t, pi.getY() + changeY * t);
        }
    }

    /**
     * Evaluates the curve at a given time.
     * 
     * @param t
     *            The time to evaluate at normalized to [0, 1].
     * @return The value of the curve at the given time
     * @since 2018
     */
    public Point evaluate(double t) {
        if (t < 0) {
            t = 0;
        } else if (t > 1) {
            t = 1;
        }
        int hashCode = controlPoints.hashCode();
        Point p = evaluate(controlPoints.toArray(new Point[0]), t, 0, controlPoints.size() - 1);
        if (hashCode != controlPoints.hashCode()) {
            throw new ConcurrentModificationException("Ther curve cannot be modified while it is being evaluated");
        }
        return p;
    }

    /**
     * Default constructor
     * 
     * @since 2018
     */
    public BezierCurve() {
        controlPoints = new ArrayList<Point>();
    }
}
