package earpitch.sound;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class LocalSynthesizerTest {
	public @Rule MockitoRule mockito = MockitoJUnit.rule();

	private @Mock Synthesizer midi;
	private @Mock MidiChannel piano;

	@Before
	public void before() {
		when(midi.getChannels()).thenReturn(new MidiChannel[] { piano });
	}

	@Test
	public void sendNoteOnAndOffEventsToThePiano() {
		sendNote(60);

		verify(piano).noteOn(eq(60), anyInt());
		verify(piano).noteOff(eq(60));
	}

	@Test
	public void synthesizerIsOpenedAndClosed() throws MidiUnavailableException {
		sendNote(50);
		verify(midi).open();
		verify(midi).close();
	}

	private void sendNote(int note) {
		try (LocalSynthesizer device = new LocalSynthesizer(midi)) {
			device.send(note, 0);
		}
	}
}
