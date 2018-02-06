package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.autonomous.Point;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.learning.Vector;
import org.usd232.robotics.powerup.log.Logger;

public abstract class MachineLearningDrive extends CommandBase {
    private static final Logger LOG            = new Logger();
    private static final double ACCURACY       = 0.001;
    private static final double ROUGH_ACCURACY = 0.01;
    private double              lastX;
    private double              lastY;

    protected abstract Point calculateFormula(double t);

    protected Point closestCurvePointTo(double x, double y) {
        Point closest = null;
        double distance = Double.MAX_VALUE;
        for (double t = 0; t <= (1 + ROUGH_ACCURACY / 2); t += ROUGH_ACCURACY) {
            Point pt = calculateFormula(t);
            double dx = x - pt.getX();
            double dy = y - pt.getY();
            double dist = dx * dx + dy * dy;
            if (dist < distance) {
                closest = pt;
                distance = dist;
            }
        }
        return closest;
    }

    @Override
    protected void execute() {
        double x = locationSubsystem.getX();
        double y = locationSubsystem.getY();
        double dx = x - lastX;
        double dy = y - lastY;
        lastX = x;
        lastY = y;
        double distanceTraveled = Math.sqrt(dx * dx + dy * dy);
        double targetAngle = 0;
        for (double diff = Math.PI / 2; diff > ACCURACY; diff /= 2) {
            Point value = closestCurvePointTo(x + distanceTraveled * Math.cos(targetAngle),
                            y + distanceTraveled * Math.sin(targetAngle));
            double reportedAngle = Math.atan2(value.getY() - y, value.getX() - x);
            if (reportedAngle < 0) {
                targetAngle -= diff;
            } else if (reportedAngle > 0) {
                targetAngle += diff;
            } else {
                break;
            }
        }
        Vector iv = DriveLearning.driveTo(targetAngle);
        double left = iv.getComponent("left");
        double right = iv.getComponent("right");
        double mag = Math.sqrt(left * left + right * right);
        left /= mag;
        right /= mag;
        LOG.debug("(%f, %f)", left, right);
        driveSubsystem.driveTank(left, right);
    }

    public MachineLearningDrive(double maxSpeed) {
        requires(driveSubsystem);
        requires(locationSubsystem);
    }
}
