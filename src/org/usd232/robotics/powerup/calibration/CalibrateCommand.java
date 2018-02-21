package org.usd232.robotics.powerup.calibration;

import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

/**
 * Command that allows us to create calibration data.
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 */
public class CalibrateCommand extends CommandBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();

    /**
     * This sets our calibration data.
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected void initialize() {
        LOG.catchAll(()-> {
            if (liftSubsystem.amountOfThingsCalibrated == 0) {
                LOG.info("Go To Scale Height");
                liftSubsystem.amountOfThingsCalibrated++;
            }
            if (liftSubsystem.amountOfThingsCalibrated == 1) {
                Robot.calibratorData.setLiftScale(liftSubsystem.getPotentiometerValue());
                liftSubsystem.amountOfThingsCalibrated++;
                LOG.info("Calibrated The Scale Height at " + liftSubsystem.getPotentiometerValue());
                LOG.info("Go To Switch Height");
            } else if (liftSubsystem.amountOfThingsCalibrated == 2) {
                Robot.calibratorData.setLiftSwitch(liftSubsystem.getPotentiometerValue());
                liftSubsystem.amountOfThingsCalibrated++;
                LOG.info("Calibrated The Switch Height at " + liftSubsystem.getPotentiometerValue());
                LOG.info("Go To Bottom Height");
            } else if (liftSubsystem.amountOfThingsCalibrated == 3) {
                Robot.calibratorData.setLiftBottom(liftSubsystem.getPotentiometerValue());
                LOG.info("Calibrated The Lift Bottom at " + liftSubsystem.getPotentiometerValue());
                Calibration.writeToFile(Robot.calibratorData);
                LOG.info("Calibration Complete");
                liftSubsystem.amountOfThingsCalibrated = 0;
            } else {
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFinished() {
        return true;
    }
}
