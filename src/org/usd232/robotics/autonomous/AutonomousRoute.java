package org.usd232.robotics.autonomous;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * The model for a single route that the autonomous program can take. These can be differentiated by the different
 * game-specific messages
 * 
 * @author Zach Deibert
 * @see DriverStation#getGameSpecificMessage()
 * @since 2018
 * @version 2018
 */
public class AutonomousRoute {
    /**
     * A list of all the game-specific messages that are supported by this route.
     * 
     * @see DriverStation#getGameSpecificMessage()
     * @since 2018
     */
    private List<String>         supportedConfigurations;
    /**
     * A list of all the steps that this autonomous route contains.
     * 
     * @since 2018
     */
    private List<AutonomousStep> steps;
    /**
     * The list of models that this route is a part of.
     * 
     * @since 2018
     */
    List<AutonomousModel>        models;

    /**
     * Gets the list of all the game-specific messages that are supported by this route.
     * 
     * @return The list of all the game-specific messages that are supported by this route
     * @see DriverStation#getGameSpecificMessage()
     * @since 2018
     */
    public Collection<String> getSupportedConfigurations() {
        return Collections.unmodifiableCollection(supportedConfigurations);
    }

    /**
     * Adds a game-specific message that is supported by this route.
     * 
     * @param configuration
     *            The game-specific message to add
     * @see DriverStation#getGameSpecificMessage()
     * @since 2018
     */
    public void addSupportedConfiguration(String configuration) {
        if (configuration == null) {
            throw new NullPointerException("configuration cannot be null");
        }
        for (AutonomousModel model : models) {
            if (model.containsRoute(configuration)) {
                throw new IllegalArgumentException(
                                "Route belongs to model(s) that already contain a route for this game-specific message");
            }
        }
        supportedConfigurations.add(configuration);
    }

    /**
     * Removes a game-specific message that is supported by this route.
     * 
     * @param configuration
     *            The game-specific message to remove
     * @see DriverStation#getGameSpecificMessage()
     * @since 2018
     */
    public void removeSupportedConfiguration(String configuration) {
        if (configuration == null) {
            throw new NullPointerException("configuration cannot be null");
        }
        if (!supportedConfigurations.remove(configuration)) {
            throw new IllegalArgumentException("configuration was not supported by route");
        }
    }

    /**
     * Gets the list of all the steps that this autonomous route contains.
     * 
     * @return The list of all the steps that this autonomous route contains
     * @since 2018
     */
    public Collection<AutonomousStep> getSteps() {
        return Collections.unmodifiableCollection(steps);
    }

    /**
     * Adds a step that this autonomous route contains.
     * 
     * @param step
     *            The step to add
     * @since 2018
     */
    public void addStep(AutonomousStep step) {
        if (step == null) {
            throw new NullPointerException("step cannot be null");
        }
        steps.add(step);
    }

    /**
     * Removes a step that this autonomous route contains.
     * 
     * @param step
     *            The step to remove
     * @since 2018
     */
    public void removeStep(AutonomousStep step) {
        if (step == null) {
            throw new NullPointerException("step cannot be null");
        }
        if (!steps.remove(step)) {
            throw new IllegalArgumentException("step was not contained in route");
        }
    }

    /**
     * Default constructor
     * 
     * @since 2018
     */
    public AutonomousRoute() {
        supportedConfigurations = new ArrayList<String>();
        steps = new ArrayList<AutonomousStep>();
        models = new ArrayList<AutonomousModel>();
    }
}
