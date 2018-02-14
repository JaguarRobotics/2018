package org.usd232.robotics.powerup;

import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.RobotMap.Alliance;
import org.usd232.robotics.powerup.RobotMap.CalibrationMode;
import org.usd232.robotics.powerup.RobotMap.StartingPosition;
import org.usd232.robotics.powerup.calibration.Calibration;
import org.usd232.robotics.powerup.calibration.CalibratorData;
import org.usd232.robotics.powerup.commands.Autonomous;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.LogServer;
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
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger                           LOG                      = new Logger();
    /**
     * chooser used on the SmartDashboard to choose the starting position
     * 
     * @since 2017
     */
    public static final SendableChooser<StartingPosition> positionChooser          = new SendableChooser<StartingPosition>();
    /**
     * Chooser used in SmartDashboard to choose which alliance we are on
     * 
     * @since 2017
     */
    public static final SendableChooser<Alliance>         allianceChooser          = new SendableChooser<Alliance>();
    public static CalibratorData                          calibratorData;
    public static boolean                                 isTesting                = false;
    public static int                                     amountOfThingsCalibrated = 0;
    /**
     * The command that the robot does for autonomous
     * 
     * @since Always
     * @version 2018
     */
    private Command                                       autonomousCommand;
    /**
     * Chooser used in SmartDashboard to choose which alliance we are on
     * 
     * @since 2017
     */
    public static final SendableChooser<CalibrationMode>  calibrationSetter        = new SendableChooser<CalibrationMode>();

    /**
     * What runs when the robot is initalized
     * 
     * @since Always
     * @version 2018
     */
    public void robotInit() {
        CommandBase.init();
        SmartDashboard.putNumber("Joystick Tolerance", 1);
        try {
            calibratorData = Calibration.readFile();
        } catch (Exception e) {
            LOG.error("We Have Not Created The Calibration Data File");
        }
        Thread thread = new Thread(new LogServer());
        thread.start();
        calibrationSetter.addDefault("Not Calibrating", RobotMap.CalibrationMode.NotCalibrating);
        calibrationSetter.addObject("Calibrating", RobotMap.CalibrationMode.Calibrating);
        SmartDashboard.putData("Calibration Setter", calibrationSetter);
        allianceChooser.addDefault("Blue", RobotMap.Alliance.Blue);
        allianceChooser.addObject("Red", RobotMap.Alliance.Red);
        SmartDashboard.putData("Alliance", allianceChooser);
        positionChooser.addDefault("Left", RobotMap.StartingPosition.One);
        positionChooser.addObject("Middle", RobotMap.StartingPosition.Two);
        positionChooser.addObject("Right", RobotMap.StartingPosition.Three);
        SmartDashboard.putData("Starting Position", positionChooser);
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
        LOG.trace("Autonomous Initalized");
        Robot.isTesting = false;
        autonomousCommand = new Autonomous();
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
        LOG.trace("Teleop Initalized");
        isTesting = false;
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
        Scheduler.getInstance().run();
    }

    /**
     * This function is called whenever test is enabled
     * 
     * @since Always
     * @version 2018
     */
    public void testInit() {
        LOG.trace("Test Initalized");
        Robot.isTesting = true;
    }
}
