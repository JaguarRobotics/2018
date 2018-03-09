package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.ISpeedFunction;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LocationSubsystem;

public class DriveForward extends CommandBase {
    /**
     * The logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger       LOG = new Logger();
    /**
     * The amount of inches to drive forward
     * 
     * @since 2018
     * @version 2018
     */
    private final double              inches;
    /**
     * The amount the robot correct per inch
     * 
     * @since 2018
     * @version 2018
     */
    private final double              correctionPerInch;
    /**
     * The function for the robot speed
     * 
     * @since 2018
     * @version 2018
     */
    private final ISpeedFunction      speedFunc;
    /**
     * The max angle that the robot can get off by
     * 
     * @since 2018
     * @version 2018
     */
    private final double              maxAngle;
    private final double              sign;
    /**
     * The location of the robot
     * 
     * @since 2018
     * @version 2018
     */
    private LocationSubsystem.Context location;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            LOG.enter("initialize");
            location = locationSubsystem.new Context();
        });
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
            return sign * location.getY() >= inches;
        }, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void end() {
        LOG.catchAll(()-> {
            LOG.enter("end");
            driveSubsystem.driveTank(0, 0);
        });
    }

    /**
     * Drives the robot forward.
     * 
     * @param speedFunc
     *            The function that the robot uses to calculate its speed at a certain spot.
     * @param inches
     *            The amount of inches for the robot to go.
     * @param correctionPerInch
     *            The amount that the robot can correct per inch that it moves.
     * @param maxAngle
     *            The maximum angle that the robot can get off by.
     */
    public DriveForward(ISpeedFunction speedFunc, double inches, double correctionPerInch, double maxAngle) {
        requires(driveSubsystem);
        this.speedFunc = speedFunc;
        this.sign = Math.signum(inches);
        this.inches = Math.abs(inches);
        this.correctionPerInch = correctionPerInch;
        this.maxAngle = maxAngle;
    }
}
