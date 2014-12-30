package earpitch;

import earpitch.sound.Speaker;

public class Challenge {
    private Pitch[] melody;
    private int cursor;

    public Challenge(Pitch... melody) {
        this.melody = melody;
        cursor = 0;
    }

    public boolean advanceIfMatches(Pitch pitch) {
        if (isSolved()) {
            throw new IllegalStateException("Already been solved.");
        }

        boolean matches = melody[cursor].isSameTone(pitch);

        if (matches) {
            cursor++;
        }

        return matches;
    }

    public Pitch getExpected() {
        if (cursor >= melody.length) {
            return null;
        }

        return melody[cursor];
    }

    public boolean isSolved() {
        return cursor == melody.length;
    }

    public void outputTo(Speaker speaker) {
        speaker.play(melody);
    }
}
