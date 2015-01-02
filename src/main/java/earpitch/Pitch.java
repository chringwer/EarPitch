package earpitch;

import static earpitch.Pitch.Traverser.Choice.FLAT;
import static earpitch.Pitch.Traverser.Choice.SHARP;

import java.util.Arrays;

import earpitch.Pitch.Traverser.Choice;

public enum Pitch {
    A3, A$3, Bb3, B3, C4, C$4, Db4, D4, D$4, Eb4, E4, F4, F$4, Gb4, G4, G$4, Ab4, A4, A$4, Bb4, B4, C5, C$5, Db5, D5, D$5, Eb5, E5, F5, F$5, Gb5, G5, G$5, Ab5, A5, Bb5, B5;

    public static class Traverser {
        static enum Choice {
            SHARP, FLAT;
        }

        private Pitch current;
        private Choice choice;

        private Traverser(Choice choice, Pitch base) {
            this.choice = choice;
            this.current = base;
        }

        public Pitch nextHalfTone() {
            current = findPitchWithMidiNoteIncrementedBy(1);
            return current;
        }

        public Pitch nextWholeTone() {
            current = findPitchWithMidiNoteIncrementedBy(2);
            return current;
        }

        private Pitch findPitchWithMidiNoteIncrementedBy(int increment) {
            return Arrays.stream(values())
                         .filter(pitch -> pitch.midiNote == current.midiNote + increment)
                         .filter(pitch -> {
                             if (pitch.isWhole()) {
                                 return true;
                             } else if (choice == FLAT) {
                                 return pitch.isFlat();
                             } else if (choice == SHARP) {
                                 return pitch.isSharp();
                             } else {
                                 throw new IllegalStateException();
                             }
                         })
                         .findFirst()
                         .orElse(null);
        }
    }

    public static Pitch highest() {
        Pitch[] values = Pitch.values();
        return values[values.length - 1];
    }

    private int midiNote;

    static {
        Pitch[] values = Pitch.values();
        Pitch.A3.midiNote = 45;

        for (int i = 1; i < values.length; i++) {
            Pitch pitch = values[i];
            Pitch previous = values[i - 1];

            if (pitch.isFlat() && previous.isSharp()) {
                pitch.midiNote = previous.midiNote;
            } else {
                pitch.midiNote = previous.midiNote + 1;
            }
        }
    }

    public boolean isFlat() {
        return name().indexOf('b') > 0;
    }

    public boolean isSameTone(Pitch other) {
        return midiNote == other.midiNote;
    }

    public boolean isSharp() {
        return name().indexOf('$') > 0;
    }

    public boolean isWhole() {
        return !isFlat() && !isSharp();
    }

    public String prettyPrinted() {
        return name().replace('$', '#').replaceAll("[0-9]", "");
    }

    public int toMidiNote() {
        return midiNote;
    }

    public Traverser traverse() {
        return new Traverser(isFlat() ? Choice.FLAT : Choice.SHARP, this);
    }

    public Traverser traverse(Choice choice) {
        return new Traverser(choice, this);
    }
}
