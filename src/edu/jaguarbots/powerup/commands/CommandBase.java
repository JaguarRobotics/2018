package edu.jaguarbots.powerup.commands;

import edu.jaguarbots.powerup.OI;
import edu.jaguarbots.powerup.RobotMap;
import edu.jaguarbots.powerup.subsystems.DriveSubsystem;
import edu.jaguarbots.powerup.subsystems.IntakeSubsystem;
import edu.jaguarbots.powerup.subsystems.LiftSubsystem;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This is a class that establishes hierarchy and the ridiculous need to javadoc everything
 * 
 * @author Nathan Gawith
 */
public abstract class CommandBase extends Command implements RobotMap {
    /**
     * This is the oi
     */
    public static OI oi;
    /**
     * This creates the drive subsystem
     */
    public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
    /**
     * This creates the intake subsystem
     */
    public static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    /**
     * This creates the lift subsystem
     */
    public static final LiftSubsystem liftSubsystem = new LiftSubsystem();
    public CommandBase() {
	super();
    }
    public static void init() throws InterruptedException {
	try {
	    oi = new OI();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
