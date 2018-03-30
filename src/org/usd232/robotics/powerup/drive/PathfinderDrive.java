package org.usd232.robotics.powerup.drive;

import org.apache.commons.lang3.ArrayUtils;
import org.usd232.robotics.powerup.commands.CommandBase;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Config;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.Waypoint;

public class PathfinderDrive extends CommandBase {
    private static final FitMethod FITTING_METHOD   = FitMethod.HERMITE_CUBIC;
    private static final int       SAMPLES          = Config.SAMPLES_HIGH;
    private static final double    DT               = 0.05;
    private static final double    MAX_VELOCITY     = 1.7;
    private static final double    MAX_ACCELERATION = 2;
    private static final double    MAX_JERK         = 60;
    private static final Config    CONFIG;

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
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
    }
}
