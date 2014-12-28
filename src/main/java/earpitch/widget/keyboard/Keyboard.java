package earpitch.widget.keyboard;

import java.util.List;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import earpitch.Pitch;
import earpitch.widget.keyboard.PitchMapper.Builder;

public class Keyboard extends Group {
	private final int width = 30;
	private final int height = 100;

	public Keyboard() {
		Builder<Rectangle> mapper = new PitchMapper.Builder<Rectangle>();

		List<Rectangle> keys = mapper.groupBy(Pitch::toMidiNote)
										.register('!', this::black)
										.register('|', this::white)
										.compile("|!||!|!||!|!")
										.map(this::attachHandler);

		getChildren().addAll(keys);
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
