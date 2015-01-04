package earpitch;

public interface Generator {
    public interface Options {
        boolean getAlwaysStartWithBaseTone();

        Pitch getKey();

        int getLength();

        int getMaxStepSize();

        Scale getScale();
    }

    Options DEFAULT_OPTIONS = new Options() {
        @Override
        public boolean getAlwaysStartWithBaseTone() {
            return true;
        }

        @Override
        public Pitch getKey() {
            return Pitch.C4;
        }

        @Override
        public int getLength() {
            return 4;
        }

        @Override
        public int getMaxStepSize() {
            return 8;
        }

        @Override
        public Scale getScale() {
            return Scale.IONIAN;
        }
    };

    Pitch[] generate(Options options);
}
