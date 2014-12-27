package earpitch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import earpitch.Challenge.Part;

public class MelodyChallengeTest {
	@Test
	public void hasNextElementIfItemsLeft() {
		assertThat(melody(Pitch.C4).hasNext(), is(true));
	}

	@Test
	public void hasNoMoreElementsIfEmpty() {
		assertThat(melody().hasNext(), is(false));
	}

	@Test
	public void returnElementsInSequence() {
		Challenge challenge = melody(Pitch.E4, Pitch.D4, Pitch.C4);
		assertThat(challenge.next().get(), is(Pitch.E4));
		assertThat(challenge.next().get(), is(Pitch.D4));
		assertThat(challenge.next().get(), is(Pitch.C4));
	}

	@Test
	public void returnFirstElement() {
		Part firstPart = melody(Pitch.D4).next();
		assertThat(firstPart.get(), is(Pitch.D4));
	}

	private Challenge melody(Pitch... notes) {
		return new MelodyChallenge(notes);
	}
}
