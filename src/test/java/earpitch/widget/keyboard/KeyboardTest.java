package earpitch.widget.keyboard;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.categories.TestFX;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import earpitch.Pitch;

@Category(TestFX.class)
public class KeyboardTest extends GuiTest {
    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    private EventHandler<NoteEvent> eventhandler;

    @Test
    public void firesNoteEventWhenClicked() throws InterruptedException {
        click("#" + Keyboard.identifierForKey(Pitch.D4));

        ArgumentCaptor<NoteEvent> captor = ArgumentCaptor.forClass(NoteEvent.class);
        verify(eventhandler).handle(captor.capture());

        assertThat(captor.getValue().getPitch(), is(Pitch.D4));
    }

    @Test
    public void flatKeysHaveBlackColor() {
        assertThat(find("#key-" + Pitch.Bb3.toMidiNote()), hasColor(Color.BLACK));
    }

    @Test
    public void wholeKeysHaveWhiteColor() {
        assertThat(find("#key-" + Pitch.C4.toMidiNote()), hasColor(Color.WHITE));
    }

    @Override
    protected Parent getRootNode() {
        Keyboard keyboard = new Keyboard();
        keyboard.addEventHandler(NoteEvent.PLAYED, eventhandler);
        return keyboard;
    }

    private TypeSafeMatcher<Rectangle> hasColor(Color color) {
        return new TypeSafeMatcher<Rectangle>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("should have color").appendValue(color);
            }

            @Override
            protected boolean matchesSafely(Rectangle item) {
                return item.getFill().equals(color);
            }
        };
    }
}
