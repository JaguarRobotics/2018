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
     * @see SleepParameter
     * @since 2018
     */
    Sleep,
    /**
     * The robot should drive along a given distance curve.
     * 
     * @see DriveParameter
     * @since 2018
     */
    Drive,
    /**
     * The robot should turn a certain angle.
     * 
     * @see TurnParameter
     * @since 2018
     */
    Turn,
    /**
     * The robot should execute a command that was programmed for the specific game year.
     * 
     * @see CustomCommandParameter
     * @since 2018
     */
    CustomCommand
}
