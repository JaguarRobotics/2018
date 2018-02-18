package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.ISpeedFunction;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

public class DriveTurn extends CommandBase {
    private static final Logger  LOG = new Logger();
    private final ISpeedFunction speedFunc;
    private final double         angle;

    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            LOG.enter("initialize");
            locationSubsystem.reset();
        });
    }

    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            double highSpeed = speedFunc.calculateSpeed(locationSubsystem.getAngle() / angle);
            LOG.debug("Turning to %f (currently at %f)", angle, locationSubsystem.getAngle());
            driveSubsystem.driveTank(highSpeed, -highSpeed);
        });
    }

    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
            return Math.signum(locationSubsystem.getAngle() - angle) == Math.signum(angle);
        }, true);
    }

    @Override
    protected void end() {
        LOG.catchAll(()-> {
            LOG.enter("end");
            driveSubsystem.driveTank(0, 0);
        });
    }

    public DriveTurn(ISpeedFunction speedFunc, double angle) {
        requires(driveSubsystem);
        requires(locationSubsystem);
        this.speedFunc = speedFunc;
        this.angle = angle + Math.PI / 2;
    }
}
