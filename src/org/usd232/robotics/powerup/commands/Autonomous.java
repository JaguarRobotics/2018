package org.usd232.robotics.powerup.commands;

import org.usd232.robotics.autonomous.BezierCurve;
import org.usd232.robotics.autonomous.Point;
import org.usd232.robotics.powerup.drive.MachineLearningBezierDrive;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The class that sets up the routes to run in autonomous
 * 
 * @author Brian, Cody
 * @since Always
 * @version 2018
 */
public class Autonomous extends CommandGroup {
    /**
     * This is what runs when you call autonomous
     * 
     * @since Always
     * @version 2018
     */
    public Autonomous() {
        BezierCurve curve = new BezierCurve();
        curve.addControlPoint(new Point(0.25, 0.25));
        curve.addControlPoint(new Point(0.25, 0.75));
        curve.addControlPoint(new Point(0.75, 0.75));
        addSequential(new ResetLocationCommand());
        addSequential(new MachineLearningBezierDrive(0.5, curve, 2 * 12 * 8, 2 * 12 * 8));
    }
}
