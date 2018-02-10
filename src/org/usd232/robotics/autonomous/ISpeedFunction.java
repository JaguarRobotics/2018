package org.usd232.robotics.autonomous;

/**
 * Interface for a function that can be used in order to determine the speed that the robot should take along a path.
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
@FunctionalInterface
public interface ISpeedFunction {
    /**
     * Determines the speed that the robot should drive at, given a time.
     * 
     * @param time
     *            The time, from [0, 1].
     * @return The speed, from (0, 1].
     * @since 2018
     */
    double determineSpeed(double time);
}
