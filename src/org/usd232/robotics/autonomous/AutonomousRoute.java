package org.usd232.robotics.autonomous;

import java.nio.ByteBuffer;
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
public class AutonomousRoute implements IBufferSerializable {
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
     * {@inheritDoc}
     */
    @Override
    public void serialize(ByteBuffer ser) {
        Collection<String> configs = getSupportedConfigurations();
        ser.put((byte) configs.size());
        for (String config : configs) {
            byte[] buffer = config.getBytes();
            ser.put((byte) buffer.length);
            ser.put(buffer);
        }
        Collection<AutonomousStep> steps = getSteps();
        ser.put((byte) steps.size());
        for (AutonomousStep step : steps) {
            step.serialize(ser);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deserialize(ByteBuffer ser) {
        supportedConfigurations.clear();
        steps.clear();
        byte len = ser.get();
        for (int i = 0; i < len; ++i) {
            byte[] buffer = new byte[ser.get()];
            ser.get(buffer);
            addSupportedConfiguration(new String(buffer));
        }
        len = ser.get();
        for (int i = 0; i < len; ++i) {
            AutonomousStep step = new AutonomousStep();
            step.deserialize(ser);
            addStep(step);
        }
    }

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
        } else if (configuration.getBytes().length > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("configuration is too long");
        }
        for (AutonomousModel model : models) {
            if (model.containsRoute(configuration)) {
                throw new IllegalArgumentException(
                                "Route belongs to model(s) that already contain a route for this game-specific message");
            }
        }
        if (supportedConfigurations.size() >= Byte.MAX_VALUE) {
            throw new IllegalStateException("There are too many supported configurations");
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
        if (steps.size() >= Byte.MAX_VALUE) {
            throw new IllegalStateException("There are too many autonomous steps");
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
