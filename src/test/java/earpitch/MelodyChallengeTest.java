package earpitch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import earpitch.Challenge.Part;

public class MelodyChallengeTest {
	@Test
	public void hasNextElementIfItemsLeft() {
		assertThat(melody(Note.C4).hasNext(), is(true));
	}

	@Test
	public void hasNoMoreElementsIfEmpty() {
		assertThat(melody().hasNext(), is(false));
	}

	@Test
	public void returnElementsInSequence() {
		Challenge challenge = melody(Note.E4, Note.D4, Note.C4);
		assertThat(challenge.next().get(), is(Note.E4));
		assertThat(challenge.next().get(), is(Note.D4));
		assertThat(challenge.next().get(), is(Note.C4));
	}

	@Test
	public void returnFirstElement() {
		Part firstPart = melody(Note.D4).next();
		assertThat(firstPart.get(), is(Note.D4));
	}

	private Challenge melody(Note... notes) {
		return new MelodyChallenge(notes);
	}
}
