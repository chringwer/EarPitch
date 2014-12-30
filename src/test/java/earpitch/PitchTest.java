package earpitch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import earpitch.Pitch;

public class PitchTest {
    @Test
    public void determineFlatNotes() {
        assertThat(Pitch.C$4.isFlat(), is(false));
        assertThat(Pitch.Db4.isFlat(), is(true));
        assertThat(Pitch.C4.isFlat(), is(false));
    }

    @Test
    public void determineSharpNotes() {
        assertThat(Pitch.C$4.isSharp(), is(true));
        assertThat(Pitch.Db4.isSharp(), is(false));
        assertThat(Pitch.C4.isSharp(), is(false));
    }

    @Test
    public void isMappedToMidiNote() {
        assertThat(Pitch.A3.toMidiNote(), is(45));
        assertThat(Pitch.C4.toMidiNote(), is(48));
        assertThat(Pitch.C5.toMidiNote(), is(60));
        assertThat(Pitch.C$5.toMidiNote(), is(61));
        assertThat(Pitch.Db5.toMidiNote(), is(61));
    }

    @Test
    public void prettyPrinted() {
        assertThat(Pitch.C$5.prettyPrinted(), is("C#"));
        assertThat(Pitch.Bb3.prettyPrinted(), is("Bb"));
        assertThat(Pitch.G4.prettyPrinted(), is("G"));
    }

    @Test
    public void sameIfSameFrequency() {
        assertThat(Pitch.Ab4.isSameTone(Pitch.G$4), is(true));
        assertThat(Pitch.Ab4.isSameTone(Pitch.Ab4), is(true));
        assertThat(Pitch.C4.isSameTone(Pitch.D4), is(false));
    }
}
