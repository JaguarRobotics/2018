package org.usd232.robotics.powerup.learning;

public class LearningTest {
    private static final MachineLearner learner = new MachineLearner(2);

    private static void learn(double iv) {
        learner.learn(new Vector().setComponent("iv", iv * iv), new Vector().setComponent("dv", iv));
    }

    public static void main(String[] args) {
        for (int i = -10; i <= 10; ++i) {
            learn(i);
        }
        for (double i = -10; i <= 10; i += 0.25) {
            System.out.printf("%f ^ 2 = %f%n", i,
                            learner.calculate(new Vector().setComponent("dv", i)).getComponent("iv"));
        }
    }
}
