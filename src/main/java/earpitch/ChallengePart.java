package earpitch;

public class ChallengePart implements Challenge.Part {
	private Pitch note;

	public ChallengePart(Pitch note) {
		this.note = note;
	}

	@Override
	public Pitch get() {
		return note;
	}

}
