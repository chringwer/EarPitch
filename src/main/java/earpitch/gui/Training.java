package earpitch.gui;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.util.Duration;
import earpitch.Challenge;
import earpitch.Pitch;
import earpitch.Trainer;
import earpitch.sound.Speaker;
import earpitch.util.LayoutUtil;
import earpitch.widget.counter.Counter;
import earpitch.widget.keyboard.Keyboard;
import earpitch.widget.keyboard.NoteEvent;
import earpitch.widget.staff.Staff;

public class Training implements Initializable {
    public static Parent createRoot() {
        return createRoot(new Trainer(), new Speaker());
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
    private @FXML Node alert;

    private Speaker speaker;
    private ObjectProperty<Trainer> trainer;
    private Challenge challenge;
    private List<Button> buttons;

    public Training(Trainer trainer, Speaker speaker) {
        this.trainer = new SimpleObjectProperty<Trainer>(trainer);
        this.speaker = speaker;
    }

    public Trainer getTrainer() {
        return trainer.get();
    }

    @FXML
    public void hint() {
        if (challenge.isSolved()) {
            challenge = getTrainer().nextChallenge();
        }

        process(challenge.getExpected());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        keyboard.addEventHandler(NoteEvent.PLAYED, note -> {
            CompletableFuture.runAsync(() -> speaker.play(note.getPitch()));
            process(note.getPitch());
        });

        buttons = Arrays.asList(new Button[] { playButton, hintButton, resetButton });

        reset();
    }

    @FXML
    public void nextChallenge() {
        reset();
        play();
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
            staff.addNote(getTrainer().adjustToScale(pitch));
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
        challenge = getTrainer().nextChallenge();
        buttons.forEach(button -> button.setDisable(false));
        alert.setVisible(false);
        okCounter.reset();
        failCounter.reset();
        staff.clear();
    }
}
