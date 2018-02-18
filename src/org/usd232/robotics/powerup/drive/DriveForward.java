package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

public class DriveForward extends CommandBase {
    private static final Logger LOG                = new Logger();
    private static final double SPEED_CUTOFF_VALUE = 0.1;
    private final double        inches;
    private final double        correctionPerInch;
    private final double        highSpeed;
    private final double        maxAngle;
    private boolean             isSlowingDown;

    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            LOG.enter("initialize");
            locationSubsystem.reset();
            isSlowingDown = false;
        });
    }

    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            double x = locationSubsystem.getX();
            double y = locationSubsystem.getY();
            double speed = locationSubsystem.getSpeed();
            LOG.debug("LocationSubsytem is reporting (%f, %f) @ %f radians of rotation and %f speed", x, y,
                            locationSubsystem.getAngle(), speed);
            if (isSlowingDown) {
                driveSubsystem.driveTank(0, 0);
            } else {
                if (x == 0 || (Math.abs(locationSubsystem.getAngle() - Math.PI / 2) > maxAngle)
                                && Math.signum(x) == Math.signum(locationSubsystem.getAngle() - Math.PI / 2)) {
                    LOG.debug("Therefore, the tank speeds are (%f, %f)", highSpeed, highSpeed);
                    driveSubsystem.driveTank(highSpeed, highSpeed);
                } else if (x < 0) {
                    LOG.debug("Therefore, the tank speeds are (%f, %f)", highSpeed + x * correctionPerInch, highSpeed);
                    driveSubsystem.driveTank(highSpeed + x * correctionPerInch, highSpeed);
                } else {
                    LOG.debug("Therefore, the tank speeds are (%f, %f)", highSpeed, highSpeed - x * correctionPerInch);
                    driveSubsystem.driveTank(highSpeed, highSpeed - x * correctionPerInch);
                }
                if (locationSubsystem.getY() >= inches
                                - Math.abs(speed * speed / Robot.calibratorData.getFrictionalAcceleration())) {
                    LOG.debug("Slowing down now");
                    isSlowingDown = true;
                }
            }
        });
    }

    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
            return isSlowingDown && locationSubsystem.getSpeed() < SPEED_CUTOFF_VALUE;
        }, true);
    }

    @Override
    protected void end() {
        LOG.catchAll(()-> {
            LOG.enter("end");
            driveSubsystem.driveTank(0, 0);
        });
    }

    public DriveForward(double highSpeed, double inches, double correctionPerInch, double maxAngle) {
        requires(driveSubsystem);
        requires(locationSubsystem);
        this.highSpeed = highSpeed;
        this.inches = inches;
        this.correctionPerInch = correctionPerInch;
        this.maxAngle = maxAngle;
    }
}
