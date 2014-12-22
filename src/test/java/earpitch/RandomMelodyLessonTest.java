package earpitch;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RandomMelodyLessonTest {
	@Test
	public void challengesAreDiffrent() {
		Challenge challenge = new RandomMelodyLesson(2).nextChallenge();
		assertThat(challenge.next(), not(sameInstance(challenge.next())));
	}

	@Test
	public void emptyChallengeIfMelodyHasLengthZero() {
		Challenge challenge = new RandomMelodyLesson().nextChallenge();
		assertThat(challenge.hasNext(), is(false));
	}

	@Test
	public void numberOfChallengePartsMatchMelodyLength() {
		Challenge challenge = new RandomMelodyLesson(2).nextChallenge();
		assertThat(challenge.hasNext(), is(true));
		assertThat(challenge.next(), notNullValue());
		assertThat(challenge.next(), notNullValue());
		assertThat(challenge.hasNext(), is(false));
	}
}
