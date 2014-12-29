package earpitch.sound;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class LocalSynthesizer implements Device, AutoCloseable {
	private static Synthesizer acquireSynthesizer() {
		try {
			return MidiSystem.getSynthesizer();
		} catch (MidiUnavailableException e) {
			throw new IllegalStateException(e);
		}
	}

	private Synthesizer midi;

	public LocalSynthesizer() {
		this(acquireSynthesizer());
	}

	public LocalSynthesizer(Synthesizer midi) {
		this.midi = midi;
		open(midi);
	}

	@Override
	public void close() {
		midi.close();
	}

	@Override
	public void send(int midiNote, long durationInMillis) {
		piano().noteOn(midiNote, 128);
		waitFor(durationInMillis);
		piano().noteOff(midiNote);
	}

	private void open(Synthesizer midi) {
		try {
			midi.open();
		} catch (MidiUnavailableException e) {
			throw new IllegalStateException(e);
		}
	}

	private MidiChannel piano() {
		MidiChannel[] channels = midi.getChannels();
		return channels[0];
	}

	private void waitFor(long durationInMillis) {
		try {
			Thread.sleep(durationInMillis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
	}
}
