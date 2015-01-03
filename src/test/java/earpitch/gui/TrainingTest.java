package earpitch.gui;

import static earpitch.test.Matchers.hasText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.loadui.testfx.Assertions.assertNodeExists;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javafx.scene.Node;
import javafx.scene.Parent;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.categories.TestFX;
import org.loadui.testfx.utils.TestUtils;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import earpitch.Challenge;
import earpitch.Pitch;
import earpitch.Trainer;
import earpitch.sound.Speaker;
import earpitch.widget.counter.Counter;
import earpitch.widget.keyboard.Keyboard;
import earpitch.widget.staff.Note;

@RunWith(MockitoJUnitRunner.class)
@Category(TestFX.class)
public class TrainingTest extends GuiTest {
    private @Mock Speaker speaker;
    private @Mock Trainer trainer;
    private @Mock Challenge challenge;

    @Test
    public void countersMatchNumberOfCorrectAndIncorrectGuesses() {
        Counter okCounter = find("#okCounter");
        Counter failCounter = find("#failCounter");

        when(challenge.advanceIfMatches(Pitch.C4)).thenReturn(true);
        when(challenge.advanceIfMatches(Pitch.D4)).thenReturn(false);

        click("#" + Keyboard.identifierForKey(Pitch.C4));
        assertThat(okCounter.getText(), is("1"));
        assertThat(failCounter.getText(), is("0"));

        click("#" + Keyboard.identifierForKey(Pitch.D4));
        assertThat(okCounter.getText(), is("1"));
        assertThat(failCounter.getText(), is("1"));
    }

    @Test
    public void displayNoteWhenHintIsClicked() {
        when(challenge.getExpected()).thenReturn(Pitch.D4);
        when(challenge.advanceIfMatches(Pitch.D4)).thenReturn(true);

        click("#hintButton");

        assertNodeExists(instanceOf(Note.class));
        verify(challenge).getExpected();
    }

    @Test
    public void playToneWhenKeyIsClicked() {
        click("#" + Keyboard.identifierForKey(Pitch.D4));
        verify(speaker).play(Pitch.D4);
    }

    @Test
    public void showAlertAfterChallengeIsSolved() {
        when(challenge.advanceIfMatches(Pitch.A4)).thenReturn(true);
        when(challenge.isSolved()).thenReturn(true);

        click("#" + Keyboard.identifierForKey(Pitch.A4));

        TestUtils.awaitCondition(() -> {
            try {
                Node alert = find("#alert-background");
                return alert.isVisible();
            } catch (Exception e) {
                return false;
            }
        });

        assertNodeExists(hasText("Next Challenge?"));
    }

    @Test
    public void whenPlayIsClickedSendChallengeToSpeaker() {
        reset(challenge);
        click("#playButton");
        verify(challenge).outputTo(speaker);
    }

    @Override
    protected Parent getRootNode() {
        when(trainer.nextChallenge()).thenReturn(challenge);
        when(trainer.adjustToScale(any(Pitch.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        return Training.createRoot(trainer, speaker);
    }
}
