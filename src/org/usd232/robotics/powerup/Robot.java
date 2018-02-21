package org.usd232.robotics.powerup;

import org.usd232.robotics.powerup.RobotMap.Alliance;
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
 * @author Brian, Zach, Cody
 * @since 2018
 * @version 2018
 */
public class Robot extends IterativeRobot {
    /**
     * The Logger to use in the Robot class.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger                           LOG             = new Logger();
    /**
     * The server for the minimap project that sends the coords back and forth.
     * 
     * @since 2018
     * @version 2018
     */
    public static MinimapCoordsServer                     minimapServer;
    /**
     * chooser used on the SmartDashboard to choose the starting position.
     * 
     * @since 2017
     * @version 2018
     */
    public static final SendableChooser<StartingPosition> positionChooser = LOG
                    .catchAll(()->new SendableChooser<StartingPosition>());
    /**
     * Chooser used in SmartDashboard to choose which alliance we are on.
     * 
     * @since 2017
     * @version 2018
     */
    public static final SendableChooser<Alliance>         allianceChooser = LOG
                    .catchAll(()->new SendableChooser<Alliance>());
    /**
     * The data that is saved to the robot for the calibration.
     * 
     * @since 2018
     * @version 2018
     */
    public static CalibratorData                          calibratorData;
    /**
     * The command that the robot does for autonomous.
     * 
     * @since Always
     * @version 2018
     */
    private Command                                       autonomousCommand;

    /**
     * {@inheritDoc}
     */
    public void robotInit() {
        LOG.catchAll(()-> {
            try {
                Calibration.init();
                calibratorData = Calibration.readFile();
                LOG.info("Calibration Data:");
                LOG.info("Bottom: %f", calibratorData.getLiftBottom());
                LOG.info("Switch: %f", calibratorData.getLiftSwitch());
                LOG.info("Scale: %f", calibratorData.getLiftScale());
                LOG.info("Friction: %f", calibratorData.getFrictionalAcceleration());
            } catch (Exception e) {
                LOG.error("Exception in getting calibration file");
                calibratorData = new CalibratorData();
            }
            CommandBase.init();
            Thread thread = new Thread(new LogServer());
            thread.start();
            allianceChooser.addDefault("Blue", RobotMap.Alliance.Blue);
            allianceChooser.addObject("Red", RobotMap.Alliance.Red);
            SmartDashboard.putData("Alliance", allianceChooser);
            positionChooser.addDefault("Left", RobotMap.StartingPosition.One);
            positionChooser.addObject("Middle", RobotMap.StartingPosition.Two);
            positionChooser.addObject("Right", RobotMap.StartingPosition.Three);
            SmartDashboard.putData("Starting Position", positionChooser);
            Autonomous.loadDashboard();
            minimapServer = new MinimapCoordsServer(CommandBase.locationSubsystem.new Context());
            minimapServer.start();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void robotPeriodic() {
        LOG.catchAll(()-> {
            Scheduler.getInstance().run();
            CommandBase.locationSubsystem.updateValues();
        });
    }

    /**
     * {@inheritDoc}
     */
    public void disabledInit() {
    }

    /**
     * {@inheritDoc}
     */
    public void disabledPeriodic() {
    }

    /**
     * {@inheritDoc}
     */
    public void autonomousInit() {
        LOG.catchAll(()-> {
            LOG.trace("Autonomous Initalized");
            autonomousCommand = new Autonomous();
            autonomousCommand.start();
        });
    }

    /**
     * {@inheritDoc}
     */
    public void autonomousPeriodic() {
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    public void teleopPeriodic() {
    }

    /**
     * {@inheritDoc}
     */
    public void testPeriodic() {
        LOG.catchAll(()-> {
            CommandBase.driveSubsystem.driveTank(1, 1);
        });
    }

    /**
     * {@inheritDoc}
     */
    public void testInit() {
        LOG.catchAll(()-> {
            LOG.trace("Test Initalized");
        });
    }
}
