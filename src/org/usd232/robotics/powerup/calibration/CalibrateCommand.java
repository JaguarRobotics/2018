package org.usd232.robotics.powerup.calibration;

import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.RobotMap;
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
        if (Robot.calibrationSetter.getSelected().equals(RobotMap.CalibrationMode.Calibrating)) {
            if(Robot.amountOfThingsCalibrated == 0) {
                LOG.info("Go To Scale Height");
                Robot.amountOfThingsCalibrated++;
            }
            if (Robot.amountOfThingsCalibrated == 1) {
                Robot.calibratorData.setLiftScale(liftSubsystem.getPotentiometerValue());
                Robot.amountOfThingsCalibrated++;
                LOG.info("Calibrated The Scale Height");
                LOG.info("Go To Switch Height");
            } else if (Robot.amountOfThingsCalibrated == 2) {
                Robot.calibratorData.setLiftSwitch(liftSubsystem.getPotentiometerValue());
                Robot.amountOfThingsCalibrated++;
                LOG.info("Calibrated The Switch Height");
                LOG.info("Go To Bottom Height");
            } else if (Robot.amountOfThingsCalibrated == 3) {
                Robot.calibratorData.setLiftBottom(liftSubsystem.getPotentiometerValue());
                LOG.info("Calibrated The Lift Bottom");
                Calibration.writeToFile(Robot.calibratorData);
                LOG.info("Calibration Complete");
                Robot.amountOfThingsCalibrated = 0;
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
