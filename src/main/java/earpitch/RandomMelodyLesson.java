package earpitch;

import java.util.Random;
import java.util.stream.Stream;

public class RandomMelodyLesson implements Lesson {
	private int length;

	public RandomMelodyLesson() {
		this(0);
	}

	public RandomMelodyLesson(int length) {
		this.length = length;
	}

	@Override
	public Challenge nextChallenge() {
		Random random = new Random();
		Note[] notes = Note.values();
		Note[] melody = Stream.generate(() -> notes[random.nextInt(notes.length)]).limit(length).toArray(Note[]::new);

		return new MelodyChallenge(melody);
	}
}
