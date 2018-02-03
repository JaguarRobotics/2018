package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;
import org.usd232.robotics.powerup.log.Logger;

public class LocationSubsystem extends SubsystemBase {
    /**
     * The Logger
     * 
     * @since 2018
     * @version 2018
     */
    private static final Logger LOG = new Logger();
    private double lastLeftEncoder  = 0;
    private double lastRightEncoder = 0;
    private double lastX            = 0;
    private double lastY            = 0;
    private double lastAngle        = 0;

    @Override
    protected void initDefaultCommand() {
    }

    public double getX() {
        double encoderValueLeft = CommandBase.driveSubsystem.getEncoderLeft();
        double encoderValueRight = CommandBase.driveSubsystem.getEncoderRight();
        double angleValue = getAngle();
        double deltaAngle = Math.abs(angleValue - lastAngle);
        double deltaRightValue = encoderValueRight - lastRightEncoder;
        double deltaLeftValue = encoderValueLeft - lastLeftEncoder;
        double addedEncoderValues = deltaLeftValue + (deltaRightValue * -1);
        double versineAngle = 1 - Math.cos(deltaAngle);
        double cosAngle = Math.cos(deltaAngle);
        double sinAngle = Math.sin(deltaAngle);
        double x = (((((addedEncoderValues / deltaAngle - ROBOT_WIDTH) / 2) + ROBOT_WIDTH * .5) * versineAngle)
                        * sinAngle)
                        + (((((addedEncoderValues / deltaAngle - ROBOT_WIDTH) / 2) + ROBOT_WIDTH * .5) * sinAngle)
                                        * cosAngle)
                        + lastX;
        lastX = x;
        return x;
    }

    public double getY() {
        double encoderValueLeft = CommandBase.driveSubsystem.getEncoderLeft();
        double encoderValueRight = CommandBase.driveSubsystem.getEncoderRight();
        double angleValue = getAngle();
        double deltaAngle = Math.abs(angleValue - lastAngle);
        double deltaRightValue = encoderValueRight - lastRightEncoder;
        double deltaLeftValue = encoderValueLeft - lastLeftEncoder;
        double addedEncoderValues = deltaLeftValue + (deltaRightValue * -1);
        double versineAngle = 1 - Math.cos(deltaAngle);
        double cosAngle = Math.cos(deltaAngle);
        double sinAngle = Math.sin(deltaAngle);
        double y = (((((addedEncoderValues / deltaAngle - ROBOT_WIDTH) / 2) + ROBOT_WIDTH * .5) * versineAngle)
                        * cosAngle)
                        + (((((addedEncoderValues / deltaAngle - ROBOT_WIDTH) / 2) + ROBOT_WIDTH * .5) * sinAngle)
                                        * sinAngle)
                        + lastY;
        lastY = y;
        return y;
    }

    public double getAngle() {
        double angle = IO.gyro.getAngle();
        return angle;
    }
}
