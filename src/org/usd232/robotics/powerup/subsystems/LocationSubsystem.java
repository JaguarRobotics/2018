package org.usd232.robotics.powerup.subsystems;

import org.usd232.robotics.powerup.IO;
import org.usd232.robotics.powerup.commands.CommandBase;

public class LocationSubsystem extends SubsystemBase {
    private double       lastLeftEncoder  = 0;
    private double       lastRightEncoder = 0;
    private double       lastX            = 0;
    private double       lastY            = 0;
    private double       lastAngle        = 0;
    private final double angleOffset;

    public LocationSubsystem() {
        CommandBase.driveSubsystem.resetEncoders(true, true);
        angleOffset = getAngle();
    }

    @Override
    protected void initDefaultCommand() {
    }

    public double getX() {
        double encoderValueLeft = CommandBase.driveSubsystem
                        .getDistanceInInches(CommandBase.driveSubsystem.getEncoderLeft());
        double encoderValueRight = CommandBase.driveSubsystem
                        .getDistanceInInches(CommandBase.driveSubsystem.getEncoderRight());
        double angleValue = getAngle();
        double deltaAngle = Math.abs(angleValue - lastAngle);
        double deltaRightValue = encoderValueRight - lastRightEncoder;
        double deltaLeftValue = encoderValueLeft - lastLeftEncoder;
        double addedEncoderValues = deltaLeftValue + (deltaRightValue * -1);
        double versineAngle = 1 - Math.cos(deltaAngle);
        double cosAngle = Math.cos(angleValue);
        double sinAngle = Math.sin(angleValue);
        double sinDeltaAngle = Math.sin(deltaAngle);
        if (deltaAngle != 0) {
            lastX += (((((addedEncoderValues / deltaAngle - ROBOT_WIDTH) / 2) + ROBOT_WIDTH * .5) * versineAngle)
                            * cosAngle)
                            + (((((addedEncoderValues / deltaAngle - ROBOT_WIDTH) / 2) + ROBOT_WIDTH * .5)
                                            * sinDeltaAngle) * sinAngle);
        } else {
            lastX += addedEncoderValues / 2 * sinAngle;
        }
        return lastX;
    }

    public double getY() {
        double encoderValueLeft = CommandBase.driveSubsystem
                        .getDistanceInInches(CommandBase.driveSubsystem.getEncoderLeft());
        double encoderValueRight = CommandBase.driveSubsystem
                        .getDistanceInInches(CommandBase.driveSubsystem.getEncoderRight());
        double angleValue = getAngle();
        double deltaAngle = Math.abs(angleValue - lastAngle);
        double deltaRightValue = encoderValueRight - lastRightEncoder;
        double deltaLeftValue = encoderValueLeft - lastLeftEncoder;
        double addedEncoderValues = deltaLeftValue + (deltaRightValue * -1);
        double versineAngle = 1 - Math.cos(deltaAngle);
        double cosAngle = Math.cos(angleValue);
        double sinAngle = Math.sin(angleValue);
        double sinDeltaAngle = Math.sin(deltaAngle);
        if (deltaAngle != 0) {
            lastY += (((((addedEncoderValues / deltaAngle - ROBOT_WIDTH) / 2) + ROBOT_WIDTH * .5) * versineAngle)
                            * sinAngle)
                            + (((((addedEncoderValues / deltaAngle - ROBOT_WIDTH) / 2) + ROBOT_WIDTH * .5)
                                            * sinDeltaAngle) * cosAngle);
        } else {
            lastY += addedEncoderValues / 2 * cosAngle;
        }
        return lastY;
    }

    public double getAngle() {
        double angle = IO.gyro.getAngle();
        return angle - angleOffset;
    }

    public void setLastVariables() {
        lastLeftEncoder = CommandBase.driveSubsystem.getDistanceInInches(CommandBase.driveSubsystem.getEncoderLeft());
        lastRightEncoder = CommandBase.driveSubsystem.getDistanceInInches(CommandBase.driveSubsystem.getEncoderRight());
        lastAngle = getAngle();
    }
}
