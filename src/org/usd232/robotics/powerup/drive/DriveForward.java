package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.ISpeedFunction;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LocationSubsystem;

public class DriveForward extends CommandBase {
    private static final Logger       LOG = new Logger();
    private final double              inches;
    private final double              correctionPerInch;
    private final ISpeedFunction      speedFunc;
    private final double              maxAngle;
    private final double              sign;
    private LocationSubsystem.Context location;

    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            LOG.enter("initialize");
        });
    }

    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            double x = location.getX();
            double y = location.getY();
            double highSpeed = speedFunc.calculateSpeed(location.getY() / inches);
            LOG.debug("LocationSubsytem is reporting (%f, %f) @ %f", x, y, location.getAngle());
            if (x == 0 || (Math.abs(location.getAngle() - Math.PI / 2) > maxAngle)
                            && Math.signum(x) == Math.signum(location.getAngle() - Math.PI / 2)) {
                LOG.debug("Therefore, the tank speeds are (%f, %f)", highSpeed, highSpeed);
                driveSubsystem.driveTank(sign * highSpeed, sign * highSpeed);
            } else if (x < 0) {
                LOG.debug("Therefore, the tank speeds are (%f, %f)", highSpeed + x * correctionPerInch, highSpeed);
                driveSubsystem.driveTank(sign * (highSpeed + x * correctionPerInch), sign * highSpeed);
            } else {
                LOG.debug("Therefore, the tank speeds are (%f, %f)", highSpeed, highSpeed - x * correctionPerInch);
                driveSubsystem.driveTank(sign * highSpeed, sign * (highSpeed - x * correctionPerInch));
            }
        });
    }

    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
            return sign * location.getY() >= inches;
        }, true);
    }

    @Override
    protected void end() {
        LOG.catchAll(()-> {
            LOG.enter("end");
            driveSubsystem.driveTank(0, 0);
        });
    }

    public DriveForward(ISpeedFunction speedFunc, double inches, double correctionPerInch, double maxAngle) {
        requires(driveSubsystem);
        location = locationSubsystem.new Context();
        this.speedFunc = speedFunc;
        this.sign = Math.signum(inches);
        this.inches = Math.abs(inches);
        this.correctionPerInch = correctionPerInch;
        this.maxAngle = maxAngle;
    }
}
