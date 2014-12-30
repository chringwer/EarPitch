package earpitch.generator;

import java.util.Random;
import java.util.stream.Stream;

import earpitch.Generator;
import earpitch.Pitch;

public class RandomGenerator implements Generator {
    @Override
    public Pitch[] generate(int length) {
        Random random = new Random();
        Pitch[] notes = Pitch.values();
        Pitch[] melody = Stream.generate(() -> notes[random.nextInt(notes.length)])
                               .limit(length)
                               .toArray(Pitch[]::new);

        return melody;
    }
}
