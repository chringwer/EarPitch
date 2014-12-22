package earpitch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import earpitch.Challenge.Part;

public class MelodyChallengeTest {
	@Test
	public void hasNoMoreElementsIfEmpty() {
		assertThat(melody().hasNext(), is(false));
	}

	@Test
	public void hasNextElementIfItemsLeft() {
		assertThat(melody(Note.C).hasNext(), is(true));
	}

	@Test
	public void returnFirstElement() {
		Part firstPart = melody(Note.D).next();
		assertThat(firstPart.get(), is(Note.D));
	}

	@Test
	public void returnElementsInSequence() {
		Challenge challenge = melody(Note.E, Note.D, Note.C);
		assertThat(challenge.next().get(), is(Note.E));
		assertThat(challenge.next().get(), is(Note.D));
		assertThat(challenge.next().get(), is(Note.C));
	}

	private Challenge melody(Note... notes) {
		return new MelodyChallenge(notes);
	}
}
