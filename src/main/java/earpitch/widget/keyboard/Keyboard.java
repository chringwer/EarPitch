package earpitch.widget.keyboard;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import earpitch.Pitch;
import earpitch.widget.keyboard.PitchMapper.Builder;

public class Keyboard extends Region {
    private final int width = 30;
    private final int height = 120;

    public Keyboard() {
        Builder<Rectangle> mapper = new PitchMapper.Builder<Rectangle>();

        setMinHeight(height);

        List<Rectangle> keys = mapper.groupBy(Pitch::toMidiNote)
                                     .register('!', this::black)
                                     .register('|', this::white)
                                     .compile("|!||!|!||!|!")
                                     .map(this::attachHandler);

        getChildren().addAll(keys);
    }

    @Override
    protected void layoutChildren() {
        ObservableList<Node> children = getChildren();
        Insets padding = getPadding();
        double total = getWidth() - (padding.getLeft() + padding.getRight());
        double width = total / children.size();

        for (int idx = 0; idx < children.size(); idx++) {
            Rectangle rectangle = (Rectangle) children.get(idx);
            rectangle.setWidth(width);
            rectangle.setTranslateX(padding.getLeft() + idx * width);
        }
    }

    private Rectangle attachHandler(Rectangle key, List<Pitch> pitches) {
        Pitch pitch = pitches.get(0);
        key.setId("key-" + pitch.toMidiNote());
        key.setOnMouseClicked((e) -> fireEvent(new NoteEvent(pitch)));
        return key;
    }

    private Rectangle black(int idx) {
        Rectangle key = key(idx, Color.BLACK);
        key.getStyleClass().add("black");
        return key;
    }

    private Rectangle key(int idx, Color fill) {
        Rectangle key = new Rectangle(width, height);
        key.setTranslateX(idx * width);
        key.setStroke(Color.BLACK);
        key.setFill(fill);
        key.setCursor(Cursor.HAND);
        key.getStyleClass().add("key");
        return key;
    }

    private Rectangle white(int idx) {
        Rectangle key = key(idx, Color.WHITE);
        key.getStyleClass().add("white");
        return key;
    }
}
