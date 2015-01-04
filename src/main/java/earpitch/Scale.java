package earpitch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Iterables;
import com.google.common.primitives.Chars;

import earpitch.CircleOfFiths.Signature;

public enum Scale {
    IONIAN("Major", "TTsTTTs"), AEOLIAN("Minor", "TsTTsTT");

    public class Pointer {
        private Pitch base;
        private Pitch current;
        private ListIterator<Integer> iterator;
        private Signature signature;

        public Pointer(Pitch baseTone, Scale scale) {
            this.base = baseTone;
            this.current = baseTone;
            signature = CircleOfFiths.getSignature(base, Scale.this);
            iterator = intervals.listIterator();
        }

        public Pitch moveAndGet(int steps) {
            int position = iterator.nextIndex();
            int interval = toInterval(steps);

            List<Pitch> candidates = PITCH_BY_MIDINOTE.get(current.toMidiNote() + interval);

            if (candidates == null) {
                iterator = intervals.listIterator(position);
                throw new IndexOutOfBoundsException("Cannot move " + interval + " steps from " + current.name());
            }

            current = withMatchingSignature(candidates);

            return current;
        }

        public Pitch withMidiNote(int midiNote) {
            return withMatchingSignature(PITCH_BY_MIDINOTE.get(midiNote));
        }

        private int toInterval(int steps) {
            boolean isAscending = steps >= 0;

            int result = 0;
            for (int i = 0; i < Math.abs(steps); i++) {
                if (isAscending) {
                    if (!iterator.hasNext()) {
                        iterator = intervals.listIterator();
                    }

                    result += iterator.next();
                } else {
                    if (!iterator.hasPrevious()) {
                        iterator = intervals.listIterator(intervals.size());
                    }

                    result -= iterator.previous();
                }
            }
            return result;
        }

        private Pitch withMatchingSignature(List<Pitch> candidates) {
            return Iterables.tryFind(candidates, signature::matches).or(candidates.get(0));
        }
    }

    private static Map<Integer, List<Pitch>> createGroups() {
        return Arrays.stream(Pitch.values()).collect(Collectors.groupingBy(Pitch::toMidiNote));
    }

    private static Map<Integer, List<Pitch>> PITCH_BY_MIDINOTE = createGroups();
    private List<Integer> intervals;
    private String pattern;
    private String label;

    private Scale(String label, String pattern) {
        this.label = label;
        this.pattern = pattern;
        this.intervals = parse(pattern);
    }

    public boolean isMajor() {
        return pattern.charAt(2) == 's';
    }

    public Pitch pitchAt(int midiNote) {
        return null;
    }

    public List<Pitch> range(Pitch start, int length) {
        List<Pitch> result = new ArrayList<Pitch>();
        Pointer pointer = withBaseTone(start);
        result.add(pointer.moveAndGet(0));
        for (int i = 1; i < length; i++) {
            result.add(pointer.moveAndGet(1));
        }
        return result;
    }

    @Override
    public String toString() {
        return label;
    }

    public Pointer withBaseTone(Pitch baseTone) {
        return new Pointer(baseTone, this);
    }

    private List<Integer> parse(String pattern) {
        return Chars.asList(pattern.toCharArray())
                    .stream()
                    .map(symbol -> symbol == 's' ? 1 : 2)
                    .collect(Collectors.toList());
    }
}
