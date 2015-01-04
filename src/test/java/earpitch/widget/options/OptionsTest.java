package earpitch.widget.options;

import static earpitch.Pitch.C4;
import static earpitch.Pitch.C5;
import static earpitch.test.Matchers.hasText;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.categories.TestFX;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import earpitch.Pitch;
import earpitch.Scale;
import earpitch.Trainer;
import earpitch.generator.TestOptions;

@Category(TestFX.class)
@RunWith(MockitoJUnitRunner.class)
public class OptionsTest extends GuiTest {
    private @Mock EventHandler<Event> onUpdateCallback;
    private @Mock Trainer trainer;
    private Options options;

    @Test
    public void baseToneChoiceBoxCoversOneOctave() {
        ChoiceBox<Pitch> choiceBox = find("#key");

        List<Integer> valuesAsMidiNotes = choiceBox.getItems()
                                                   .stream()
                                                   .map(Pitch::toMidiNote)
                                                   .collect(Collectors.toList());

        assertThat(valuesAsMidiNotes, not(empty()));

        assertThat(valuesAsMidiNotes,
                   everyItem(both(greaterThanOrEqualTo(C4.toMidiNote())).and(lessThan(C5.toMidiNote()))));
    }

    @Test
    public void convertLabelToPitch() {
        ChoiceBox<Pitch> choiceBox = find("#key");
        click(choiceBox);

        Node choiceItem = find(hasText("C#"));
        click(choiceItem);

        assertThat(choiceBox.getValue(), is(Pitch.C$4));
    }

    @Test
    public void fireOnUpdateCallbackWhenOptionHasChanged() {
        CheckBox checkBox = find("#alwaysStartWithBaseTone");

        click(checkBox);

        verify(onUpdateCallback).handle(any(Event.class));
    }

    @Test
    public void initializeOptionsWithValuesObtainedFromTrainer() {
        when(trainer.getOptions()).thenReturn(TestOptions.withKey(Pitch.Bb3));

        ArgumentCaptor<ObservableOptions> captor = ArgumentCaptor.forClass(ObservableOptions.class);
        options.setTrainer(trainer);

        verify(trainer).setOptions(captor.capture());

        assertThat(captor.getValue().getKey(), is(Pitch.Bb3));
    }

    @Test
    public void scaleChoiceBoxContainsAllScales() {
        ChoiceBox<Pitch> choiceBox = find("#scale");
        assertThat(choiceBox.getItems(), hasSize(Scale.values().length));
    }

    @Override
    protected Parent getRootNode() {
        options = new Options();
        options.setOnUpdate(onUpdateCallback);
        return options;
    }
}
