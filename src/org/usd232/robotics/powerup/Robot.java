package org.usd232.robotics.powerup;

import java.net.URL;
import java.net.URLClassLoader;
import org.usd232.robotics.powerup.calibration.Calibration;
import org.usd232.robotics.powerup.calibration.CalibratorData;
import org.usd232.robotics.powerup.commands.Autonomous;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.LogServer;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.minimap.MinimapCoordsServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

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
    private static final Logger       LOG = new Logger();
    /**
     * The server for the minimap project that sends the coords back and forth.
     * 
     * @since 2018
     * @version 2018
     */
    public static MinimapCoordsServer minimapServer;
    /**
     * The data that is saved to the robot for the calibration.
     * 
     * @since 2018
     * @version 2018
     */
    public static CalibratorData      calibratorData;
    /**
     * The command that the robot does for autonomous.
     * 
     * @since Always
     * @version 2018
     */
    private Command                   autonomousCommand;
    /**
     * The preferences instance.
     * 
     * @since 2018
     * @version 2018
     */
    public static Preferences         preferences;

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
            } catch (Exception e) {
                LOG.error("Exception in getting calibration file!");
                calibratorData = new CalibratorData();
            }
            preferences = Preferences.getInstance();
            CommandBase.init();
            Thread thread = new Thread(new LogServer());
            thread.start();
            Autonomous.loadDashboard();
            // minimapServer = new MinimapCoordsServer(CommandBase.locationSubsystem.new Context());
            // minimapServer.start();
            URLClassLoader cl = (URLClassLoader) ClassLoader.getSystemClassLoader();
            for (URL url : cl.getURLs()) {
                LOG.debug(url.getFile());
            }
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
