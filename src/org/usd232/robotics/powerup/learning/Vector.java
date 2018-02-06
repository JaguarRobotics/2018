package org.usd232.robotics.powerup.learning;

import java.util.HashMap;
import java.util.Map;

public class Vector implements Cloneable {
    private final Map<String, Double> components;

    public double getComponent(String name) {
        return components.get(name);
    }

    public Vector setComponent(String name, double value) {
        components.put(name, value);
        return this;
    }

    public double distanceTo(Vector other) {
        if (components.size() != other.components.size()) {
            throw new IllegalArgumentException("Vectors must contain the same number of components");
        }
        double squareDistance = 0;
        for (String key : components.keySet()) {
            if (!other.components.containsKey(key)) {
                throw new IllegalArgumentException("Vectors must contain the same components");
            }
            double a = getComponent(key);
            double b = other.getComponent(key);
            double diff = a - b;
            squareDistance += diff * diff;
        }
        return Math.sqrt(squareDistance);
    }

    public void addFrom(Vector other) {
        if (components.size() != other.components.size()) {
            throw new IllegalArgumentException("Vectors must contain the same number of components");
        }
        for (String key : components.keySet()) {
            if (!other.components.containsKey(key)) {
                throw new IllegalArgumentException("Vectors must contain the same components");
            }
            double a = getComponent(key);
            double b = other.getComponent(key);
            setComponent(key, a + b);
        }
    }

    public void divideBy(double scalar) {
        for (String key : components.keySet()) {
            setComponent(key, getComponent(key) / scalar);
        }
    }

    @Override
    public Vector clone() {
        return new Vector(new HashMap<String, Double>(components));
    }

    private Vector(Map<String, Double> components) {
        this.components = components;
    }

    public Vector() {
        this(new HashMap<String, Double>());
    }
}
