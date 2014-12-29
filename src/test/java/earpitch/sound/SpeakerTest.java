package earpitch.sound;

import static earpitch.Pitch.C4;
import static earpitch.Pitch.D4;
import static earpitch.Pitch.F4;
import static org.mockito.Mockito.verify;

import java.time.Duration;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class SpeakerTest {
	public @Rule MockitoRule mockito = MockitoJUnit.rule();

	private @Mock Device device;

	@Test
	public void triggerDeviceToOutputMelody() {
		Duration duration = Duration.ofMillis(1);
		Speaker speaker = new Speaker(device, duration);
		speaker.play(C4, D4, F4);

		verify(device).send(C4.toMidiNote(), duration.toMillis());
		verify(device).send(D4.toMidiNote(), duration.toMillis());
		verify(device).send(F4.toMidiNote(), duration.toMillis());
	}
}
