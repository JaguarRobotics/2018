package org.usd232.robotics.powerup;

import org.usd232.robotics.powerup.RobotMap.Alliance;
import org.usd232.robotics.powerup.RobotMap.CalibrationMode;
import org.usd232.robotics.powerup.RobotMap.StartingPosition;
import org.usd232.robotics.powerup.calibration.Calibration;
import org.usd232.robotics.powerup.calibration.CalibratorData;
import org.usd232.robotics.powerup.commands.Autonomous;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.LogServer;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.minimap.MinimapCoordsServer;
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
    public static MinimapCoordsServer                     minimapServer;
    /**
     * chooser used on the SmartDashboard to choose the starting position
     * 
     * @since 2017
     */
    public static final SendableChooser<StartingPosition> positionChooser          = LOG
                    .catchAll(()->new SendableChooser<StartingPosition>());
    /**
     * Chooser used in SmartDashboard to choose which alliance we are on
     * 
     * @since 2017
     */
    public static final SendableChooser<Alliance>         allianceChooser          = LOG
                    .catchAll(()->new SendableChooser<Alliance>());
    public static CalibratorData                          calibratorData;
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
    public static final SendableChooser<CalibrationMode>  calibrationSetter        = LOG
                    .catchAll(()->new SendableChooser<CalibrationMode>());

    /**
     * What runs when the robot is initalized
     * 
     * @since Always
     * @version 2018
     */
    public void robotInit() {
        LOG.catchAll(()-> {
            SmartDashboard.putNumber("Joystick Tolerance", 1);
            try {
                Calibration.init();
                calibratorData = Calibration.readFile();
                LOG.info("Calibration Data:");
                LOG.info("Bottom: %f", calibratorData.getLiftBottom());
                LOG.info("Switch: %f", calibratorData.getLiftSwitch());
                LOG.info("Scale: %f", calibratorData.getLiftScale());
            } catch (Exception e) {
                LOG.error("Exception in getting calibration file");
                calibratorData = new CalibratorData();
            }
            CommandBase.init();
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
            Autonomous.loadDashboard();
            minimapServer = new MinimapCoordsServer(CommandBase.locationSubsystem);
            minimapServer.start();
        });
    }

    /**
     * What the robot does repeatedly while existing
     * 
     * @since Always
     * @version 2018
     */
    @Override
    public void robotPeriodic() {
        LOG.catchAll(()-> {
            Scheduler.getInstance().run();
            CommandBase.locationSubsystem.updateValues();
            LOG.info("Encoders are at Left %d, Right %d", IO.leftDriveEncoder.get(), IO.rightDriveEncoder.get());
        });
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
    }

    /**
     * What autonomous does on start
     * 
     * @since Always
     * @version 2018
     */
    public void autonomousInit() {
        LOG.catchAll(()-> {
            LOG.trace("Autonomous Initalized");
            autonomousCommand = new Autonomous();
            autonomousCommand.start();
        });
    }

    /**
     * What the robot constantly does while in autonomous
     * 
     * @since Always
     * @version 2018
     */
    public void autonomousPeriodic() {
    }

    /**
     * This function is called when teleop starts
     * 
     * @since Always
     * @version 2018
     */
    public void teleopInit() {
        LOG.catchAll(()-> {
            LOG.trace("Teleop Initalized");
            if (autonomousCommand != null) {
                autonomousCommand.cancel();
            }
        });
    }

    /**
     * This function is called repeatedly during teleop
     * 
     * @since Always
     * @version 2018
     */
    public void teleopPeriodic() {
    }

    /**
     * This function is called repeatedly while in test mode
     * 
     * @since Always
     * @version 2018
     */
    public void testPeriodic() {
        LOG.catchAll(()-> {
            CommandBase.driveSubsystem.driveTank(1, 1);
        });
    }

    /**
     * This function is called whenever test is enabled
     * 
     * @since Always
     * @version 2018
     */
    public void testInit() {
        LOG.catchAll(()-> {
            LOG.trace("Test Initalized");
        });
    }
}
