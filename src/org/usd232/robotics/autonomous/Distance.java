package org.usd232.robotics.autonomous;

public class Distance implements IAutonomousStepParameter {
    public int distance;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}