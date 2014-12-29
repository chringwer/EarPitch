package earpitch.scene;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import earpitch.Challenge;
import earpitch.RandomMelodyLesson;
import earpitch.sound.Speaker;
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
    private @FXML Button playButton;
    private Challenge challenge;
    private Speaker speaker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        challenge = new RandomMelodyLesson(4).nextChallenge();
        speaker = new Speaker();

        for (Challenge.Part part : challenge) {
            staff.addNote(part.get());
        }

        keyboard.addEventHandler(NoteEvent.PLAYED, note -> staff.addNote(note.getPitch()));
    }

    @FXML
    public void play() {
        playButton.setDisable(true);
        CompletableFuture.runAsync(() -> speaker.play(challenge.getPitches()))
                         .thenRun(() -> Platform.runLater(() -> playButton.setDisable(false)));
    }
}
