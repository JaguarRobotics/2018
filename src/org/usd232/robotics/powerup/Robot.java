package org.usd232.robotics.powerup;

import org.usd232.robotics.powerup.commands.CommandBase;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The first class that is called to set everything up
 * 
 * @author Everyone
 * @since Always
 * @version 2018
 */
public class Robot extends IterativeRobot {
    /**
     * The command that the robot does for autonomous
     * 
     * @since Always
     * @version 2018
     */
    private Command autonomousCommand;

    /**
     * What runs when the robot is initalized
     * 
     * @since Always
     * @version 2018
     */
    public void robotInit() {
        CommandBase.init();
        SmartDashboard.putNumber("Joystick Tolerance", 1);
    }

    /**
     * What the robot does repeatedly while existing
     * 
     * @since Always
     * @version 2018
     */
    @Override
    public void robotPeriodic() {
        super.robotPeriodic();
    }

    /**
     * What runs when the robot is disabled
     * 
     * @since Always
     * @version 2018
     */
    public void disabledInit() {
    }

    /**
     * What the robot does repeatedly while disabled
     * 
     * @since Always
     * @version 2018
     */
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * What autonomous does on start
     * 
     * @since Always
     * @version 2018
     */
    public void autonomousInit() {
        autonomousCommand.start();
    }

    /**
     * What the robot constantly does while in autonomous
     * 
     * @since Always
     * @version 2018
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called when teleop starts
     * 
     * @since Always
     * @version 2018
     */
    public void teleopInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    /**
     * This function is called repeatedly during teleop
     * 
     * @since Always
     * @version 2018
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called repeatedly while in test mode
     * 
     * @since Always
     * @version 2018
     */
    public void testPeriodic() {
    }
}
