package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.IOFactory;
import org.usd232.robotics.powerup.MotorID;
import org.usd232.robotics.powerup.RobotMap;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The base class for all of the subsystems
 * 
 * @author Zach Deibert
 * @since 2017
 * @version 2017
 */
abstract class SubsystemBase extends Subsystem implements RobotMap {
    /**
     * Creates a motor
     * 
     * @param channel
     *            The PWM channel that the motor is attached to. 0-9 are on-board, 10-19 are on the MXP port
     * @param motor
     *            The type of motor to create
     * @return The motor object
     * @since 2017
     */
    protected static SpeedController motor(int channel, MotorID motor) {
        return IOFactory.motor(channel, motor);
    }
}
