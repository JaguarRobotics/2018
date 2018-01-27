package org.usd232.robotics.powerup.commands;

import org.usd232.robotics.autonomous.BezierCurve;
import org.usd232.robotics.autonomous.Point;
import org.usd232.robotics.powerup.drive.BezierDrive;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The class that sets up the routes to run in autonomous
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public class Autonomous extends CommandGroup {
    /**
     * Sets up this {@link CommandGroup} to run the autonomous commands
     * 
     * @since 2018
     * @version 2018
     */
    public Autonomous() {
        BezierCurve curve = new BezierCurve();
        curve.addControlPoint(new Point(0.25, 0.25));
        curve.addControlPoint(new Point(0.25, 0.75));
        curve.addControlPoint(new Point(0.75, 0.75));
        addSequential(new BezierDrive(curve, 2 * 12 * 8, 2 * 12 *8, 18, 0.5));
    }
}
