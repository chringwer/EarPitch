package earpitch;

import java.util.Iterator;
import java.util.List;

import earpitch.Challenge.Part;

public interface Challenge extends Iterable<Part>, Iterator<Part> {
	public interface Part {
		Pitch get();
	}

	public List<Pitch> getPitches();

	@Override
	default public Iterator<Part> iterator() {
		return this;
	}

	@Override
	boolean hasNext();

	@Override
	Part next();
}
