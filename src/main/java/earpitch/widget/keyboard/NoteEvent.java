package earpitch.widget.keyboard;

import javafx.event.Event;
import javafx.event.EventType;
import earpitch.Pitch;

@SuppressWarnings("serial")
public class NoteEvent extends Event {
	public static final EventType<NoteEvent> PLAYED = new EventType<NoteEvent>("PLAYED");
	private Pitch pitch;

	public NoteEvent(Pitch pitch) {
		super(PLAYED);
		this.pitch = pitch;
	}

	public Pitch getPitch() {
		return pitch;
	}
}
