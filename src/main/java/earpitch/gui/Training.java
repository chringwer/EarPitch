package earpitch.gui;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.util.Duration;
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
    public static Parent createRoot() {
        return createRoot(new Trainer(Option.FIXED_FIRST_TONE), new Speaker());
    }

    public static Parent createRoot(Trainer trainer, Speaker speaker) {
        return LayoutUtil.load(new Training(trainer, speaker));
    }

    private @FXML Staff staff;
    private @FXML Keyboard keyboard;
    private @FXML Button playButton;
    private @FXML Button hintButton;
    private @FXML Button resetButton;
    private @FXML Counter okCounter;
    private @FXML Counter failCounter;
    private @FXML CheckBox fixedFirstToneCheckbox;
    private @FXML Node alert;

    private Speaker speaker;
    private Trainer trainer;
    private Challenge challenge;
    private List<Button> buttons;

    public Training(Trainer trainer, Speaker speaker) {
        this.trainer = trainer;
        this.speaker = speaker;
    }

    @FXML
    public void hint() {
        if (challenge.isSolved()) {
            challenge = trainer.nextChallenge();
        }

        process(challenge.getExpected());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fixedFirstToneCheckbox.selectedProperty().addListener((e, oldVal, newVal) -> {
            trainer.set(Option.FIXED_FIRST_TONE, newVal);
            reset();
        });

        keyboard.addEventHandler(NoteEvent.PLAYED, note -> {
            CompletableFuture.runAsync(() -> speaker.play(note.getPitch()));
            process(note.getPitch());
        });

        buttons = Arrays.asList(new Button[] { playButton, hintButton, resetButton });

        reset();
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
            okCounter.increment();
            staff.addNote(pitch);
        } else {
            failCounter.increment();
        }

        if (challenge.isSolved()) {
            buttons.forEach(button -> button.setDisable(true));
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep((long) Duration.seconds(1).toMillis());
                    Platform.runLater(() -> alert.setVisible(true));
                } catch (Exception e) {
                    return;
                }
            });
        }
    }

    @FXML
    public void reset() {
        challenge = trainer.nextChallenge();
        buttons.forEach(button -> button.setDisable(false));
        alert.setVisible(false);
        okCounter.reset();
        failCounter.reset();
        staff.clear();
        play();
    }
}
