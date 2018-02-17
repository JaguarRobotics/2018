package org.usd232.robotics.autonomous;

import java.nio.ByteBuffer;

/**
 * The parameters for a custom command
 * 
 * @author Zach Deibert
 * @see StepType#CustomCommand
 * @since 2018
 * @version 2018
 */
public class CustomCommandParameter implements IAutonomousStepParameter {
    /**
     * The ID of the custom command to run.
     * 
     * @since 2018
     */
    private byte   commandID;
    /**
     * The parameter to the command.
     * 
     * @since 2018
     */
    private String parameter;

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(ByteBuffer ser) {
        ser.put(getCommandID());
        byte[] param = getParameter().getBytes();
        ser.putShort((short) param.length);
        ser.put(param);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deserialize(ByteBuffer ser) {
        setCommandID(ser.get());
        short length = ser.getShort();
        byte[] param = new byte[length];
        ser.get(param);
        setParameter(new String(param));
    }

    /**
     * Gets the ID of the custom command to run.
     * 
     * @return The ID of the command
     * @since 2018
     */
    public byte getCommandID() {
        return commandID;
    }

    /**
     * Sets the ID of the custom command to run.
     * 
     * @param commandID
     *            The ID of the command
     * @since 2018
     */
    public void setCommandID(byte commandID) {
        this.commandID = commandID;
    }

    /**
     * Gets the parameter to the command.
     * 
     * @return The parameter to the command
     * @since 2018
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * Sets the parameter to the command.
     * 
     * @param parameter
     *            The parameter to the command
     * @since 2018
     */
    public void setParameter(String parameter) {
        if (parameter == null) {
            throw new NullPointerException("parameter cannot be null");
        } else if (parameter.getBytes().length > Short.MAX_VALUE) {
            throw new IllegalArgumentException("parameter is too long");
        }
        this.parameter = parameter;
    }

    /**
     * Default constructor
     * 
     * @since 2018
     */
    public CustomCommandParameter() {
        setParameter("");
    }
}
