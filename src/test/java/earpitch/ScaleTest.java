package earpitch;

import static earpitch.Pitch.A$4;
import static earpitch.Pitch.A3;
import static earpitch.Pitch.A4;
import static earpitch.Pitch.B4;
import static earpitch.Pitch.Bb4;
import static earpitch.Pitch.C$4;
import static earpitch.Pitch.C4;
import static earpitch.Pitch.C5;
import static earpitch.Pitch.D4;
import static earpitch.Pitch.D5;
import static earpitch.Pitch.E4;
import static earpitch.Pitch.E5;
import static earpitch.Pitch.F$4;
import static earpitch.Pitch.F4;
import static earpitch.Pitch.G$4;
import static earpitch.Pitch.G4;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

import earpitch.Scale.Pointer;

public class ScaleTest {
    @Test
    public void aeolianScaleWithSharpNotes() {
        assertThat(Scale.AEOLIAN.range(E4, 8), contains(E4, F$4, G4, A4, B4, C5, D5, E5));
    }

    @Test
    public void determineMinorOrMajorStyle() {
        assertThat(Scale.IONIAN.isMajor(), is(true));
        assertThat(Scale.AEOLIAN.isMajor(), is(false));
    }

    @Test
    public void followKeySignature() {
        Pointer pointer = Scale.IONIAN.withBaseTone(F4);
        assertThat(pointer.moveAndGet(3), is(Bb4));
    }

    @Test
    public void ionianScaleOnlyNaturals() {
        assertThat(Scale.IONIAN.range(C4, 8), contains(C4, D4, E4, F4, G4, A4, B4, C5));
    }

    @Test
    public void ionianScaleWithSharpNotes() {
        assertThat(Scale.IONIAN.range(E4, 4), contains(E4, F$4, G$4, A4));
    }

    @Test
    public void navigateByPointerAeolian() {
        Pointer pointer = Scale.AEOLIAN.withBaseTone(E4);

        assertThat(pointer.moveAndGet(0), is(E4));
        assertThat(pointer.moveAndGet(-2), is(C4));
        assertThat(pointer.moveAndGet(3), is(F$4));
    }

    @Test
    public void navigateByPointerIonian() {
        Pointer pointer = Scale.IONIAN.withBaseTone(C4);

        assertThat(pointer.moveAndGet(0), is(C4));
        assertThat(pointer.moveAndGet(1), is(D4));
        assertThat(pointer.moveAndGet(1), is(E4));
        assertThat(pointer.moveAndGet(-2), is(C4));
    }

    @Test
    public void rollbackWhenOutOfBounds() {
        Pointer pointer = Scale.IONIAN.withBaseTone(A3);

        try {
            pointer.moveAndGet(-8);
            fail("no exception was thrown");
        } catch (IndexOutOfBoundsException e) {
        }

        assertThat(pointer.moveAndGet(2), is(C$4));
    }

    @Test
    public void translateMidiNoteToCorrectPitch() {
        assertThat(Scale.IONIAN.withBaseTone(F4).withMidiNote(Bb4.toMidiNote()), is(Bb4));
        assertThat(Scale.IONIAN.withBaseTone(F$4).withMidiNote(Bb4.toMidiNote()), is(A$4));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenPointerIsOutOfBounds() {
        Scale.IONIAN.withBaseTone(A3).moveAndGet(-5);
    }
}
