package earpitch.widget.staff;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import earpitch.Pitch;

public class SlotTest {
    @Test
    public void findSlotsForMissingStaves() {
        assertThat(Slot.at(Pitch.A3).findSlotsForMissingStaves(), hasSize(2));
        assertThat(Slot.at(Pitch.A3).findSlotsForMissingStaves(), hasItem(Slot.at(Pitch.A3)));
        assertThat(Slot.at(Pitch.C5).findSlotsForMissingStaves(), empty());
        assertThat(Slot.at(Pitch.A5).findSlotsForMissingStaves(), hasSize(1));
        assertThat(Slot.at(Pitch.A5).findSlotsForMissingStaves(), hasItem(Slot.at(Pitch.A5)));
    }

    @Test
    public void flatAndNormalNotesHaveTheSameSlot() {
        assertThat(Slot.at(Pitch.D4), is(Slot.at(Pitch.Db4)));
    }

    @Test
    public void higherNoteHaveHigherSlots() {
        assertThat(Slot.at(Pitch.B3), lessThan(Slot.at(Pitch.C4)));
    }

    @Test
    public void sharpAndNormalNotesHaveTheSameSlot() {
        assertThat(Slot.at(Pitch.C4), is(Slot.at(Pitch.C$4)));
    }

    @Test
    public void slotZeroIsB4() {
        assertThat(Slot.at(Pitch.B4).getIndex(), is(0));
    }
}
