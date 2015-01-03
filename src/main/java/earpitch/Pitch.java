package earpitch;

public enum Pitch {
    A3, A$3, Bb3, B3, C4, C$4, Db4, D4, D$4, Eb4, E4, F4, F$4, Gb4, G4, G$4, Ab4, A4, A$4, Bb4, B4, C5, C$5, Db5, D5, D$5, Eb5, E5, F5, F$5, Gb5, G5, G$5, Ab5, A5, Bb5, B5;

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

    public boolean isNatural() {
        return !isFlat() && !isSharp();
    }

    public String prettyPrinted() {
        return name().replace('$', '#').replaceAll("[0-9]", "");
    }

    public int toMidiNote() {
        return midiNote;
    }

    public Pitch withSameOctaveAs(Pitch other) {
        String myName = name();
        String otherName = other.name();
        String octave = String.valueOf(otherName.charAt(otherName.length() - 1));

        if (myName.endsWith(octave)) {
            return this;
        } else {
            return valueOf(myName.replaceFirst("[0-9]", octave));
        }
    }
}
