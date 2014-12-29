package earpitch.sound;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import earpitch.Pitch;

public class Speaker {
	private Device device;
	private Duration defaultDuration;

	public Speaker() {
		this(new LocalSynthesizer(), Duration.ofMillis(500));
	}

	public Speaker(Device device, Duration defaultDuration) {
		this.device = device;
		this.defaultDuration = defaultDuration;
	}

	public void play(List<Pitch> pitches) {
		for (Pitch pitch : pitches) {
			device.send(pitch.toMidiNote(), defaultDuration.toMillis());
		}
	}

	public void play(Pitch... pitches) {
		play(Arrays.asList(pitches));
	}
}
