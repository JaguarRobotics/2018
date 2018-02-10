package org.usd232.robotics.autonomous;

/**
 * The parameters for a sleep step
 * 
 * @author Zach Deibert
 * @see StepType#Sleep
 * @since 2018
 * @version 2018
 */
public class SleepParameter implements IAutonomousStepParameter {
    /**
     * The time to sleep, in milliseconds.
     * 
     * @since 2018
     */
    private short millis;

    /**
     * Gets the time to sleep, in milliseconds.
     * 
     * @return The time to sleep, in milliseconds
     * @since 2018
     */
    public short getMillis() {
        return millis;
    }

    /**
     * Sets the time to sleep, in milliseconds.
     * 
     * @param millis
     *            The time to sleep, in milliseconds
     * @since 2018
     */
    public void setMillis(short millis) {
        if (millis <= 0) {
            throw new IllegalArgumentException("millis must be positive");
        }
        this.millis = millis;
    }
}
