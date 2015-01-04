package earpitch.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

import earpitch.Generator;
import earpitch.Pitch;
import earpitch.Scale.Pointer;

public class ScaleBasedGenerator implements Generator {
    private int maxStepSize = 8;

    @Override
    public Pitch[] generate(Options options) {
        RandomDataGenerator random = new RandomDataGenerator();
        Pointer pointer = options.getScale().withBaseTone(options.getKey());
        List<Pitch> result = generate(pointer, random, options.getLength(), options.getAlwaysStartWithBaseTone());

        return result.toArray(new Pitch[result.size()]);
    }

    List<Pitch> generate(Pointer pointer, RandomDataGenerator random, int length, boolean alwaysStartWithBaseTone) {
        List<Pitch> result = new ArrayList<Pitch>(length);

        Pitch baseTone = pointer.moveAndGet(0);

        for (int i = 0; i < length; i++) {
            result.add(next(pointer, random.nextInt(-maxStepSize, maxStepSize)));
        }

        if (alwaysStartWithBaseTone) {
            result.remove(length - 1);
            result.add(0, baseTone);
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
