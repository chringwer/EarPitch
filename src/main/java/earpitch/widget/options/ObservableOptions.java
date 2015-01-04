package earpitch.widget.options;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventType;
import earpitch.Generator.Options;
import earpitch.Pitch;
import earpitch.Scale;

public class ObservableOptions implements Options {
    public static EventType<Event> CHANGED = new EventType<Event>("CHANGED");

    private BooleanProperty dirty;
    private ObjectProperty<Pitch> key;
    private ObjectProperty<Scale> scale;
    private IntegerProperty length;
    private IntegerProperty maxStepSize;
    private BooleanProperty startWithBaseTone;

    public ObservableOptions(Options defaults) {
        dirty = new SimpleBooleanProperty();

        key = new SimpleObjectProperty<Pitch>(defaults.getKey());
        key.addListener((o) -> dirty.set(true));

        scale = new SimpleObjectProperty<Scale>(defaults.getScale());
        scale.addListener((o) -> dirty.set(true));

        length = new SimpleIntegerProperty(defaults.getLength());
        length.addListener((o) -> dirty.set(true));

        maxStepSize = new SimpleIntegerProperty(defaults.getMaxStepSize());
        maxStepSize.addListener((o) -> dirty.set(true));

        startWithBaseTone = new SimpleBooleanProperty(defaults.getAlwaysStartWithBaseTone());
        startWithBaseTone.addListener((o) -> dirty.set(true));
    }

    public BooleanProperty alwaysStartWithBaseToneProperty() {
        return startWithBaseTone;
    }

    public BooleanProperty dirtyProperty() {
        return dirty;
    }

    @Override
    public boolean getAlwaysStartWithBaseTone() {
        return alwaysStartWithBaseToneProperty().get();
    }

    @Override
    public Pitch getKey() {
        return keyProperty().get();
    }

    @Override
    public int getLength() {
        return lengthProperty().get();
    }

    @Override
    public int getMaxStepSize() {
        return maxStepSizeProperty().get();
    }

    @Override
    public Scale getScale() {
        return scaleProperty().get();
    }

    public ObjectProperty<Pitch> keyProperty() {
        return key;
    }

    public IntegerProperty lengthProperty() {
        return length;
    }

    public IntegerProperty maxStepSizeProperty() {
        return maxStepSize;
    }

    public ObjectProperty<Scale> scaleProperty() {
        return scale;
    }

    public void setAll(Options other) {
        if (other == null) {
            return;
        }

        key.set(other.getKey());
        scale.set(other.getScale());
        length.set(other.getLength());
        startWithBaseTone.set(other.getAlwaysStartWithBaseTone());
    }
}
