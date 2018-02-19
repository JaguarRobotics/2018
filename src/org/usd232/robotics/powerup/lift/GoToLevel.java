package org.usd232.robotics.powerup.lift;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

/**
 * The command to lower the lift to a specific step
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class GoToLevel extends CommandBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();
    private double              targetPotentiometerValue;
    private CommandBase         command;

    /**
     * Lowers the lift of the robot to specified potentiometer value
     * 
     * @param lowerValue
     *            the value the robot the lift to
     * @since 2018
     * @version 2018
     */
    public GoToLevel(double targetValue) {
        this.targetPotentiometerValue = targetValue;
    }

    /**
     * What happens on initialize, does nothing
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            double currentPotentiometerValue = liftSubsystem.getPotentiometerValue();
            LOG.info("Current Value Of Potentiometer " + currentPotentiometerValue);
            if (targetPotentiometerValue >= currentPotentiometerValue) {
                LOG.info("Raising to the height of " + this.targetPotentiometerValue);
                command = new Raise(targetPotentiometerValue);
            } else if (targetPotentiometerValue <= currentPotentiometerValue) {
                LOG.info("Lowering to the height of " + this.targetPotentiometerValue);
                command = new Lower(targetPotentiometerValue);
            }
            command.initializePublic();
        });
    }

    /**
     * What happens while the command is running, moves the motor
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void execute() {
        LOG.catchAll(()-> {
            command.executePublic();
        });
    }

    /**
     * Checks if the potentiometer value has reached the target value
     * 
     * @return true if potentiometer value is lower than or equal to targetValue
     * @since 2018
     * @version 2018
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
     * Turns off the motor when the command ends
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void end() {
        LOG.catchAll(()-> {
            command.endPublic();
            command = null;
        });
    }

    /**
     * Runs end if the command is interrupted
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void interrupted() {
        LOG.catchAll(()-> {
            end();
        });
    }
}
