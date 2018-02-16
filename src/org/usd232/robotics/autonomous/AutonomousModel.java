package org.usd232.robotics.autonomous;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
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
public class AutonomousModel implements IBufferSerializable {
    /**
     * The maximum size of the model
     * 
     * @since 2018
     */
    private static final int      MAX_SER_SIZE = 65536;
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
    public static final byte      modelVersion = 1;
    /**
     * The year of the game that this model was created for.
     * 
     * @since 2018
     */
    private short                 year;
    /**
     * The scale difference between the units used in this model and inches
     * 
     * @since 2018
     */
    private float                 scale        = 1;

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(ByteBuffer ser) {
        Collection<AutonomousRoute> routes = getRoutes();
        ser.put((byte) routes.size());
        for (AutonomousRoute route : routes) {
            route.serialize(ser);
        }
        ser.put(modelVersion);
        ser.putShort(year);
        ser.putFloat(scale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deserialize(ByteBuffer ser) {
        routes.clear();
        byte length = ser.get();
        for (int i = 0; i < length; ++i) {
            AutonomousRoute route = new AutonomousRoute();
            route.deserialize(ser);
            addRoute(route);
        }
        if (ser.get() != modelVersion) {
            throw new IllegalStateException("Wrong model version");
        }
        setYear(ser.getShort());
        setScale(ser.getFloat());
    }

    /**
     * Serializes this object
     * 
     * @return The binary data
     * @since 2018
     */
    public byte[] serialize() {
        ByteBuffer ser = ByteBuffer.allocate(MAX_SER_SIZE);
        serialize(ser);
        byte[] buffer = new byte[ser.position()];
        ser.position(0);
        ser.get(buffer);
        return buffer;
    }

    /**
     * Deserializes this object
     * 
     * @param buffer
     *            The binary data
     * @since 2018
     */
    public void deserialize(byte[] buffer) {
        deserialize(ByteBuffer.wrap(buffer));
    }

    /**
     * Serializes this object to a string
     * 
     * @return The string data
     * @since 2018
     */
    public String serializeString() {
        return Base64.getEncoder().encodeToString(serialize());
    }

    /**
     * Deserializes this object from a string
     * 
     * @param str
     *            The string data
     * @since 2018
     */
    public void deserialize(String str) {
        deserialize(Base64.getDecoder().decode(str));
    }

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
        if (route.models.size() >= Byte.MAX_VALUE) {
            throw new IllegalStateException("There are too many routes");
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
    public void setYear(short year) {
        if (year < 0) {
            throw new IllegalArgumentException("Year cannot be negative");
        }
        this.year = year;
    }

    /**
     * Gets the scale difference between the units used in this model and inches
     * 
     * @return The scale difference between the units used in this model and inches
     * @since 2018
     */
    public float getScale() {
        return scale;
    }

    /**
     * Sets the scale difference between the units used in this model and inches
     * 
     * @param scale
     *            The scale difference between the units used in this model and inches
     * @since 2018
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * Default constructor
     * 
     * @since 2018
     */
    public AutonomousModel() {
        routes = new ArrayList<AutonomousRoute>();
        setYear((short) Calendar.getInstance().get(Calendar.YEAR));
    }

    /**
     * Constructs from a serialized string
     * 
     * @param data
     *            The string data
     * @see AutonomousModel#deserialize(String)
     * @see AutonomousModel#serializeString()
     * @since 2018
     */
    public AutonomousModel(String data) {
        this();
        deserialize(data);
    }
}
