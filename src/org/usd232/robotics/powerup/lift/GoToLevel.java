package org.usd232.robotics.powerup.lift;

import java.util.function.Supplier;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

/**
 * The command to take the lift to a specific step.
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class GoToLevel extends CommandBase {
    /**
     * The Logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();
    /**
     * The target potentiometer value to go to on the robot.
     * 
     * @since 2018
     * @version 2018
     */
    private Supplier<Double>              targetPotentiometerValue;
    /**
     * The command that the robot runs based on where the robot is.
     * 
     * @since 2018
     * @version 2018
     */
    private CommandBase         command;

    /**
     * Takes the lift of the robot to specified potentiometer value
     * 
     * @param lowerValue
     *            the value the robot the lift to
     * @since 2018
     * @version 2018
     */
    public GoToLevel(Supplier<Double> targetValue) {
        this.targetPotentiometerValue = targetValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            double currentPotentiometerValue = liftSubsystem.getPotentiometerValue();
            LOG.info("Current Value Of Potentiometer " + currentPotentiometerValue);
            if (targetPotentiometerValue.get() >= currentPotentiometerValue) {
                LOG.info("Raising to the height of " + this.targetPotentiometerValue.get());
                command = new Raise(targetPotentiometerValue.get());
            } else if (targetPotentiometerValue.get() <= currentPotentiometerValue) {
                LOG.info("Lowering to the height of " + this.targetPotentiometerValue.get());
                command = new Lower(targetPotentiometerValue.get());
            }
            command.initializePublic();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            command.executePublic();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFinished() {
        return LOG.catchAll(()-> {
            if (command == null) {
                return false;
            } else {
                return command.isFinishedPublic();
            }
        }, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void end() {
        LOG.catchAll(()-> {
            command.endPublic();
            command = null;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void interrupted() {
        LOG.catchAll(()-> {
            end();
        });
    }
}