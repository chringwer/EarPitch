package earpitch.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import earpitch.Challenge;
import earpitch.Pitch;
import earpitch.Trainer;
import earpitch.Trainer.Option;
import earpitch.sound.Speaker;
import earpitch.util.LayoutUtil;
import earpitch.widget.counter.Counter;
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
    private @FXML Button hintButton;
    private @FXML Button resetButton;
    private @FXML Counter okCounter;
    private @FXML Counter failCounter;
    private @FXML CheckBox fixedFirstToneCheckbox;

    private Speaker speaker;
    private Trainer trainer;
    private Challenge challenge;

    @FXML
    public void hint() {
        if (challenge.isSolved()) {
            challenge = trainer.nextChallenge();
        }

        process(challenge.getExpected());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trainer = new Trainer(Option.FIXED_FIRST_TONE);
        speaker = new Speaker();

        fixedFirstToneCheckbox.selectedProperty().addListener((e, oldVal, newVal) -> {
            trainer.set(Option.FIXED_FIRST_TONE, newVal);
            reset();
        });

        keyboard.addEventHandler(NoteEvent.PLAYED, note -> {
            CompletableFuture.runAsync(() -> speaker.play(note.getPitch()));
            process(note.getPitch());
        });

        reset();
    }

    @FXML
    public void play() {
        playButton.setDisable(true);
        CompletableFuture.runAsync(() -> challenge.outputTo(speaker))
                         .thenRun(() -> Platform.runLater(() -> playButton.setDisable(false)));
    }

    public void process(Pitch pitch) {
        if (challenge.isSolved()) {
            challenge = trainer.nextChallenge();
        }

        boolean matched = challenge.advanceIfMatches(pitch);

        if (matched) {
            okCounter.increment();
            staff.addNote(pitch);
        } else {
            failCounter.increment();
        }
    }

    @FXML
    public void reset() {
        challenge = trainer.nextChallenge();
        okCounter.reset();
        failCounter.reset();
        staff.clear();
    }
}
