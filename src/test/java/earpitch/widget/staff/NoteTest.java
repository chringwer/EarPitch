package earpitch.widget.staff;

import static earpitch.test.Matchers.hasText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.categories.TestFX;
import org.loadui.testfx.exceptions.NoNodesFoundException;

import earpitch.Pitch;

@Category(TestFX.class)
public class NoteTest extends GuiTest {
    private SymbolFactory symbolFactory = new SymbolFactory();

    private Note sharpNote;
    private Note flatNote;
    private Note highNote;
    private Note lowNote;

    @Test
    public void flatNotesHaveAccidentals() {
        Text flatSymbol = symbolFactory.createFlat();
        assertThat(flatNote, contains(flatSymbol));
        assertThat(sharpNote, not(contains(flatSymbol)));
    }

    @Test
    public void highAndLowNotesHaveExtraStaves() {
        Text staveSymbol = symbolFactory.createSingleStave();
        assertThat(highNote, contains(staveSymbol));
        assertThat(lowNote, contains(staveSymbol));
        assertThat(sharpNote, not(contains(staveSymbol)));
        assertThat(flatNote, not(contains(staveSymbol)));
    }

    @Test
    public void sharpNotesHaveAccidentals() {
        Text sharpSymbol = symbolFactory.createSharp();
        assertThat(sharpNote, contains(sharpSymbol));
        assertThat(flatNote, not(contains(sharpSymbol)));
    }

    @Override
    protected Parent getRootNode() {
        sharpNote = new Note(Pitch.C$5);
        flatNote = new Note(Pitch.Bb4);
        highNote = new Note(Pitch.A5);
        lowNote = new Note(Pitch.A3);

        return new HBox(sharpNote, flatNote, lowNote, highNote);
    }

    private Matcher<Note> contains(Text label) {
        return new TypeSafeMatcher<Note>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("should contain").appendValue(label.getText());
            }

            @Override
            protected boolean matchesSafely(Note note) {
                try {
                    return !findAll(hasText(label.getText()), note).isEmpty();
                } catch (NoNodesFoundException e) {
                    return false;
                }
            }
        };
    }
}
