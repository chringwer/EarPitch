package earpitch;

import java.util.stream.IntStream;

public class CircleOfFiths {
    enum Signature {
        SHARP, FLAT;

        public boolean matches(Pitch pitch) {
            return pitch.isSharp() && this == SHARP || pitch.isFlat() && this == FLAT;
        }
    }

    public static Signature getSignature(Pitch base, Scale scale) {
        int distance = distance(base);

        if (!base.isNatural()) {
            return base.isFlat() ? Signature.FLAT : Signature.SHARP;
        }

        if (scale.isMajor()) {
            return distance > 6 ? Signature.FLAT : Signature.SHARP;
        } else {
            return distance > 3 && distance < 9 ? Signature.SHARP : Signature.FLAT;
        }
    }

    private static int distance(Pitch base) {
        int[] range = IntStream.range(Pitch.C4.toMidiNote(), Pitch.C5.toMidiNote()).toArray();
        int target = base.withSameOctaveAs(Pitch.C4).toMidiNote();

        int distance = 0;
        for (int idx = 0; range[idx % range.length] != target; idx += FIFTH_INTERVAL) {
            distance++;
        }

        return distance;
    }

    private static final int FIFTH_INTERVAL = 7;
}
