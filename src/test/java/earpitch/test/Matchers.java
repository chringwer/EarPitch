package earpitch.test;

import javafx.scene.text.Text;

import org.hamcrest.Matcher;
import org.loadui.testfx.controls.impl.HasLabelStringMatcher;

public class Matchers {
    public static Matcher<Object> hasText(String text) {
        return new HasLabelStringMatcher(text) {
            @Override
            public boolean matchesSafely(Object target) {
                if (target instanceof Text) {
                    return text.equals(((Text) target).getText());
                } else {
                    return super.matchesSafely(target);
                }
            }
        };
    };
}
