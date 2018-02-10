package org.usd232.robotics.powerup;

@FunctionalInterface
public interface ISpeedFunction {
    double calculateSpeed(double time);
}
