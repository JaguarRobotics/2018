package org.usd232.robotics.autonomous;

import java.nio.ByteBuffer;

/**
 * An interface for a class that is serializable with the {@link ByteBuffer} class
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public interface IBufferSerializable {
    /**
     * Serializes this object to a given buffer
     * 
     * @param ser
     *            The buffer to serialize to
     * @since 2018
     */
    void serialize(ByteBuffer ser);

    /**
     * Deserializes this object from a given buffer
     * 
     * @param ser
     *            The buffer to serialize from
     * @since 2018
     */
    void deserialize(ByteBuffer ser);
}
