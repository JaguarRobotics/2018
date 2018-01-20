package org.usd232.robotics.powerup.commands;

import org.usd232.robotics.powerup.OI;
import org.usd232.robotics.powerup.RobotMap;
import org.usd232.robotics.powerup.subsystems.DriveSubsystem;
import org.usd232.robotics.powerup.subsystems.IntakeSubsystem;
import org.usd232.robotics.powerup.subsystems.LiftSubsystem;
import org.usd232.robotics.powerup.subsystems.LocationSubsystem;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This is a class that establishes hierarchy and the ridiculous need to javadoc everything
 * 
 * @author Nathan Gawith
 */
public abstract class CommandBase extends Command implements RobotMap {
    /**
     * This creates the oi
     * 
     * @since Always
     * @version 2018
     */
    public static OI                oi;
    /**
     * This creates the drive subsystem
     * 
     * @since Always
     * @version 2018
     */
    public static DriveSubsystem    driveSubsystem;
    /**
     * This creates the intake subsystem
     * 
     * @since 2018
     * @version 2018
     */
    public static IntakeSubsystem   intakeSubsystem;
    /**
     * This creates the lift subsystem
     * 
     * @since 2018
     * @version 2018
     */
    public static LiftSubsystem     liftSubsystem;
    /**
     * This creates the Tracking subsystem
     * 
     * @since 2018
     * @version 2018
     */
    public static LocationSubsystem locationSubsystem;

    /**
     * This is the class that is the base of the commands and initializes the subsystems
     * 
     * @since Always
     * @version 2018
     */
    public CommandBase() {
        super();
    }

    /**
     * This method intializes all of the subsystems
     * 
     * @since Always
     * @version 2018
     */
    public static void init() {
        oi = new OI();
        driveSubsystem = new DriveSubsystem();
        intakeSubsystem = new IntakeSubsystem();
        liftSubsystem = new LiftSubsystem();
        locationSubsystem = new LocationSubsystem();
    }
}
