package earpitch.generator;

import earpitch.Generator;
import earpitch.Generator.Options;
import earpitch.Pitch;
import earpitch.Scale;

public class TestOptions implements Generator.Options {
    public static Options withKey(Pitch key) {
        TestOptions options = new TestOptions(Generator.DEFAULT_OPTIONS);
        options.key = key;
        return options;
    }

    public static Options withLength(int length) {
        TestOptions options = new TestOptions(Generator.DEFAULT_OPTIONS);
        options.length = length;
        return options;
    }

    public static Options withMaxStepSize(int maxStepSize) {
        TestOptions options = new TestOptions(Generator.DEFAULT_OPTIONS);
        options.maxStepSize = maxStepSize;
        return options;
    }

    private boolean startWithBaseTone;
    private Pitch key;
    private Scale scale;
    private int length;

    private int maxStepSize;

    private TestOptions(Generator.Options defaults) {
        this.startWithBaseTone = defaults.getAlwaysStartWithBaseTone();
        this.key = defaults.getKey();
        this.scale = defaults.getScale();
        this.length = defaults.getLength();
        this.maxStepSize = defaults.getMaxStepSize();
    }

    @Override
    public boolean getAlwaysStartWithBaseTone() {
        return startWithBaseTone;
    }

    @Override
    public Pitch getKey() {
        return key;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int getMaxStepSize() {
        return maxStepSize;
    }

    @Override
    public Scale getScale() {
        return scale;
    }
}
