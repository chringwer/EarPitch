package earpitch;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import earpitch.generator.RandomGenerator;

public class Trainer {
    public enum Option {
        FIXED_FIRST_TONE;
    }

    private Set<Option> options;
    private Generator generator;

    public Trainer(Option... options) {
        this.options = new HashSet<Trainer.Option>();

        if (options != null) {
            this.options.addAll(Arrays.asList(options));
        }

        updateGenerator();
    }

    public void disable(Option option) {
        options.remove(option);
        updateGenerator();
    }

    public void enable(Option option) {
        options.add(option);
        updateGenerator();
    }

    public Challenge nextChallenge() {
        return new Challenge(generator.generate(4));
    }

    public void set(Option option, boolean enabled) {
        if (enabled) {
            enable(option);
        } else {
            disable(option);
        }
    }

    private void updateGenerator() {
        RandomGenerator generator = new RandomGenerator();

        if (options.contains(Option.FIXED_FIRST_TONE)) {
            generator.setFirstTone(Pitch.A4);
        }

        this.generator = generator;
    }
}
