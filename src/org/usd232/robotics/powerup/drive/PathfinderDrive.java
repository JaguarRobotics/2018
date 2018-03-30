package org.usd232.robotics.powerup.drive;

import org.apache.commons.lang3.ArrayUtils;
import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Config;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;

public class PathfinderDrive extends CommandBase {
    private static final FitMethod FITTING_METHOD   = FitMethod.HERMITE_CUBIC;
    private static final int       SAMPLES          = Config.SAMPLES_HIGH;
    private static final double    DT               = 0.05;
    private static final double    MAX_VELOCITY     = 1.7;
    private static final double    MAX_ACCELERATION = 2;
    private static final double    MAX_JERK         = 60;
    private static final double CUTTOFF_VALUE   = .4;
    private static final Config    CONFIG;
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

    public static PathfinderDrive createFromPoints(Waypoint... waypoints) {
        Waypoint[] arr = ArrayUtils.clone(waypoints);
        ArrayUtils.reverse(arr);
        return new PathfinderDrive(Pathfinder.generate(arr, CONFIG));
    }

    public static PathfinderDrive createFromPoints(double... values) {
        if (values.length % 3 != 0) {
            throw new IllegalArgumentException("Points must be a set of (x, y, rotation) values.");
        }
        Waypoint[] waypoints = new Waypoint[values.length / 3];
        for (int i = 0; i < waypoints.length; ++i) {
            waypoints[i] = new Waypoint(values[i * 3], values[i * 3 + 1], values[i * 3 + 2]);
        }
        return createFromPoints(waypoints);
    }

    public static PathfinderDrive createStraight(double distance) {
        return createFromPoints(0, 0, 0, distance, 0, 0);
    }

    public static PathfinderDrive createTurn(double angle) {
        return createFromPoints(0, 0, 0, 0, 0, angle);
    }

    static {
        CONFIG = new Config(FITTING_METHOD, SAMPLES, DT, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
    }

    public PathfinderDrive(Trajectory trajectory) {
        this.trajectory = trajectory;
    }
}
