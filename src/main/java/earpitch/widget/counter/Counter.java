package earpitch.widget.counter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.converter.NumberStringConverter;

public class Counter extends Label {
    private IntegerProperty counter;
    private StringProperty color;

    public Counter() {
        setFont(Font.font("Arial Narrow", FontWeight.EXTRA_BOLD, 50));
        setMinHeight(150);
        setMinWidth(150);
        setAlignment(Pos.CENTER);

        setTextFill(Color.WHITE);

        counter = new SimpleIntegerProperty(0);
        color = new SimpleStringProperty("#000000");

        color.addListener((e, oldVal, newVal) -> {
            updateColor(newVal);
        });

        textProperty().bindBidirectional(counter, new NumberStringConverter());
    }

    public StringProperty colorProperty() {
        return color;
    }

    public String getColor() {
        return color.get();
    }

    public int increment() {
        counter.set(counter.get() + 1);
        return counter.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    private void updateColor(String color) {
        Color backgroundColor = Color.web(color);
        setBackground(new Background(new BackgroundFill(backgroundColor, new CornerRadii(20), Insets.EMPTY)));
    }
}
