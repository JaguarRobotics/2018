package org.usd232.robotics.powerup.learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MachineLearner {
    private final int                       roundingFactor;
    private final Map<Vector, List<Vector>> knowledge;

    private List<Vector> getKnowledge(Vector dv) {
        if (knowledge.containsKey(dv)) {
            return knowledge.get(dv);
        } else {
            List<Vector> list = new ArrayList<Vector>();
            knowledge.put(dv, list);
            return list;
        }
    }

    public void learn(Vector iv, Vector dv) {
        getKnowledge(dv).add(iv);
    }

    public Vector calculate(Vector dv) {
        if (knowledge.size() == 0) {
            throw new IllegalStateException("Cannot calculate without any knowledge!");
        }
        List<Guess> guesses = new LinkedList<Guess>();
        for (Vector knownInput : knowledge.keySet()) {
            for (Vector knownOutput : knowledge.get(knownInput)) {
                guesses.add(new Guess(knownInput, knownOutput, dv.distanceTo(knownInput)));
            }
        }
        guesses.sort((a, b)->Double.compare(a.certainty, b.certainty));
        int max = Math.min(roundingFactor, guesses.size());
        Vector bestGuess = null;
        Iterator<Guess> guessIterator = guesses.iterator();
        for (int i = 0; i < max; ++i) {
            Guess guess = guessIterator.next();
            if (bestGuess == null) {
                bestGuess = guess.output;
            } else {
                bestGuess.addFrom(guess.output);
            }
        }
        bestGuess.divideBy(max);
        return bestGuess;
    }

    public MachineLearner(int roundingFactor) {
        this.roundingFactor = roundingFactor;
        knowledge = new HashMap<Vector, List<Vector>>();
    }
}
