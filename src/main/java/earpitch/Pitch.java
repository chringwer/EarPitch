package earpitch;

public enum Pitch {
	A3, A$3, Bb3, B3, C4, C$4, Db4, D4, D$4, Eb4, E4, F4, F$4, Gb4, G4, G$4, Ab4, A4, A$4, Bb4, B4, C5, C$5, Db5, D5, D$5, Eb5, E5, F5, F$5, Gb5, G5, G$5, Ab5, A5;

	private int midi;

	static {
		Pitch[] values = Pitch.values();
		Pitch.A3.midi = 45;

		for (int i = 1; i < values.length; i++) {
			Pitch pitch = values[i];
			Pitch previous = values[i - 1];

			if (pitch.isFlat() && previous.isSharp()) {
				pitch.midi = previous.midi;
			} else {
				pitch.midi = previous.midi + 1;
			}
		}
	}

	public boolean isFlat() {
		return name().indexOf('b') > 0;
	}

	public boolean isSharp() {
		return name().indexOf('$') > 0;
	}

	public int toMidiNote() {
		return midi;
	}
}
