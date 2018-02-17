package org.usd232.robotics.autonomous;

import java.nio.ByteBuffer;

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
    private float angle;

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(ByteBuffer ser) {
        ser.putFloat(getAngle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deserialize(ByteBuffer ser) {
        setAngle(ser.getFloat());
    }

    /**
     * Gets the angle to turn, in radians.
     * 
     * @return The angle to turn, in radians
     * @since 2018
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Sets the angle to turn, in radians.
     * 
     * @param angle
     *            The angle to turn, in radians
     * @since 2018
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }
}
