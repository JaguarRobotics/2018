package org.usd232.robotics.powerup;

import org.usd232.robotics.powerup.calibration.CalibratorData;
import org.usd232.robotics.powerup.commands.Autonomous;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.drive.TestDrive;
import org.usd232.robotics.powerup.subsystems.LocationSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The first class that is called to set everything up
 * 
 * @author Everyone
 * @since Always
 * @version 2018
 */
public class Robot extends IterativeRobot {
    public static CalibratorData        calibratorData;
    public static boolean               isTesting                = false;
    public static int                   amountOfThingsCalibrated = 0;
    /**
     * The command that the robot does for autonomous
     * 
     * @since Always
     * @version 2018
     */
    private Command                     autonomousCommand;
    /**
     * Chooser used in SmartDashboard to choose which alliance we are on
     * 
     * @since 2017
     */
    @SuppressWarnings("rawtypes")
    public static final SendableChooser calibrationSetter        = new SendableChooser();

    /**
     * What runs when the robot is initalized
     * 
     * @since Always
     * @version 2018
     */
    @SuppressWarnings("unchecked")
    public void robotInit() {
        CommandBase.init();
        autonomousCommand = new Autonomous();
        //autonomousCommand = new TestDrive(0.5);
        SmartDashboard.putNumber("Joystick Tolerance", 1);
//        try {
//            calibratorData = Calibration.readFile();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        calibrationSetter.addDefault("Not Calibrating", RobotMap.CalibrationMode.NotCalibrating);
//        calibrationSetter.addObject("Calibrating", RobotMap.CalibrationMode.Calibrating);
//        SmartDashboard.putData("Calibration Setter", calibrationSetter);
    }

    /**
     * What the robot does repeatedly while existing
     * 
     * @since Always
     * @version 2018
     */
    @Override
    public void robotPeriodic() {
        CommandBase.locationSubsystem.updateValues();
    }

    /**
     * What runs when the robot is disabled
     * 
     * @since Always
     * @version 2018
     */
    public void disabledInit() {
    	CommandBase.locationSubsystem.reset();
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
    	CommandBase.locationSubsystem.reset();
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
        isTesting = true;
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

    /**
     * This function is called whenever test is enabled
     * 
     * @since Always
     * @version 2018
     */
    public void testInit() {
    }
}
