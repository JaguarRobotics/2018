package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.autonomous.ISpeedFunction;
import org.usd232.robotics.autonomous.Point;
import org.usd232.robotics.powerup.commands.CommandBase;

/**
 * Drives along a piecewise curve
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public class PiecewiseDrive extends CommandBase {
    /**
     * A list of points, in order, to follow as part of the curve
     * 
     * @since 2018
     */
    private final Point[]        points;
    /**
     * The number of inches in distance that the robot must be within a point for it to be considered at that point
     * 
     * @since 2018
     */
    private final double         accuracy;
    /**
     * The speed that the robot should travel in
     * 
     * @since 2018
     */
    private final ISpeedFunction speedFunc;
    /**
     * The index of the point that the robot is currently approaching
     * 
     * @since 2018
     */
    private int                  pointNum;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        pointNum = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute() {
        while (!isFinished()) {
            double dx = locationSubsystem.getX() - points[pointNum].getX();
            double dy = locationSubsystem.getY() - points[pointNum].getY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance <= accuracy) {
                ++pointNum;
            } else {
                double angle = Math.atan2(dy, dx) - Math.PI / 2;
                double[] powers = driveSubsystem.getMotorPowers(angle);
                double speed = speedFunc.determineSpeed(((double) pointNum) / (double) points.length);
                if (speed < 0) {
                    speed = 0;
                } else if (speed > 1) {
                    speed = 1;
                }
                driveSubsystem.driveTank(speed * powers[0], speed * powers[1]);
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFinished() {
        return pointNum >= points.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void end() {
        driveSubsystem.robotStop();
    }

    /**
     * Default constructor
     * 
     * @param points
     *            A list of points, in order, to follow as part of the curve
     * @param accuracy
     *            The number of inches in distance that the robot must be within a point for it to be considered at that
     *            point
     * @param speedFunc
     *            The speed that the robot should travel in
     * @since 2018
     */
    public PiecewiseDrive(Point[] points, double accuracy, ISpeedFunction speedFunc) {
        this.points = points;
        this.accuracy = accuracy;
        this.speedFunc = speedFunc;
        requires(driveSubsystem);
        requires(locationSubsystem);
    }
}
