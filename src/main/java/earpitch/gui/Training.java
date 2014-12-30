package earpitch.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import earpitch.Challenge;
import earpitch.Pitch;
import earpitch.Trainer;
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
    private Speaker speaker;
    private Trainer trainer;
    private Challenge challenge;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trainer = new Trainer();
        speaker = new Speaker();
        challenge = trainer.nextChallenge();

        keyboard.addEventHandler(NoteEvent.PLAYED, note -> {
            CompletableFuture.runAsync(() -> speaker.play(note.getPitch()));
            process(note.getPitch());
        });
    }

    @FXML
    public void play() {
        playButton.setDisable(true);
        CompletableFuture.runAsync(() -> challenge.outputTo(speaker))
                         .thenRun(() -> Platform.runLater(() -> playButton.setDisable(false)));
    }

    public void process(Pitch pitch) {
        boolean matched = challenge.advanceIfMatches(pitch);

        if (matched) {
            staff.addNote(pitch);
        }

        if (challenge.isSolved()) {
            challenge = trainer.nextChallenge();
            staff.clear();
        }
    }
}
