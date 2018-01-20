package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.autonomous.BezierCurve;
import org.usd232.robotics.autonomous.Point;
import org.usd232.robotics.powerup.commands.CommandBase;

/**
 * Drives in along a Bezier curve
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public class BezierDrive extends CommandBase {
    /**
     * The resolution of the curve to generate
     * 
     * @since 2018
     */
    private static final double TIME_STEP        = 1.0 / 100.0;
    /**
     * The maximum to allow the robot to change in angle in a single frame
     * 
     * @since 2018
     */
    private static final double MAX_ANGLE_CHANGE = Math.PI / 4;
    /**
     * The curve to drive along
     * 
     * @since 2018
     */
    private final BezierCurve   curve;
    private final Point startingPoint;
    /**
     * The width of the field
     * 
     * @since 2018
     */
    private final double        fieldWidth;
    /**
     * The height of the field
     * 
     * @since 2018
     */
    private final double        fieldHeight;
    /**
     * The width of the robot
     * 
     * @since 2018
     */
    private final double        robotWidth;
    /**
     * The center of mass of the robot
     * 
     * @since 2018
     */
    private final double        centerOfMass;
    /**
     * The previous frame's x-coordinate
     * 
     * @since 2018
     */
    private double              lastX;
    /**
     * The previous frame's y-coordinate
     * 
     * @since 2018
     */
    private double              lastY;
    /**
     * The previous frame's left encoder value
     * 
     * @since 2018
     */
    private double              lastLeftEncoder;
    /**
     * The previous frame's right encoder value
     * 
     * @since 2018
     */
    private double              lastRightEncoder;
    /**
     * If the command has been finished
     * 
     * @since 2018
     */
    private boolean             finished;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        driveSubsystem.robotStop();
        lastLeftEncoder = driveSubsystem.getEncoderLeft();
        lastRightEncoder = driveSubsystem.getEncoderRight();
        finished = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute() {
        System.out.printf("(%f, %f) @ %f\n", locationSubsystem.getX(), locationSubsystem.getY(), locationSubsystem.getAngle());
        double time = 0;
        double distance = 9001;
        double robotX = locationSubsystem.getX() / fieldWidth + startingPoint.getX();
        double robotY = locationSubsystem.getY() / fieldHeight + startingPoint.getY();
        for (double t = 0; t < 1.000000001; t += TIME_STEP) {
            Point point = curve.evaluate(t);
            double dx = point.getX() - robotX;
            double dy = point.getY() - robotY;
            double ds = Math.sqrt(dx * dx + dy * dy);
            if (ds < distance) {
                distance = ds;
                time = t;
            }
        }
        if (time < 1 && robotX >= 0 && robotX <= 1 && robotY >= 0 && robotY <= 1) {
            double dx = robotX - lastX;
            double dy = robotY - lastY;
            distance = Math.sqrt(dx * dx + dy * dy);
            Point point = curve.evaluate(time);
            Point nextPoint = curve.evaluate(time + TIME_STEP);
            dx = nextPoint.getX() - point.getX();
            dy = nextPoint.getY() - point.getY();
            double ds = Math.sqrt(dx * dx + dy * dy);
            dx /= ds;
            dy /= ds;
            double angle = Math.atan2(point.getY() - robotY + dy * distance, point.getX() - robotX + dx * distance)
                            - locationSubsystem.getAngle();
            if (angle < -MAX_ANGLE_CHANGE) {
                angle = -MAX_ANGLE_CHANGE;
            } else if (angle > MAX_ANGLE_CHANGE) {
                angle = MAX_ANGLE_CHANGE;
            }
            double encoderLeft = driveSubsystem.getEncoderLeft();
            double encoderRight = driveSubsystem.getEncoderRight();
            double ds1 = encoderLeft - lastLeftEncoder;
            double ds2 = encoderRight - lastRightEncoder;
            lastLeftEncoder = encoderLeft;
            lastRightEncoder = encoderRight;
            ds = Math.sqrt(ds1 * ds1 + ds2 * ds2);
            if (ds == 0) {
                ds = 1;
            }
            ds1 = ds - robotWidth * centerOfMass * angle;
            ds2 = ds1 + robotWidth * angle;
            ds = Math.max(Math.abs(ds1), Math.abs(ds2));
            ds1 /= ds;
            ds2 /= ds;
            driveSubsystem.driveTank(ds2, ds1);
        } else {
            finished = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void end() {
        driveSubsystem.robotStop();
        finished = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFinished() {
        return finished;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void interrupted() {
        end();
    }

    /**
     * Default constructor
     * 
     * @param curve
     *            The curve to drive
     * @param fieldWidth
     *            The width of the field
     * @param fieldHeight
     *            The height of the field
     * @param robotWidth
     *            The width of the robot
     * @param centerOfMass
     *            The center of mass of the robot from left to right (from 0 to 1)
     * @since 2018
     */
    public BezierDrive(BezierCurve curve, double fieldWidth, double fieldHeight, double robotWidth,
                    double centerOfMass) {
        requires(driveSubsystem);
        requires(locationSubsystem);
        this.curve = curve;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.robotWidth = robotWidth;
        this.centerOfMass = centerOfMass;
        startingPoint = curve.getControlPoints().toArray(new Point[0])[0];
        lastX = startingPoint.getX();
        lastY = startingPoint.getY();
    }
}
