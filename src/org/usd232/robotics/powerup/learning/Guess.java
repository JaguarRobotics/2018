package org.usd232.robotics.powerup.learning;

class Guess {
    public final Vector input;
    public final Vector output;
    public final double certainty;

    public Guess(Vector input, Vector output, double certainty) {
        this.input = input;
        this.output = output;
        this.certainty = certainty;
    }
}
