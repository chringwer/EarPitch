package earpitch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import earpitch.CircleOfFiths.Signature;

public class CircleOfFithsTest {
    @Test
    public void ambiguosTones() {
        assertThat(CircleOfFiths.getSignature(Pitch.F$4, Scale.IONIAN), is(Signature.SHARP));
        assertThat(CircleOfFiths.getSignature(Pitch.Gb4, Scale.IONIAN), is(Signature.FLAT));
    }

    @Test
    public void majorScale() {
        assertThat(CircleOfFiths.getSignature(Pitch.G4, Scale.IONIAN), is(Signature.SHARP));
        assertThat(CircleOfFiths.getSignature(Pitch.C4, Scale.IONIAN), is(Signature.SHARP));
        assertThat(CircleOfFiths.getSignature(Pitch.F5, Scale.IONIAN), is(Signature.FLAT));
    }

    @Test
    public void minorScale() {
        assertThat(CircleOfFiths.getSignature(Pitch.E4, Scale.AEOLIAN), is(Signature.SHARP));
        assertThat(CircleOfFiths.getSignature(Pitch.C4, Scale.AEOLIAN), is(Signature.FLAT));
        assertThat(CircleOfFiths.getSignature(Pitch.F5, Scale.AEOLIAN), is(Signature.FLAT));
    }
}
