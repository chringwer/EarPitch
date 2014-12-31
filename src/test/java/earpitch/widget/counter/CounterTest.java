package earpitch.widget.counter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

public class CounterTest extends GuiTest {
    private Counter counter;

    @Test
    public void startAtZero() {
        assertThat(counter.getText(), is("0"));
    }

    @Test
    public void updateLabelWhenIncrements() {
        click("#incrementer");
        waitUntil(counter.getText(), is("1"), 5);
        assertThat(counter.getText(), is("1"));
    }

    @Override
    protected Parent getRootNode() {
        Button button = new Button();
        button.setId("incrementer");
        counter = new Counter();

        button.setOnAction(e -> counter.increment());

        return new HBox(counter, button);
    }
}
