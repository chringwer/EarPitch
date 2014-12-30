package earpitch;

import earpitch.generator.RandomGenerator;

public class Trainer {
    private Generator generator;

    public Trainer() {
        generator = new RandomGenerator();
    }

    public Challenge nextChallenge() {
        return new Challenge(generator.generate(4));
    }
}
