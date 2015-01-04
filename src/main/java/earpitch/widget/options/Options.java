package earpitch.widget.options;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import earpitch.Generator;
import earpitch.Pitch;
import earpitch.Scale;
import earpitch.Trainer;
import earpitch.util.LayoutUtil;

public class Options extends GridPane implements Initializable {
    private @FXML ChoiceBox<Pitch> key;
    private @FXML ChoiceBox<Scale> scale;
    private @FXML CheckBox alwaysStartWithBaseTone;
    private ObservableOptions options;

    private ObjectProperty<Trainer> trainer = new SimpleObjectProperty<Trainer>();
    private ObjectProperty<EventHandler<Event>> onUpdate = new SimpleObjectProperty<EventHandler<Event>>() {
        @Override
        protected void invalidated() {
            setEventHandler(ObservableOptions.CHANGED, get());
        }
    };

    public Options() {
        this.options = new ObservableOptions(Generator.DEFAULT_OPTIONS);
        LayoutUtil.load(this);
    }

    public EventHandler<Event> getOnUpdate() {
        return onUpdateProperty().get();
    }

    public Trainer getTrainer() {
        return trainerProperty().get();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        trainer.addListener((e, prev, trainer) -> {
            options.setAll(trainer.getOptions());
            trainer.setOptions(options);
        });

        initScaleChoiceBox();
        initKeyChoiceBox();

        options.dirtyProperty().addListener((e, wasDirty, isDirty) -> {
            if (isDirty) {
                fireEvent(new Event(ObservableOptions.CHANGED));
                options.dirtyProperty().set(false);
            }
        });

        alwaysStartWithBaseTone.selectedProperty().bindBidirectional(options.alwaysStartWithBaseToneProperty());
    }

    public ObjectProperty<EventHandler<Event>> onUpdateProperty() {
        return onUpdate;
    }

    public void setOnUpdate(EventHandler<Event> onUpdate) {
        onUpdateProperty().set(onUpdate);
    }

    public void setOptions(ObservableOptions options) {
        this.options = options;
    }

    public void setTrainer(Trainer trainer) {
        trainerProperty().set(trainer);
    }

    public ObjectProperty<Trainer> trainerProperty() {
        return trainer;
    }

    private void initKeyChoiceBox() {
        List<Pitch> values = IntStream.range(Pitch.C4.ordinal(), Pitch.C5.ordinal())
                                      .boxed()
                                      .map(idx -> Pitch.values()[idx])
                                      .collect(Collectors.toList());

        key.setItems(FXCollections.observableArrayList(values));
        key.setConverter(new StringConverter<Pitch>() {
            @Override
            public Pitch fromString(String label) {
                return Pitch.valueOf(label.replace('#', '$') + "4");
            }

            @Override
            public String toString(Pitch pitch) {
                return pitch.prettyPrinted();
            }
        });
        key.valueProperty().bindBidirectional(options.keyProperty());
    }

    private void initScaleChoiceBox() {
        scale.setItems(FXCollections.observableArrayList(Scale.values()));
        scale.valueProperty().bindBidirectional(options.scaleProperty());
    }
}
