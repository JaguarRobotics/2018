package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.LocationSubsystem;

public class TurnLeft extends CommandBase {
    private static final Logger       LOG = new Logger();
    private final double              speed;
    private final double              angle;
    private double                    SLOW_SPEED_TURN;
    private LocationSubsystem.Context location;

    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            location = locationSubsystem.createContext();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            double speed = this.speed;
            if (location.getAngle() <= ((Math.PI / 2) - angle / 2) / 2) {
                speed = SLOW_SPEED_TURN;
            }
            LOG.debug("Turning to %f (currently at %f)", angle, location.getAngle());
            driveSubsystem.driveTank(-speed, speed);
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
        this.SLOW_SPEED_TURN = Robot.preferences.getDouble("SLOW_SPEED_TURN", .45);
        this.speed = speed;
        angle -= Math.PI / 2;
        this.angle = Math.abs(angle);
    }
}