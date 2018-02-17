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
        double joystickTolerance = SmartDashboard.getNumber("Joystick Tolerance", 1);
        double joystick0 = oi.Joystick0.getY() * joystickTolerance;
        double joystick1 = oi.Joystick1.getY() * joystickTolerance;
        double absoluteValueJoystick0 = Math.abs(joystick0);
        double absoluteValueJoystick1 = Math.abs(joystick1);
        left = absoluteValueJoystick0 / joystick0 * (absoluteValueJoystick0 * (1 - minValue) + minValue);
        right = absoluteValueJoystick1 / joystick1 * (absoluteValueJoystick1 * (1 - minValue) + minValue);
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
