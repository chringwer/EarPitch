package earpitch.widget.staff;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import earpitch.Pitch;

public class Note extends StackPane {
	private SymbolFactory symbols;
	private Pitch pitch;
	private Slot slot;

	public Note(Pitch note) {
		this(new SymbolFactory(), note);
	}

	public Note(SymbolFactory symbols, Pitch pitch) {
		this.symbols = symbols;
		this.pitch = pitch;
		slot = Slot.at(pitch);

		double translateX = hasAccidentals() ? symbols.getFontSize() * 0.2 : 0;

		addStaves(translateX);
		addNote(translateX);
		addAccidentals(translateX);
		addLabel();
	}

	@Override
	public String toString() {
		return pitch.name();
	}

	private void addAccidentals(double translateX) {
		if (hasAccidentals()) {
			Label accidental = pitch.isSharp() ? symbols.createSharp() : symbols.createFlat();
			accidental.setTranslateX(-translateX);
			slot.moveToSlot(accidental);
			getChildren().add(accidental);
		}
	}

	private void addLabel() {
		Label name = new Label(pitch.prettyPrinted());
		name.setTranslateY(150);
		name.setFont(Font.font(10));
		getChildren().add(name);
	}

	private void addNote(double translateX) {
		Label notehead = symbols.createNote(slot.isUpperHalf());
		notehead.setTranslateX(translateX);
		slot.moveToSlot(notehead);
		getChildren().add(notehead);
	}

	private void addStaves(double translateX) {
		getChildren().add(symbols.createStaves());

		for (Slot each : slot.findSlotsForMissingStaves()) {
			Label stave = symbols.createSingleStave();
			each.moveToSlot(stave);
			stave.setTranslateX(translateX);
			getChildren().add(stave);
		}
	}

	private boolean hasAccidentals() {
		return pitch.isSharp() || pitch.isFlat();
	}
}
