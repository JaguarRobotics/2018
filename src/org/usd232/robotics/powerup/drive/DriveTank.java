package org.usd232.robotics.powerup.drive;

import org.usd232.robotics.powerup.commands.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives the robot in teleop based on left and right joystick inputs.
 * 
 * @author Zach Diebert, Alex Whipple, Brian Parks
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
    /**
     * The minimum power value
     * 
     * @since 2018
     * @version 2018
     */
    double minValue = .3;
    /**
     * How far the input "dead zone" extends from 0
     * 
     * @since 2018
     * @version 2018
     */
    double deadZone = 0.1;
    /**
     * Scaling factor to allow the "dead zone" to be a cube root function that curves into  
     */
    double deadZoneScale = minValue / Math.cbrt(deadZone);
    
    @Override
    protected void initialize() {
    }

    /**
     * Drives the robot with an initial cube root function in the "dead zone" that curves rather cleanly into a linear scale of the joystick inputs
     * 
     * @since 2017
     * @version 2018
     */
    @Override
    protected void execute() {
        double joystickTolerance = SmartDashboard.getNumber("Joystick Tolerance", 1);
        double joystick0 = oi.Joystick0.getY() * joystickTolerance;
        double joystick1 = oi.Joystick1.getY() * joystickTolerance;
        left = scaleInput(joystick0);
        right = scaleInput(joystick1);
        driveSubsystem.driveTank(-left, -right);
    }
    
    private double scaleInput(double input) {
    	double output = 0;
    	double absoluteInput = Math.abs(input);
    	if(absoluteInput < deadZone) {
    		output = Math.cbrt(input) * deadZoneScale;
    	} else {
    		output = absoluteInput / input * (((1 - minValue) / (1 - deadZone)) * (absoluteInput - deadZone) + minValue);
    	}
    	return output;
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
