package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.drive.DriveTank;
import org.usd232.robotics.powerup.log.Logger;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * @author Brian, Nathan G, Zach D. Drive subsystem holds all of methods used in the commands for drive
 * @since 2017
 */
public class DriveSubsystem extends SubsystemBase {
    private static final Logger      LOG        = new Logger();
    /**
     * Class that controls both drive motors
     */
    private static DifferentialDrive robotDrive = LOG
                    .catchAll(()->new DifferentialDrive(leftDriveMotor, rightDriveMotor));

    /**
     * Drives the robot based on left and right speeds. Calls adjusted driving when 1 or -1
     * 
     * @param left
     *            speed
     * @param right
     *            speed
     */
    public void driveTank(double left, double right) {
        robotDrive.tankDrive(left, right);
    }

    /**
     * Sets the default command of the subsystem.
     */
    public void initDefaultCommand() {
        LOG.catchAll(()-> {
            setDefaultCommand(new DriveTank());
        });
    }
}
