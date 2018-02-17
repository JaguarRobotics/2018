package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.ISpeedFunction;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

public class DriveForward extends CommandBase {
    private static final Logger  LOG = new Logger();
    private final double         inches;
    private final double         correctionPerInch;
    private final ISpeedFunction speedFunc;
    private final double         maxAngle;

    @Override
    protected void initialize() {
        LOG.enter("initialize");
        locationSubsystem.reset();
    }

    @Override
    protected void execute() {
        double x = locationSubsystem.getX();
        double y = locationSubsystem.getY();
        double highSpeed = speedFunc.calculateSpeed(locationSubsystem.getY() / inches);
        LOG.debug("(%f, %f)", x, y);
        if (x == 0 || (Math.abs(locationSubsystem.getAngle() - Math.PI / 2) > maxAngle)
                        && Math.signum(x) == Math.signum(locationSubsystem.getAngle() - Math.PI / 2)) {
            driveSubsystem.driveTank(highSpeed, highSpeed);
        } else if (x < 0) {
            driveSubsystem.driveTank(highSpeed + x * correctionPerInch, highSpeed);
        } else {
            driveSubsystem.driveTank(highSpeed, highSpeed - x * correctionPerInch);
        }
    }

    @Override
    protected boolean isFinished() {
        return locationSubsystem.getY() >= inches;
    }

    @Override
    protected void end() {
        LOG.enter("end");
        driveSubsystem.driveTank(0, 0);
    }

    public DriveForward(ISpeedFunction speedFunc, double inches, double correctionPerInch, double maxAngle) {
        requires(driveSubsystem);
        requires(locationSubsystem);
        this.speedFunc = speedFunc;
        this.inches = inches;
        this.correctionPerInch = correctionPerInch;
        this.maxAngle = maxAngle;
    }
}
