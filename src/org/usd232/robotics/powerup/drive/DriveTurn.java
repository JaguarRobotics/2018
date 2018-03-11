package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.ISpeedFunction;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LocationSubsystem;

public class DriveTurn extends CommandBase {
    private static final Logger       LOG = new Logger();
    private final ISpeedFunction      speedFunc;
    private final double              angle;
    private final double              sign;
    private LocationSubsystem.Context location;

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
            double highSpeed = speedFunc.calculateSpeed((location.getAngle() - Math.PI / 2) / angle);
            LOG.debug("Turning to %f (currently at %f)", angle, location.getAngle());
            driveSubsystem.driveTank(sign * -highSpeed, sign * highSpeed);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
//            return Math.signum(location.getAngle() - sign * angle - Math.PI / 2) == sign;
        	return Math.abs(location.getAngle() - Math.PI / 2) >= angle;
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
     * Turns the robot at a specified angle.
     * 
     * @param speedFunc
     *            The function of the speed that the robot should be moving at.
     * @param angle
     *            The angle to turn the robot to.
     */
    public DriveTurn(ISpeedFunction speedFunc, double angle) {
        requires(driveSubsystem);
        this.speedFunc = speedFunc;
        sign = Math.signum(angle);
        this.angle = Math.abs(angle);
    }
}
