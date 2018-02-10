package org.usd232.robotics.autonomous;

/**
 * Represents a single point.
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public class Point {
    /**
     * The x-coordinate of the point.
     * 
     * @since 2018
     */
    private double x;
    /**
     * The y-coordinate of the point.
     * 
     * @since 2018
     */
    private double y;

    /**
     * Gets the x-coordinate of the point.
     * 
     * @return The x-coordinate of the point
     * @since 2018
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the point.
     * 
     * @param x
     *            The x-coordinate of the point
     * @since 2018
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the y-coordinate of the point.
     * 
     * @return The y-coordinate of the point
     * @since 2018
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the point.
     * 
     * @param y
     *            The y-coordinate of the point
     * @since 2018
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Sets the point to (0, 0).
     * 
     * @since 2018
     */
    public Point() {
    }

    /**
     * Creates a new point.
     * 
     * @param x
     *            The x-coordinate of the point
     * @param y
     *            The y-coordinate of the point
     */
    public Point(double x, double y) {
        setX(x);
        setY(y);
    }
}
