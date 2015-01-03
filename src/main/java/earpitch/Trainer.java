package earpitch;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import earpitch.generator.ScaleBasedGenerator;

public class Trainer {
    public enum Option {
        FIXED_FIRST_TONE;
    }

    private static final Pitch BASE_TONE = Pitch.C5;

    private Set<Option> options;
    private Generator generator;
    private Scale scale = Scale.IONIAN;

    public Trainer(Option... options) {
        this.options = new HashSet<Trainer.Option>();

        if (options != null) {
            this.options.addAll(Arrays.asList(options));
        }

        updateGenerator();
    }

    public Pitch adjustToScale(Pitch pitch) {
        return scale.withBaseTone(BASE_TONE).withMidiNote(pitch.toMidiNote());
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
        ScaleBasedGenerator generator = new ScaleBasedGenerator(scale);

        if (options.contains(Option.FIXED_FIRST_TONE)) {
            generator.setFirstTone(BASE_TONE);
        }

        this.generator = generator;
    }
}
