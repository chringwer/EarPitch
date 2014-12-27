package earpitch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

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
}
