package earpitch.scene;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import earpitch.Challenge.Part;
import earpitch.MelodyChallenge;
import earpitch.RandomMelodyLesson;
import earpitch.util.LayoutUtil;
import earpitch.widget.keyboard.Keyboard;
import earpitch.widget.keyboard.NoteEvent;
import earpitch.widget.staff.Staff;

public class Training implements Initializable {
	public static Scene createScene() {
		return LayoutUtil.load(new Training());
	}

	private @FXML Staff staff;
	private @FXML Keyboard keyboard;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MelodyChallenge challenge = new RandomMelodyLesson(4).nextChallenge();
		Part part = challenge.next();
		staff.addNote(part.get());
		keyboard.addEventHandler(NoteEvent.PLAYED, note -> staff.addNote(note.getPitch()));
	}
}
