package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.commands.CommandBase;
import jaci.pathfinder.Trajectory;

public class PathfinderDrive extends CommandBase {
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

    public static PathfinderDrive createStraight(double distance) {
        return new PathfinderDrive(null);
    }

    public static PathfinderDrive createTurn(double angle) {
        return new PathfinderDrive(null);
    }

    public PathfinderDrive(Trajectory trajectory) {
    }
}
