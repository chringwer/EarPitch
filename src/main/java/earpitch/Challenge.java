package earpitch;

public interface Challenge {
	public interface Part {
		Pitch get();
	}

	boolean hasNext();

	Part next();
}
