package org.usd232.robotics.autonomous;

/**
 * The parameters for a turning command
 * 
 * @author Zach Deibert
 * @see StepType#Turn
 * @since 2018
 * @version 2018
 */
public class TurnParameter implements IAutonomousStepParameter {
    /**
     * The angle to turn, in radians.
     * 
     * @since 2018
     */
    private double angle;

    /**
     * Gets the angle to turn, in radians.
     * 
     * @return The angle to turn, in radians
     * @since 2018
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Sets the angle to turn, in radians.
     * 
     * @param angle
     *            The angle to turn, in radians
     * @since 2018
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }
}
