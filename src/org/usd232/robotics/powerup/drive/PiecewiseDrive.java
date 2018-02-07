package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.autonomous.ISpeedFunction;
import org.usd232.robotics.autonomous.Point;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

/**
 * Drives along a piecewise curve
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public class PiecewiseDrive extends CommandBase {
    private static final Logger  LOG = new Logger();
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
            double dx = points[pointNum].getX() - locationSubsystem.getX();
            double dy = points[pointNum].getY() - locationSubsystem.getY();
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
                LOG.debug("Running to point #%d (%.3f, %.3f) from (%.3f, %.3f), which is (%.3f, %.3f) difference, by settings motors to (%.3f, %.3f) at speed %.3f (that's an angle of %.3f).",
                                pointNum, points[pointNum].getX(), points[pointNum].getY(), locationSubsystem.getX(),
                                locationSubsystem.getY(), dx, dy, powers[0], powers[1], speed, angle);
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
