package earpitch;

public interface Challenge {
	public interface Part {
		Note get();
	}

	boolean hasNext();

	Part next();
}
