package earpitch.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import earpitch.Challenge;
import earpitch.Challenge.Part;
import earpitch.widget.staff.Staff;

public class MelodyChallengeView extends HBox {
	private ObjectProperty<Challenge> property = new SimpleObjectProperty<>();
	private Staff staff;

	public MelodyChallengeView() {
		setAlignment(Pos.CENTER);

		staff = new Staff(4);
		getChildren().add(staff);

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
