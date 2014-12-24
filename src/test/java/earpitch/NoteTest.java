package earpitch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NoteTest {
	@Test
	public void determineFlatNotes() {
		assertThat(Note.C$4.isFlat(), is(false));
		assertThat(Note.Db4.isFlat(), is(true));
		assertThat(Note.C4.isFlat(), is(false));
	}

	@Test
	public void determineSharpNotes() {
		assertThat(Note.C$4.isSharp(), is(true));
		assertThat(Note.Db4.isSharp(), is(false));
		assertThat(Note.C4.isSharp(), is(false));
	}
}
