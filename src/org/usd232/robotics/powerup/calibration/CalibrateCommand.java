package org.usd232.robotics.powerup.calibration;

import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.RobotMap;
import org.usd232.robotics.powerup.commands.CommandBase;
/**
 * Command to write the Calibration file
 * 
 * @author Brian
 * @since 2018
 * @version 2018
 *
 */
public class CalibrateCommand extends CommandBase {
    @Override
    protected void initialize() {
        if (Robot.calibrationSetter.getSelected().equals(RobotMap.CalibrationMode.Calibrating)) {
            if (Robot.amountOfThingsCalibrated == 0) {
                CalibratorData.setLiftScale(liftSubsystem.getPotentiometerValue());
                Robot.amountOfThingsCalibrated++;
            } else if (Robot.amountOfThingsCalibrated == 1) {
                CalibratorData.setLiftSwitch(liftSubsystem.getPotentiometerValue());
                Robot.amountOfThingsCalibrated++;
            } else if (Robot.amountOfThingsCalibrated == 2) {
                CalibratorData.setLiftBottom(liftSubsystem.getPotentiometerValue());
                Robot.amountOfThingsCalibrated = 0;
                Calibration.writeToFile(Robot.calibratorData);
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
