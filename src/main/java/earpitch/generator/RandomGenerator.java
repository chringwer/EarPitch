package earpitch.generator;

import java.util.Random;
import java.util.stream.Stream;

import earpitch.Generator;
import earpitch.Pitch;

public class RandomGenerator implements Generator {
    private Pitch initial;

    @Override
    public Pitch[] generate(int length) {
        Random random = new Random();
        Pitch[] notes = Pitch.values();
        Pitch[] melody = Stream.generate(() -> notes[random.nextInt(notes.length)]).limit(length).toArray(Pitch[]::new);

        if (initial != null) {
            melody[0] = initial;
        }

        return melody;
    }

    public void setFirstTone(Pitch pitch) {
        initial = pitch;
    }
}
