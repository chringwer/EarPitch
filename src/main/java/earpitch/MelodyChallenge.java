package earpitch;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class MelodyChallenge implements Challenge {
	private List<Part> parts;
	private Iterator<Part> iterator;

	public MelodyChallenge(Pitch... notes) {
		parts = Arrays.stream(notes).map((note) -> new ChallengePart(note)).collect(Collectors.toList());
		iterator = parts.iterator();
	}

	@Override
	public List<Pitch> getPitches() {
		return parts.stream().map(Part::get).collect(Collectors.toList());
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Part next() {
		return iterator.next();
	}
}
