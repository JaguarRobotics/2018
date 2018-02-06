package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.autonomous.Point;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.learning.MachineLearner;
import org.usd232.robotics.powerup.learning.Vector;
import org.usd232.robotics.powerup.log.Logger;

public abstract class MachineLearningDrive extends CommandBase {
    private static final Logger LOG            = new Logger();
    private static final double ACCURACY       = 0.001;
    private static final double ROUGH_ACCURACY = 0.01;
    private MachineLearner      learner;
    private double              lastX;
    private double              lastY;
    private double              lastAngle;
    private Vector              iv;
    private final double        maxSpeed;

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
    protected void initialize() {
        learner = new MachineLearner(5);
        for (int i = 0; i < 5; ++i) {
            learner.learn(new Vector().setComponent("left", maxSpeed * 0.7).setComponent("right", maxSpeed),
                            new Vector().setComponent("angle", -Math.PI));
            learner.learn(new Vector().setComponent("left", maxSpeed).setComponent("right", maxSpeed * 0.7),
                            new Vector().setComponent("angle", Math.PI));
        }
        iv = null;
    }

    @Override
    protected void execute() {
        double x = locationSubsystem.getX();
        double y = locationSubsystem.getY();
        double angle = locationSubsystem.getAngle();
        double dx = x - lastX;
        double dy = y - lastY;
        double dAngle = angle - lastAngle;
        lastX = x;
        lastY = y;
        lastAngle = angle;
        if (iv != null) {
            learner.learn(iv, new Vector().setComponent("angle", dAngle));
        }
        double distanceTraveled = Math.sqrt(dx * dx + dy * dy);
        double targetAngle = 0;
        for (double diff = Math.PI / 2; diff > ACCURACY; diff /= 2) {
            Point value = closestCurvePointTo(x + distanceTraveled * -Math.sin(targetAngle),
                            y + distanceTraveled * Math.cos(targetAngle));
            double reportedAngle = Math.atan2(value.getX() - x, -value.getY() + y);
            if (reportedAngle < targetAngle) {
                targetAngle -= diff;
            } else if (reportedAngle > targetAngle) {
                targetAngle += diff;
            } else {
                break;
            }
        }
        iv = learner.calculate(new Vector().setComponent("angle", targetAngle));
        LOG.debug("(%f, %f) @ %f", iv.getComponent("left"), iv.getComponent("right"), targetAngle);
        driveSubsystem.driveTank(iv.getComponent("left"), iv.getComponent("right"));
    }

    @Override
    protected void end() {
        // Free up some memory
        learner = null;
    }

    public MachineLearningDrive(double maxSpeed) {
        requires(driveSubsystem);
        requires(locationSubsystem);
        this.maxSpeed = maxSpeed;
    }
}
