package earpitch.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.RandomDataGenerator;

import earpitch.Generator;
import earpitch.Pitch;
import earpitch.Scale.Pointer;

public class ScaleBasedGenerator implements Generator {
    @Override
    public Pitch[] generate(Options options) {
        RandomDataGenerator random = new RandomDataGenerator();
        Pointer pointer = options.getScale().withBaseTone(options.getKey());
        List<Pitch> result = generate(pointer, random, options);

        return result.toArray(new Pitch[result.size()]);
    }

    List<Pitch> generate(Pointer pointer, RandomDataGenerator random, Options options) {
        List<Pitch> result = new ArrayList<Pitch>(options.getLength());

        Pitch baseTone = pointer.moveAndGet(0);

        for (int i = 0; i < options.getLength(); i++) {
            result.add(next(pointer, random.nextInt(-options.getMaxStepSize(), options.getMaxStepSize())));
        }

        if (options.getAlwaysStartWithBaseTone()) {
            result.remove(options.getLength() - 1);
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
