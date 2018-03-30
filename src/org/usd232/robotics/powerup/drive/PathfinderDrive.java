package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class PathfinderDrive extends CommandBase {
    private static final double CUTTOFF_VALUE   = .4;
    private final int           TICKS_PER_REV   = 1000;
    private final double        DIAMETER        = 6;
    private double              l               = 0;
    private double              r               = 0;
    private final int           encoderPosition = 0;
    private final Trajectory    trajectory;
    private EncoderFollower     left;
    private EncoderFollower     right;

    @Override
    protected void initialize() {
        left = new EncoderFollower(trajectory);
        right = new EncoderFollower(trajectory);
        left.configureEncoder(encoderPosition, TICKS_PER_REV, DIAMETER);
        right.configureEncoder(encoderPosition, TICKS_PER_REV, DIAMETER);
    }

    @Override
    protected void execute() {
        l = left.calculate(IO.leftDriveEncoder.get());
        r = right.calculate(IO.rightDriveEncoder.get());
        double gyro_heading = IO.gyro.getAngle();
        double desired_heading = Pathfinder.r2d(left.getHeading());
        double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
        double turn = 0.8 * (-1.0 / 80.0) * angleDifference;
        driveSubsystem.driveTank(l + turn, r - turn);
    }

    @Override
    protected boolean isFinished() {
        if (Math.abs(l) <= CUTTOFF_VALUE && Math.abs(r) <= CUTTOFF_VALUE) {
            return true;
        }
        return false;
    }

    @Override
    protected void end() {
        driveSubsystem.driveTank(0, 0);
    }

    public static PathfinderDrive createStraight(double distance) {
        return new PathfinderDrive(null);
    }

    public static PathfinderDrive createTurn(double angle) {
        return new PathfinderDrive(null);
    }

    public PathfinderDrive(Trajectory trajectory) {
        this.trajectory = trajectory;
    }
}
