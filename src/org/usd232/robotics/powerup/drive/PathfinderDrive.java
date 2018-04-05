package org.usd232.robotics.powerup.drive;

import org.apache.commons.lang3.ArrayUtils;
import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.SensorContext;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Config;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class PathfinderDrive extends CommandBase {
    private static final FitMethod FITTING_METHOD   = FitMethod.HERMITE_CUBIC;
    private static final int       SAMPLES          = Config.SAMPLES_HIGH;
    private static final double    DT               = 0.05;
    private static final double    MAX_VELOCITY     = 1.7;
    private static final double    MAX_ACCELERATION = 2;
    private static final double    MAX_JERK         = 60;
    private static final double    CUTTOFF_VALUE    = 0.4;
    private static final int       TICKS_PER_REV    = 58;
    private static final double    DIAMETER         = 0.1524;
    private static final double    WHEELBASE_WIDTH  = 0.4191;
    private static Config          CONFIG;
    private final Trajectory       trajectory;
    private EncoderFollower        left;
    private EncoderFollower        right;
    private SensorContext          sensors;
    private double                 l                = 0;
    private double                 r                = 0;
    private double                 gyro_heading;
    private double                 desired_heading;
    private TankModifier           modifier;
    /**
     * The logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger    LOG              = new Logger();

    @Override
    protected void initialize() {
        LOG.trace("Pathfinder initialized");
        modifier = new TankModifier(trajectory).modify(WHEELBASE_WIDTH);
        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());
        sensors = new SensorContext();
        left.configureEncoder(IO.leftDriveEncoder.get(), TICKS_PER_REV, DIAMETER);
        right.configureEncoder(IO.rightDriveEncoder.get(), TICKS_PER_REV, DIAMETER);
    }

    @Override
    protected void execute() {
        double l = left.calculate(IO.leftDriveEncoder.get());
        double r = right.calculate(IO.rightDriveEncoder.get());
        gyro_heading = IO.gyro.getAngle();
        desired_heading = Pathfinder.r2d(left.getHeading() - right.getHeading());
        LOG.debug("Desired heading: " + desired_heading);
        LOG.debug("gyro heading: " + gyro_heading);
        double angleDifference = desired_heading - gyro_heading;
        double turn = 0.8 * (-1.0 / 80.0) * angleDifference;
        LOG.debug("Left: " + (l + turn));
        LOG.debug("Right: " + (r - turn));
        driveSubsystem.driveTank((l + turn), (r - turn));
    }

    @Override
    protected boolean isFinished() {
        if (left.isFinished() && right.isFinished() && gyro_heading >= desired_heading) {
            return true;
        }
        return false;
    }

    @Override
    protected void end() {
        LOG.trace("Pathfinder ended");
        driveSubsystem.driveTank(0, 0);
        sensors = null;
    }

    public static PathfinderDrive createFromPoints(Waypoint... waypoints) {
        try {
            LOG.trace("Entered create from points, waypoints");
            Waypoint[] arr = ArrayUtils.clone(waypoints);
            ArrayUtils.reverse(arr);
            Trajectory traj = Pathfinder.generate(waypoints, CONFIG);
            LOG.trace("Above return traj");
            return new PathfinderDrive(traj);
        } catch (Throwable t) {
            LOG.error(t);
        }
        LOG.trace("Above return null");
        return null;
    }

    public static PathfinderDrive createFromPoints(double... values) {
        LOG.trace("Entered create from points, values");
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
        LOG.trace("Create Straight Entered");
        return createFromPoints(0, 0, 0, distance, 0, 0);
    }

    public static PathfinderDrive test() {
        Waypoint[] points = new Waypoint[] { new Waypoint(10, 0, 0), new Waypoint(0, 0, 0) };
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
                        Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
        Trajectory trajectory = Pathfinder.generate(points, config);
        return new PathfinderDrive(trajectory);
    }

    public static PathfinderDrive createTurn(double angle) {
        LOG.trace("Entered create turn");
        return createFromPoints(0, 0, 0, 0, 0, angle);
    }

    static {
        CONFIG = new Config(FITTING_METHOD, SAMPLES, DT, MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
    }

    public PathfinderDrive(Trajectory trajectory) {
        LOG.trace("Constructed PathFinder with a trajectory");
        this.trajectory = trajectory;
    }
}
