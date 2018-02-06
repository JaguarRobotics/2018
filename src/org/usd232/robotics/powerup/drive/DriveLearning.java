package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.learning.MachineLearner;
import org.usd232.robotics.powerup.learning.Vector;
import org.usd232.robotics.powerup.log.Logger;

public class DriveLearning extends CommandBase {
    public class StopCommand extends CommandBase {
        @Override
        protected void execute() {
            finished = true;
        }

        @Override
        protected boolean isFinished() {
            return true;
        }
    }

    private static final Logger         LOG       = new Logger();
    private static final MachineLearner learner   = new MachineLearner(10);
    private boolean                     finished;
    private double                      lastAngle = 0;

    public static Vector driveTo(double angle) {
        return learner.calculate(new Vector().setComponent("angle", angle));
    }

    @Override
    protected void initialize() {
        LOG.info("Learning to drive...");
        lastAngle = locationSubsystem.getAngle();
    }

    @Override
    protected void execute() {
        double angle = locationSubsystem.getAngle();
        double angleDiff = angle - lastAngle;
        lastAngle = angle;
        learner.learn(new Vector().setComponent("left", driveSubsystem.getLeftMotor()).setComponent("right",
                        driveSubsystem.getRightMotor()), new Vector().setComponent("angle", angleDiff));
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }

    @Override
    protected void end() {
        LOG.info("Now I know how to drive :)");
    }
}
