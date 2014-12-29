package earpitch.sound;

public interface Device {
	void send(int midiNote, long durationInMillis);
}
