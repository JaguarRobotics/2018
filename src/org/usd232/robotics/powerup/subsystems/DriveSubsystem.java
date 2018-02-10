package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.drive.DriveTank;
import org.usd232.robotics.powerup.log.Logger;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * @author Brian, Nathan G, Zach D. Drive subsystem holds all of methods used in the commands for drive
 * @since 2017
 */
public class DriveSubsystem extends SubsystemBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();
    /**
     * Class that controls both drive motors
     */
    private static DifferentialDrive robotDrive = new DifferentialDrive(leftDriveMotor, rightDriveMotor);

    /**
     * Drives the robot based on left and right speeds. Calls adjusted driving when 1 or -1
     * 
     * @param left
     *            speed
     * @param right
     *            speed
     */
    public void driveTank(double left, double right) {
        LOG.debug("Motors are setting to (%f, %f)", left, right);
        robotDrive.tankDrive(left, right);
    }

    /**
     * @return whether extended. If true, extended.
     */
    public static boolean getGearShift() {
        return gearShiftSolenoid.get();
    }

    /**
     * Extends solenoid to shift gears on wheels.
     */
    public static void gearShiftHigh() {
        gearShiftSolenoid.set(true);
    }

    /**
     * Retracts solenoid to shift back gear on wheels.
     */
    public static void gearShiftLow() {
        gearShiftSolenoid.set(false);
    }

    /**
     * Sets the default command of the subsystem.
     */
    public void initDefaultCommand() {
        setDefaultCommand(new DriveTank());
    }
}
