package org.usd232.robotics.autonomous;

import java.nio.ByteBuffer;

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
    private float distance;

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(ByteBuffer ser) {
        ser.putFloat(getDistance());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deserialize(ByteBuffer ser) {
        setDistance(ser.getFloat());
    }

    /**
     * Gets the distance, in inches, to drive forward.
     * 
     * @return The distance, in inches, to drive forward
     * @since 2018
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Sets the distance, in inches, to drive forward.
     * 
     * @param distance
     *            The distance, in inches, to drive forward
     * @sinec 2018
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }
}
