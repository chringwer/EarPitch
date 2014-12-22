package earpitch;

public class ChallengePart implements Challenge.Part {
	private Note note;

	public ChallengePart(Note note) {
		this.note = note;
	}

	@Override
	public Note get() {
		return note;
	}

}
