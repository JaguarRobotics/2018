package org.usd232.robotics.autonomous;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * The main model for the autonomous paths
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public class AutonomousModel {
    /**
     * The list of routes that are supported.
     * 
     * @since 2018
     */
    private List<AutonomousRoute> routes;
    /**
     * The version of the autonomous model that this object is.
     * 
     * @since 2018
     */
    public static final int       modelVersion = 1;
    /**
     * The year of the game that this model was created for.
     * 
     * @since 2018
     */
    private int                   year;

    /**
     * Gets the list of routes that are supported.
     * 
     * @return The list of routes that are supported
     * @since 2018
     */
    public Collection<AutonomousRoute> getRoutes() {
        return Collections.unmodifiableCollection(routes);
    }

    /**
     * Determines whether or not this model contains a route for a game-specific message.
     * 
     * @param configuration
     *            The game-specific message to check for
     * @return If this model contains a route for the game-specific message
     * @since 2018
     * @see DriverStation#getGameSpecificMessage()
     */
    public boolean containsRoute(String configuration) {
        for (AutonomousRoute route : routes) {
            if (route.getSupportedConfigurations().contains(configuration)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the route for a game-specific message.
     * 
     * @param configuration
     *            The game-specific message to check for
     * @return The route to use for the game-specific message
     * @see DriverStation#getGameSpecificMessage()
     * @since 2018
     */
    public AutonomousRoute getRoute(String configuration) {
        for (AutonomousRoute route : routes) {
            if (route.getSupportedConfigurations().contains(configuration)) {
                return route;
            }
        }
        throw new UnsupportedOperationException("The game-specific message is not mapped to any route");
    }

    /**
     * Adds a route to this model
     * 
     * @param route
     *            The route to add
     * @since 2018
     */
    public void addRoute(AutonomousRoute route) {
        if (route == null) {
            throw new NullPointerException("route cannot be null");
        }
        for (String configuration : route.getSupportedConfigurations()) {
            if (containsRoute(configuration)) {
                throw new IllegalArgumentException("Route contains configurations that already exist in this model");
            }
        }
        route.models.add(this);
        routes.add(route);
    }

    /**
     * Removes a route from this model
     * 
     * @param route
     *            The route to remove
     * @since 2018
     */
    public void removeRoute(AutonomousRoute route) {
        if (route == null) {
            throw new NullPointerException("route cannot be null");
        }
        if (!routes.remove(route)) {
            throw new IllegalArgumentException("route was not contained in model");
        }
    }

    /**
     * Gets the year that this model was created for.
     * 
     * @return The year that this model was created for
     * @since 2018
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year that this model was created for.
     * 
     * @param year
     *            The year that this model was created for
     * @since 2018
     */
    public void setYear(int year) {
        if (year < 0) {
            throw new IllegalArgumentException("Year cannot be negative");
        }
        this.year = year;
    }

    /**
     * Default constructor
     * 
     * @since 2018
     */
    public AutonomousModel() {
        routes = new ArrayList<AutonomousRoute>();
        year = Calendar.getInstance().get(Calendar.YEAR);
    }
}
