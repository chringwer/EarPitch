package earpitch.widget.staff;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.loadui.testfx.controls.Commons.hasText;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import earpitch.Pitch;
import earpitch.widget.staff.Staff;
import earpitch.widget.staff.SymbolFactory;

public class StaffTest extends GuiTest {
	@Test
	public void higherNotesHaveHigherYPositions() {
		Set<Node> notes = findNotes();

		List<Bounds> bounds = notes.stream().map((n) -> getBounds(n))
				.sorted((b1, b2) -> -Double.compare(b1.getMinX(), b2.getMinX()))
				.collect(Collectors.toCollection(LinkedList::new));

		double first = bounds.get(0).getMinY();
		double second = bounds.get(1).getMinY();

		assertThat(first, lessThan(second));
	}

	@Test
	public void numberOfNoteheadsEqualsMelody() {
		Set<Node> notes = findNotes();
		assertThat(notes, hasSize(2));
	}

	@Override
	protected Parent getRootNode() {
		Staff staff = new Staff(2);
		staff.addNote(Pitch.C4);
		staff.addNote(Pitch.D4);
		return staff;
	}

	private Set<Node> findNotes() {
		SymbolFactory symbolFactory = new SymbolFactory();
		Label upperNote = symbolFactory.createNote(true);
		Label lowerNote = symbolFactory.createNote(false);

		Set<Node> notes = findAll(anyOf(hasText(upperNote.getText()), hasText(lowerNote.getText())),
				getWindowByIndex(0).getScene().getRoot());
		return notes;
	}

	private Bounds getBounds(Node node) {
		return node.localToScreen(node.getBoundsInParent());
	}
}
