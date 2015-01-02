package earpitch.widget.counter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.categories.TestFX;

import com.google.common.base.Supplier;

@Category(TestFX.class)
public class CounterTest extends GuiTest {
    private Counter counter;

    @Test
    public void startAtZero() {
        assertThat(counter.getText(), is("0"));
    }

    @Test
    public void updateColorWhenSet() {
        click("#colorer");

        Supplier<Paint> primaryBackgroundColor = () -> {
            Background background = counter.getBackground();
            if (background != null) {
                List<BackgroundFill> fills = background.getFills();
                return fills.get(0).getFill();
            } else {
                return null;
            }
        };

        waitUntil(primaryBackgroundColor.get(), is(Color.GREEN), 5);

        assertThat(counter.getColor(), is(toRGBCode(Color.GREEN)));
        assertThat(counter.colorProperty().get(), is(toRGBCode(Color.GREEN)));
        assertThat(primaryBackgroundColor.get(), is(Color.GREEN));
    }

    @Test
    public void updateLabelWhenIncrements() {
        click("#incrementer");
        waitUntil(counter.getText(), is("1"), 5);
        assertThat(counter.getText(), is("1"));
    }

    @Test
    public void zeroAfterReset() {
        click("#incrementer");
        click("#incrementer");
        waitUntil(counter.getText(), is("2"), 5);

        click("#resetter");
        waitUntil(counter.getText(), is("0"), 5);

        assertThat(counter.getText(), is("0"));
    }

    @Override
    protected Parent getRootNode() {
        counter = new Counter();

        Button incrementer = new Button("Increment");
        incrementer.setId("incrementer");
        incrementer.setOnAction(e -> counter.increment());

        Button colorer = new Button("Color");
        colorer.setId("colorer");
        colorer.setOnAction(e -> counter.setColor(toRGBCode(Color.GREEN)));

        Button resetter = new Button("Reset");
        resetter.setId("resetter");
        resetter.setOnAction(e -> counter.reset());

        return new HBox(counter, incrementer, colorer, resetter);
    }

    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                             (int) (color.getRed() * 255),
                             (int) (color.getGreen() * 255),
                             (int) (color.getBlue() * 255));
    }
}
