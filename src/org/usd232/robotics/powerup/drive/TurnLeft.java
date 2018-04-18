package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LocationSubsystem;

public class TurnLeft extends CommandBase {
    private static final Logger       LOG          = new Logger();
    private final double              speed;
    private final double              angle;
    private final double              CUTOFF_SPEED = .4;
    private LocationSubsystem.Context location;

    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            LOG.enter("initialize");
            location = locationSubsystem.createContext();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            double highSpeed = speed;
            if (location.getAngle() <= ((Math.PI / 2) - angle / 2) / 2) {
                highSpeed = CUTOFF_SPEED;
            }
            LOG.debug("Turning to %f (currently at %f)", angle, location.getAngle());
            driveSubsystem.driveTank(-highSpeed, highSpeed);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
            return location.getAngle() <= (Math.PI / 2) - angle / 2;
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
            location = null;
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
    public TurnLeft(double speed, double angle) {
        LOG.enter("Entered Turn Left");
        requires(driveSubsystem);
        this.speed = speed;
        angle -= Math.PI / 2;
        this.angle = Math.abs(angle);
    }
}