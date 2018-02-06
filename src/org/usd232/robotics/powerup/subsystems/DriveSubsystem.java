package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.drive.DriveTank;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * @author Brian, Nathan G, Zach D. Drive subsystem holds all of methods used in the commands for drive
 * @since 2017
 */
public class DriveSubsystem extends SubsystemBase {
    /**
     * Class that controls both drive motors
     */
    private static DifferentialDrive robotDrive    = new DifferentialDrive(leftDriveMotor, rightDriveMotor);
    /**
     * distance left encoder has traveled.
     */
    private double                   leftEncoderValue;
    /**
     * distance right encoder has traveled.
     */
    private double                   rightEncoderValue;
    /**
     * array of encoder values with left occupying 0, and right occupying 1.
     */
    private double[]                 encoderValues = { leftEncoderValue, rightEncoderValue };
    /**
     * Diameter of pulleys, used for encoder calculations. (in inches)
     */
    public double                    diameter      = 6;
    /**
     * pulses per rotation for the encoders.
     */
    private int                      ppr           = (int) (400 * 3);
    /**
     * Counter used to count in get motor powers
     */
    public static int                counter       = 0;

    /**
     * Calculates motor powers for adjusted driving <html>you can view the math
     * below<img src="https://puu.sh/tO9Si/990853f967.png"></img></html>
     * 
     * @return returns an array of powers with left in slot 0 & right in slot 1
     */
    public double[] getMotorPowers(double desiredAngle) {
    	double angle = CommandBase.locationSubsystem.getAngle();
    	double turnAngle = desiredAngle - angle;
    	double left = 0;
    	double right = 0;
    	double tan = Math.tan(turnAngle + 45);
    	if(angle == 0) {
    		left = 1;
    		right = 1;
    	}
    	if(angle < 0) {
    		left = tan;
    		right = 1;
    	} else {
    		left = 1;
    		right = Math.pow(tan, -1);
    	}
    	if(left >= 1) left = 1;
    	if(right >= 1) right = 1;
    	System.out.println("Angle: " + angle);
    	return new double[] {left, right};
    }

    /**
     * @param encoderTicks
     *            encoder ticks to move
     * @return encoder ticks converted to inches
     */
    public double getDistanceInInches(double encoderTicks) {
        double result = (diameter * Math.PI) * (encoderTicks / ppr);
        return result;
    }

    /**
     * @param inches
     *            number of inches to convert
     * @return number of encoder ticks for the specified number of inches.
     */
    public double getEncoderTicksFromInches(double inches) {
        double result = inches * (ppr / (Math.PI * diameter));
        return result;
    }

    /**
     * @param encoder
     *            ticks number of encoder ticks to convert
     * @return number of inches from encoder ticks
     */
    public double getEncoderInchesFromEncoderTicks(double encoderTicks) {
        double result = encoderTicks / ppr * (Math.PI * diameter);
        return result;
    }

    /**
     * @param radians
     *            ` The amount of radians the robot is going to go
     * @returns the amount of encoder ticks from from radians.
     */
    public double getEncoderTicksFromRadians(double radians) {
        double result = getEncoderTicksFromInches(radians * ROBOT_WIDTH / 2);
        return result;
    }

    /**
     * Calculates the amount of radians that is equivilant to a passed in number of degrees
     * 
     * @param degrees
     *            The degrees that the robot should turn
     * @return The amount of radians per the amount of degrees passed in
     */
    public double getRadiansFromDegrees(double degrees) {
        return (degrees / 360) * (Math.PI * 2);
    }

    /**
     * resets the encoders.
     * 
     * @param left
     *            make true to reset left encoder
     * @param right
     *            make true to reset right encoder
     */
    public void resetEncoders(boolean left, boolean right) {
        if (left) {
            leftDriveEncoder.reset();
        }
        if (right) {
            rightDriveEncoder.reset();
        }
    }

    /**
     * Sets encoder distance
     */
    public void startEncoders() {
        leftDriveEncoder.setDistancePerPulse(Math.PI * diameter / ppr);
        rightDriveEncoder.setDistancePerPulse(Math.PI * diameter / ppr);
    }

    /**
     * gets encoder values
     * 
     * @return array of encoder values with left occupying slot 0, and right occupying slot 1
     */
    public double[] getEncoders() {
        leftEncoderValue = leftDriveEncoder.getDistance();
        rightEncoderValue = rightDriveEncoder.getDistance();
        encoderValues = new double[] { leftEncoderValue, rightEncoderValue };
        return encoderValues;
    }

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
     * Turns robot counter-clockwise at a specific speed.
     * 
     * @param speed
     *            power to turn the robot
     */
    public void robotTurn(double speed) {
        robotDrive.tankDrive(-speed, speed);
    }

    /**
     * Stops both drive motors
     */
    public void robotStop() {
        robotDrive.tankDrive(0, 0);
    }

    /**
     * @return left encoder distance
     */
    public double getEncoderLeft() {
        return leftDriveEncoder.getDistance();
    }

    /**
     * @return right encoder distance
     */
    public double getEncoderRight() {
        return rightDriveEncoder.getDistance();
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
