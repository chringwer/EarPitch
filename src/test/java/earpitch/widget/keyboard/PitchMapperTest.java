package earpitch.widget.keyboard;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.function.BiFunction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.categories.TestFX;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import earpitch.Pitch;

@Category(TestFX.class)
public class PitchMapperTest {
    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Mock
    private BiFunction<String, List<Pitch>, String> mapper;

    @Test
    @SuppressWarnings("unchecked")
    public void allowToGroupPitches() {
        new PitchMapper.Builder<String>().register('X', (idx) -> "x")
                                         .groupBy((pitch) -> pitch.prettyPrinted())
                                         .compile("X")
                                         .map(mapper);

        verify(mapper).apply(anyString(), (List<Pitch>) argThat(contains(Pitch.A$3, Pitch.A$4)));
    }

    @Test
    public void mapAccordingToPattern() {
        assertThat(mapToString().subList(0, 5), contains("W", "B", "W", "B", "W"));
    }

    @Test
    public void mapAllPitchEntries() {
        assertThat(mapToString(), hasSize(Pitch.values().length));
    }

    private List<String> mapToString() {
        return new PitchMapper.Builder<String>().register('!', (idx) -> "B")
                                                .register('|', (idx) -> "W")
                                                .compile("|!|!|")
                                                .map((s, p) -> s);
    }
}
