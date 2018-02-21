package org.usd232.robotics.powerup.commands;

import org.usd232.robotics.powerup.OI;
import org.usd232.robotics.powerup.RobotMap;
import org.usd232.robotics.powerup.log.Logger;
import org.usd232.robotics.powerup.subsystems.DriveSubsystem;
import org.usd232.robotics.powerup.subsystems.IntakeSubsystem;
import org.usd232.robotics.powerup.subsystems.LiftSubsystem;
import org.usd232.robotics.powerup.subsystems.LocationSubsystem;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This is a class that initializes the subsystems
 * 
 * @author Brian Parks, Zach Diebert
 * @since 2018
 * @version 2018
 */
public abstract class CommandBase extends Command implements RobotMap {
    /**
     * The Logger.
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger     LOG = new Logger();
    /**
     * This is the OI.
     * 
     * @since Always
     * @version 2018
     */
    public static OI                oi;
    /**
     * This is the drive subsystem.
     * 
     * @since Always
     * @version 2018
     */
    public static DriveSubsystem    driveSubsystem;
    /**
     * This is the intake subsystem.
     * 
     * @since 2018
     * @version 2018
     */
    public static IntakeSubsystem   intakeSubsystem;
    /**
     * This is the lift subsystem.
     * 
     * @since 2018
     * @version 2018
     */
    public static LiftSubsystem     liftSubsystem;
    /**
     * This is the location subsystem.
     * 
     * @since 2018
     * @version 2018
     */
    public static LocationSubsystem locationSubsystem;

    /**
     * Public initialize command for commands to use if needed.
     * 
     * @since 2018
     * @version 2018
     */
    public void initializePublic() {
        initialize();
    }

    /**
     * Public execute command for commands to use if needed.
     * 
     * @since 2018
     * @version 2018
     */
    public void executePublic() {
        execute();
    }

    /**
     * Public isFinished command for commands to use if needed.
     * 
     * @since 2018
     * @version 2018
     */
    public boolean isFinishedPublic() {
        return isFinished();
    }

    /**
     * Public end command for commands to use if needed.
     * 
     * @since 2018
     * @version 2018
     */
    public void endPublic() {
        end();
    }

    /**
     * This method initializes all of the subsystems.
     * 
     * @since 2018
     * @version 2018
     */
    public static void init() {
        driveSubsystem = new DriveSubsystem();
        intakeSubsystem = new IntakeSubsystem();
        liftSubsystem = new LiftSubsystem();
        locationSubsystem = new LocationSubsystem();
        oi = new OI();
        LOG.info("Subsystems Have Been Initialized");
    }
}
