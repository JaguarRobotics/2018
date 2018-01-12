package edu.jaguarbots.powerup.commands.drive;

import edu.jaguarbots.powerup.commands.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives the robot in teleop based on left and right joystick inputs.
 * 
 * @author Nathan Gawith, Kyle K, Cody Moose, Brian Parks, Zach Deibert
 * @since Always
 * @version 2018
 */
public class DriveTank extends CommandBase {
    /**
     * Drives the robot in teleop based on left and right joystick inputs.
     * 
     * @since Always
     * @version 2018
     */
    public DriveTank() {
	requires(driveSubsystem);
    }
    /**
     * The power given to the left motor
     * 
     * @since 2017
     * @version 2018
     */
    double left;
    /**
     * The power given to the right motor
     * 
     * @since 2017
     * @version 2018
     */
    double right;
    @Override
    protected void initialize() {
    }
    /**
     * Drives the robot with joysticks exponentially (PowNum is the power that it raises it to) to give it more control
     * 
     * @since 2017
     * @version 2018
     */
    @Override
    protected void execute() {
	SmartDashboard.putNumber("EncoderLeft", CommandBase.driveSubsystem.getEncoderLeft());
	SmartDashboard.putNumber("EncoderRight", CommandBase.driveSubsystem.getEncoderRight());
	double powNum = 2;
	double joystickTolerance = SmartDashboard.getNumber("Joystick Tolerance", 1);
	double joystick0 = oi.Joystick0.getY() * joystickTolerance;
	double joystick1 = oi.Joystick1.getY() * joystickTolerance;
	double adjustedJoystick0 = Math.abs(joystick0);
	double adjustedJoystick1 = Math.abs(joystick1);
	double powerJoystick0 = Math.pow(adjustedJoystick0, powNum);
	double powerJoystick1 = Math.pow(adjustedJoystick1, powNum);
	if (Math.abs(powerJoystick0) > adjustedJoystick0) {
	    powerJoystick0 = adjustedJoystick0;
	}
	if (Math.abs(powerJoystick1) > adjustedJoystick1) {
	    powerJoystick1 = adjustedJoystick1;
	}
	left = (powerJoystick0 * (adjustedJoystick0 / joystick0)) / joystickTolerance;
	right = (powerJoystick1 * (adjustedJoystick1 / joystick1)) / joystickTolerance;
	driveSubsystem.driveTank(-left, -right);
    }
    /**
     * Determines if the command is done which is never is because driving is never done
     * 
     * @since Always
     * @version 2018
     * @return false because driving is never done
     */
    @Override
    protected boolean isFinished() {
	return false;
    }
    /**
     * What happens when the command ends
     * 
     * @since Always
     * @version 2018
     */
    @Override
    protected void end() {
    }
    /**
     * What happens if the command is interrupted
     * 
     * @since Always
     * @version 2018
     */
    @Override
    protected void interrupted() {
    }
}
