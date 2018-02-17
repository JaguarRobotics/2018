package org.usd232.robotics.powerup.calibration;

import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;
/**
 * Command to write the Calibration file
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

    @Override
    protected void initialize() {
        if (Robot.isTesting) {
            LOG.info("Go To Scale Height");
            if (Robot.amountOfThingsCalibrated == 0) {
                CalibratorData.setLiftScale(liftSubsystem.getPotentiometerValue());
                Robot.amountOfThingsCalibrated++;
                LOG.info("Calibrated The Scale Height");
                LOG.info("Go To Switch Height");
            } else if (Robot.amountOfThingsCalibrated == 1) {
                CalibratorData.setLiftSwitch(liftSubsystem.getPotentiometerValue());
                Robot.amountOfThingsCalibrated++;
                LOG.info("Calibrated The Switch Height");
                LOG.info("Go To Bottom Height");
            } else if (Robot.amountOfThingsCalibrated == 2) {
                CalibratorData.setLiftBottom(liftSubsystem.getPotentiometerValue());
                Robot.amountOfThingsCalibrated++;
                Calibration.writeToFile(Robot.calibratorData);
                LOG.info("Calibrated The Lift Bottom");
                LOG.info("Go To Height We Climb At");
            } else if (Robot.amountOfThingsCalibrated == 3) {
                CalibratorData.setLiftClimbTop(liftSubsystem.getPotentiometerValue());
                Robot.amountOfThingsCalibrated = 0;
                Calibration.writeToFile(Robot.calibratorData);
                LOG.info("Calibrated The Height We Climb At");
            } else {
            }
        }
    }

    /**
     * Always finishes immediately
     * 
     * @since 2018
     * @version 2018
     */
    @Override
    protected boolean isFinished() {
        return true;
    }
}
