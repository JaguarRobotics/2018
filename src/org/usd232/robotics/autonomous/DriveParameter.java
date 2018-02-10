package org.usd232.robotics.autonomous;

/**
 * The parameters for a drive straight command
 * 
 * @author Zach Deibert
 * @see StepType#Drive
 * @since 2018
 * @version 2018
 */
public class DriveParameter implements IAutonomousStepParameter {
    /**
     * The distance, in inches, to drive forward.
     * 
     * @since 2018
     */
    private double distance;

    /**
     * Gets the distance, in inches, to drive forward.
     * 
     * @return The distance, in inches, to drive forward
     * @since 2018
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets the distance, in inches, to drive forward.
     * 
     * @param distance
     *            The distance, in inches, to drive forward
     * @sinec 2018
     */
    public void setDistance(double distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("Distance must not be negative");
        }
        this.distance = distance;
    }
}
