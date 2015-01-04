package earpitch.generator;

import static earpitch.Pitch.C4;
import static earpitch.Pitch.D4;
import static earpitch.Pitch.E4;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.intThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import earpitch.Pitch;
import earpitch.Scale.Pointer;

@RunWith(MockitoJUnitRunner.class)
public class ScaleBasedGeneratorTest {
    private @Mock RandomDataGenerator random;
    private @Mock Pointer pointer;
    private ScaleBasedGenerator generator;

    @Before
    public void before() {
        generator = new ScaleBasedGenerator();
    }

    @Test
    public void invertIntervalIfPointerIsOutOfBounds() {
        when(random.nextInt(anyInt(), anyInt())).thenAnswer(AdditionalAnswers.returnsLastArg());
        when(pointer.moveAndGet(anyInt())).thenReturn(C4).thenThrow(new IndexOutOfBoundsException()).thenReturn(C4);

        generator.generate(pointer, random, 5, true);

        verify(pointer, atLeastOnce()).moveAndGet(intThat(greaterThan(0)));
        verify(pointer, atLeastOnce()).moveAndGet(intThat(lessThan(0)));
    }

    @Test
    public void makeSequenceWithGivenLength() {
        assertThat(generator.generate(TestOptions.withLength(3)).length, is(3));
    }

    @Test
    public void randomIntervalForEachNote() {
        when(random.nextInt(anyInt(), anyInt())).thenAnswer(AdditionalAnswers.returnsLastArg());
        when(pointer.moveAndGet(anyInt())).thenReturn(C4);

        generator.generate(pointer, random, 5, true);

        verify(random, atLeast(4)).nextInt(anyInt(), anyInt());
    }

    @Test
    public void startWithBaseTone() {
        Pitch[] melody = generator.generate(TestOptions.withKey(Pitch.F4));
        assertThat(melody[0], is(Pitch.F4));
    }

    @Test
    public void useScalePointerToGenerateSequence() {
        when(pointer.moveAndGet(anyInt())).thenReturn(C4).thenReturn(D4).thenReturn(E4);

        assertThat(generator.generate(pointer, random, 3, true), contains(C4, D4, E4));
    }
}
