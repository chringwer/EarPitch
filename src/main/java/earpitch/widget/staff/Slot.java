package earpitch.widget.staff;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Label;

import com.google.common.collect.Maps;

import earpitch.Pitch;

public class Slot implements Comparable<Slot> {
	public static Slot at(Pitch note) {
		return mapping.get(note);
	}

	private static Map<Pitch, Slot> createSlotMap() {
		Map<Pitch, Integer> result = new HashMap<>();
		Pitch[] notes = Pitch.values();

		int slotCnt = 0;
		char previous = notes[0].name().charAt(0);

		for (Pitch note : notes) {
			char current = note.name().charAt(0);
			if (current != previous) {
				slotCnt++;
			}
			result.put(note, slotCnt);
			previous = current;
		}

		Integer baseline = result.get(Pitch.E4);
		return Maps.transformValues(result, (idx) -> new Slot(idx - baseline));
	}

	private static final int LOWER = 0;
	private static final int UPPER = 8;
	private static final int MIDDLE = 4;

	private static Map<Pitch, Slot> mapping = createSlotMap();
	private int index;

	private Slot(int index) {
		this.index = index;
	}

	@Override
	public int compareTo(Slot other) {
		return Integer.compare(index, other.index);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Slot other = (Slot) obj;
		if (index != other.index)
			return false;
		return true;
	}

	public Collection<Slot> findSlotsForMissingStaves() {
		List<Slot> result = new LinkedList<>();
		int current = index;

		while (current >= UPPER + 2 || current <= LOWER - 2) {
			if (current % 2 == 0) {
				result.add(new Slot(current));
			}

			current += isUpperHalf() ? -1 : 1;
		}

		return result;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		return result;
	}

	public boolean isUpperHalf() {
		return index > MIDDLE;
	}

	public void moveToSlot(Label node) {
		double lineHeight = getLineHeight(node);

		double origin = node.getTranslateY();
		node.setTranslateY(origin + -index * lineHeight);
	}

	@Override
	public String toString() {
		return "Slot [" + index + "]";
	}

	private double getLineHeight(Label node) {
		double fontSize = node.getFont().getSize();
		double lineHeight = fontSize / 8;
		return lineHeight;
	}
}
