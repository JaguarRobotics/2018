package org.usd232.robotics.powerup.minimap;

/**
 * An interface to test the minimap without a robot.
 * 
 * @author Zach
 * @since 2018
 * @version 2018
 */
public interface IMinimapCoordProvider {
    /**
     * Gets the X position.
     * 
     * @return the X
     */
    double getX();

    /**
     * Get the Y position.
     * 
     * @return the Y position of the robot.
     */
    double getY();

    /**
     * Gets the angle of the robot.
     * 
     * @return returns the angle of the robot.
     */
    double getAngle();
}
