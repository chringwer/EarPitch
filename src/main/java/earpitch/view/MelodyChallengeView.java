package earpitch.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import earpitch.Challenge;
import earpitch.Challenge.Part;
import earpitch.widget.keyboard.Keyboard;
import earpitch.widget.keyboard.NoteEvent;
import earpitch.widget.staff.Staff;

public class MelodyChallengeView extends VBox {
	private ObjectProperty<Challenge> property = new SimpleObjectProperty<>();
	private Staff staff;
	private Keyboard keyboard;

	public MelodyChallengeView() {
		setAlignment(Pos.CENTER);

		staff = new Staff(4);
		keyboard = new Keyboard();

		getChildren().addAll(staff, keyboard);

		keyboard.addEventHandler(NoteEvent.PLAYED, (n) -> staff.addNote(n.getPitch()));

		property.addListener((e, oldValue, newValue) -> {
			refresh(newValue);
		});
	}

	public void setChallenge(Challenge challenge) {
		property.set(challenge);
	}

	private void refresh(Challenge challenge) {
		staff.clear();
		for (Part part : challenge) {
			staff.addNote(part.get());
		}
	}
}
