package org.usd232.robotics.powerup.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import org.usd232.robotics.autonomous.AutonomousModel;
import org.usd232.robotics.autonomous.AutonomousRoute;
import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.CustomCommandParameter;
import org.usd232.robotics.autonomous.DriveParameter;
import org.usd232.robotics.autonomous.SleepParameter;
import org.usd232.robotics.autonomous.TurnParameter;
import org.usd232.robotics.powerup.ISpeedFunction;
import org.usd232.robotics.powerup.drive.Delay;
import org.usd232.robotics.powerup.drive.DriveForward;
import org.usd232.robotics.powerup.drive.DriveTurn;
import org.usd232.robotics.powerup.log.Logger;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The class that sets up the routes to run in autonomous
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public class Autonomous extends CommandGroup {
    private static final Logger            LOG        = new Logger();
    private static final String            ROUTES_DIR = "/home/lvuser/routes";
    private static SendableChooser<String> chooser;

    public static void loadDashboard() {
        chooser = new SendableChooser<>();
        for (String file : new File(ROUTES_DIR).list()) {
            chooser.addObject(new String(Base64.getDecoder().decode(file)), file);
        }
        SmartDashboard.putData("Autonomous Route", chooser);
    }

    /**
     * Sets up this {@link CommandGroup} to run the autonomous commands
     * 
     * @since 2018
     * @version 2018
     */
    public Autonomous() {
        LOG.trace("Autonmous Started");
        ISpeedFunction driveSpeed = t->Math.min(0.8,
                        9.323308271 * t * t * t - 18.42105263 * t * t + 8.897744361 * t + 0.6);
        ISpeedFunction turnSpeed = t->Math.min(0.8,
                        9.674185464 * t * t * t - 18.68421053 * t * t + 8.910025063 * t + 0.6);
        AutonomousModel model = null;
        File file = new File(ROUTES_DIR, chooser.getSelected());
        try (FileInputStream stream = new FileInputStream(file)) {
            byte[] buffer = new byte[(int) file.length()];
            stream.read(buffer);
            model = new AutonomousModel(new String(buffer));
        } catch (IOException ex) {
            LOG.fatal("Unable to load autonomous file", ex);
            return;
        }
        AutonomousRoute route = model.getRoute(DriverStation.getInstance().getGameSpecificMessage());
        LOG.debug("Autonomous route:");
        for (AutonomousStep step : route.getSteps()) {
            switch (step.getType()) {
                case CustomCommand: {
                    CustomCommandParameter param = (CustomCommandParameter) step.getGenericParameter();
                    switch (param.getCommandID()) {
                        case 0: {
                            LOG.debug("Driving forward for %fs", param.getParameter());
                            DriveForward cmd = new DriveForward(driveSpeed, Double.MAX_VALUE, 0.1, 0.01);
                            cmd.setTimeoutPublic(Double.parseDouble(param.getParameter()));
                            break;
                        }
                        default:
                            LOG.error("Unknown command ID %d", param.getCommandID());
                    }
                    break;
                }
                case Drive: {
                    DriveParameter param = (DriveParameter) step.getGenericParameter();
                    double distance = param.getDistance() * model.getScale();
                    LOG.debug("Drive forward in %f", distance);
                    addSequential(new DriveForward(driveSpeed, distance, 0.1, 0.01));
                    break;
                }
                case Sleep: {
                    SleepParameter param = (SleepParameter) step.getGenericParameter();
                    LOG.debug("Sleep for %d ms", param.getMillis());
                    addSequential(new Delay(param.getMillis()));
                    break;
                }
                case Turn: {
                    TurnParameter param = (TurnParameter) step.getGenericParameter();
                    LOG.debug("Turn %f rad", param.getAngle());
                    addSequential(new DriveTurn(turnSpeed, param.getAngle()));
                    break;
                }
                default:
                    LOG.error("Unknown step type %s", step.getType());
            }
        }
    }
}
