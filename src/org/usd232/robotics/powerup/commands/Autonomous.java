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
import org.usd232.robotics.powerup.Robot;
import org.usd232.robotics.powerup.drive.Delay;
import org.usd232.robotics.powerup.drive.DriveForward;
import org.usd232.robotics.powerup.drive.DriveTurn;
import org.usd232.robotics.powerup.drive.PathfinderDrive;
import org.usd232.robotics.powerup.intake.DropCube;
import org.usd232.robotics.powerup.intake.GrabCube;
import org.usd232.robotics.powerup.intake.LowerIntake;
import org.usd232.robotics.powerup.intake.RaiseIntake;
import org.usd232.robotics.powerup.lift.GoToLevel;
import org.usd232.robotics.powerup.lift.Lower;
import org.usd232.robotics.powerup.lift.LowerToBottom;
import org.usd232.robotics.powerup.lift.Raise;
import org.usd232.robotics.powerup.lift.RaiseToScale;
import org.usd232.robotics.powerup.log.Logger;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The class that sets up the routes to run in autonomous
 * 
 * @author Zach Deibert, Brian Parks
 * @since 2018
 * @version 2018
 */
public class Autonomous extends CommandGroup {
    /**
     * The logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger            LOG        = new Logger();
    /**
     * The directory we save the auto routes to.
     * 
     * @since 2018
     * @version 2018
     */
    private static final String            ROUTES_DIR = "/home/lvuser/routes";
    /**
     * The sendable chooser where we select the auto route.
     * 
     * @since 2018
     * @version 2018
     */
    private static SendableChooser<String> chooser;
    /**
     * Loads the routes to the smart dashboard
     */
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
        addSequential(PathfinderDrive.test());
        LOG.trace("Sequentially added");
        /*ISpeedFunction driveSpeed = t->0.6;
        ISpeedFunction turnSpeed = t->Math.min(0.8,
                        9.674185464 * t * t * t - 18.68421053 * t * t + 8.910025063 * t + 0.6);
        AutonomousModel model = null;
        File file = new File(ROUTES_DIR, chooser.getSelected());
        try (FileInputStream stream = new FileInputStream(file)) {
            byte[] buffer = new byte[(int) file.length()];
            stream.read(buffer);
            model = new AutonomousModel(new String(buffer));
        } catch (IOException ex) {
            LOG.fatal(ex, "Unable to load autonomous file");
            return;
        }
        AutonomousRoute route = model.getRoute(DriverStation.getInstance().getGameSpecificMessage());
        LOG.debug("Autonomous route:");
        for (AutonomousStep step : route.getSteps()) {
            switch (step.getType()) {
                case CustomCommand: {
                    CustomCommandParameter param = (CustomCommandParameter) step.getGenericParameter();
                    switch (param.getCommandID()) {
                        case 0:
                            LOG.debug("Dropping cube");
                            addSequential(new DropCube());
                            break;
                        case 1:
                            LOG.debug("Grabbing cube");
                            addSequential(new GrabCube());
                            break;
                        case 2:
                            LOG.debug("Lowering intake");
                            addSequential(new LowerIntake());
                            break;
                        case 3:
                            LOG.debug("Raise intake");
                            addSequential(new RaiseIntake());
                            break;
                        case 4:
                            LOG.debug(String.format("Going to level %s", param.getParameter()));
                            switch (param.getParameter()) {
                                case "bottom":
                                    addSequential(new GoToLevel(()->Robot.calibratorData.getLiftBottom()));
                                    break;
                                case "switch":
                                    addSequential(new GoToLevel(()->Robot.calibratorData.getLiftSwitch()));
                                    break;
                                case "scale":
                                    addSequential(new GoToLevel(()->Robot.calibratorData.getLiftScale()));
                                    break;
                                default:
                                    LOG.error("Unknown level");
                                    break;
                            }
                            break;
                        case 5:
                            double lowerD = Double.parseDouble(param.getParameter());
                            LOG.debug("Lowering");
                            addSequential(new Lower(lowerD));
                            break;
                        case 6:
                            double raiseD = Double.parseDouble(param.getParameter());
                            LOG.debug("Raise");
                            addSequential(new Raise(raiseD));
                            break;
                        case 7:
                            LOG.debug("Raise to limit switch");
                            addSequential(new RaiseToScale());
                            break;
                        case 8:
                            LOG.debug("Lower to limit switch");
                            addSequential(new LowerToBottom());
                            break;
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
        }*/
    }
}
