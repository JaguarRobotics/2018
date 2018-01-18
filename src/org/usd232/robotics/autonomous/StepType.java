package org.usd232.robotics.autonomous;

/**
 * The type of step that the autonomous route must take.
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public enum StepType {
    /**
     * The robot should wait for a given amount of time.
     * 
     * @since 2018
     */
    Sleep,
    /**
     * The robot should drive along a given bezier curve.
     * 
     * @since 2018
     */
    Bezier,
    /**
     * The robot should execute a command that was programmed for the specific game year.
     * 
     * @since 2018
     */
    CustomCommand
}
