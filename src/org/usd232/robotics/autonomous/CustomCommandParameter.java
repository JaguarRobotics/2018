package org.usd232.robotics.autonomous;

/**
 * The parameters for a custom command
 * 
 * @author Zach Deibert
 * @since 2018
 * @version 2018
 */
public class CustomCommandParameter implements IAutonomousStepParameter {
    /**
     * The ID of the custom command to run.
     * 
     * @since 2018
     */
    private byte commandID;

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
}
