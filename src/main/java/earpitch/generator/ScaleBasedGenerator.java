package earpitch.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import earpitch.Generator;
import earpitch.Pitch;
import earpitch.Scale;
import earpitch.Scale.Pointer;

public class ScaleBasedGenerator implements Generator {
    private int maxStepSize = 8;
    private Scale scale;
    private Optional<Pitch> initialTone = Optional.empty();

    public ScaleBasedGenerator() {
        this(Scale.IONIAN);
    }

    public ScaleBasedGenerator(Scale scale) {
        this.scale = scale;
    }

    @Override
    public Pitch[] generate(int length) {
        Random random = new Random();
        Pointer pointer = scale.withBaseTone(initialTone.orElse(Pitch.C4));

        List<Pitch> result = generate(pointer, random, length);

        return result.toArray(new Pitch[length]);
    }

    public void setFirstTone(Pitch initialTone) {
        this.initialTone = Optional.of(initialTone);
    }

    List<Pitch> generate(Pointer pointer, Random random, int length) {
        int interval = random.nextInt(maxStepSize * 2 + 1) - maxStepSize;

        List<Pitch> result = new ArrayList<Pitch>(length);
        result.add(pointer.moveAndGet(0));
        for (int i = 1; i < length; i++) {
            result.add(next(pointer, interval));
        }

        return result;
    }

    private Pitch next(Pointer pointer, int interval) {
        try {
            return pointer.moveAndGet(interval);
        } catch (IndexOutOfBoundsException e) {
            return next(pointer, -interval);
        }
    }
}
