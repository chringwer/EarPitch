package earpitch;

import earpitch.generator.ScaleBasedGenerator;

public class Trainer {
    private static final Pitch BASE_TONE = Pitch.C5;

    private Generator.Options options;
    private Generator generator;

    public Trainer() {
        this(Generator.DEFAULT_OPTIONS);
    }

    public Trainer(Generator.Options options) {
        this.options = options;
        this.generator = new ScaleBasedGenerator();
    }

    public Pitch adjustToScale(Pitch pitch) {
        return options.getScale().withBaseTone(BASE_TONE).withMidiNote(pitch.toMidiNote());
    }

    public Generator.Options getOptions() {
        return options;
    }

    public Challenge nextChallenge() {
        return new Challenge(generator.generate(options));
    }

    public void setOptions(Generator.Options options) {
        this.options = options;
    }
}
