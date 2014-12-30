package earpitch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import earpitch.Challenge;
import earpitch.Pitch;

public class ChallengeTest {
    @Test
    public void advancesOnSecondTryIfCorrect() {
        Challenge solver = new Challenge(Pitch.C4, Pitch.D4);
        assertThat(solver.advanceIfMatches(Pitch.A4), is(false));
        assertThat(solver.advanceIfMatches(Pitch.C4), is(true));
        assertThat(solver.advanceIfMatches(Pitch.D4), is(true));
    }

    @Test
    public void advancesTheCursorIfInputMatched() {
        Challenge solver = new Challenge(Pitch.C4, Pitch.D4);
        assertThat(solver.advanceIfMatches(Pitch.C4), is(true));
    }

    @Test
    public void comparesToneRatherThanInstance() {
        Challenge solver = new Challenge(Pitch.Ab4);
        assertThat(solver.advanceIfMatches(Pitch.G$4), is(true));
    }

    @Test
    public void cursorFreezesIfInputMissmatched() {
        Challenge solver = new Challenge(Pitch.C4, Pitch.D4);
        assertThat(solver.advanceIfMatches(Pitch.A4), is(false));
    }

    @Test
    public void returnsNextExpectedPitch() {
        Challenge solver = new Challenge(Pitch.C4, Pitch.D4);
        assertThat(solver.getExpected(), is(Pitch.C4));
        solver.advanceIfMatches(Pitch.C4);
        assertThat(solver.getExpected(), is(Pitch.D4));
    }

    @Test
    public void isSolvedIfAllTonesWereMatched() {
        Challenge solver = new Challenge(Pitch.E4);
        assertThat(solver.isSolved(), is(false));
        solver.advanceIfMatches(Pitch.E4);
        assertThat(solver.isSolved(), is(true));
    }

    @Test(expected = IllegalStateException.class)
    public void throwsOnInputAfterBeingComplete() {
        Challenge solver = new Challenge(Pitch.E4);
        solver.advanceIfMatches(Pitch.E4);
        solver.advanceIfMatches(Pitch.F4);
    }
}
