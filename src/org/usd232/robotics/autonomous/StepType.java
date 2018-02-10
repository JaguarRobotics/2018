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
     * The robot should drive along a given distance curve.
     * 
     * @since 2018
     */
    Drive,
    /**
     * The robot should turn a certain angle.
     * 
     * @sine 2018
     */
    Turn,
    /**
     * The robot should be able to go with a standard straight
     * 
     * @since 2018
     */
    EncoderDrive,
    /**
     * The robot should be able to tank turn with the gyro
     * 
     * @since 2018
     */
    GyroTurn,
    /**
     * The robot should execute a command that was programmed for the specific game year.
     * 
     * @since 2018
     */
    CustomCommand
}
