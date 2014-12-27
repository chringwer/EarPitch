package earpitch.widget.staff;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

public class SymbolFactory {
	static final int STAVE = 0xE010;
	static final int STAVES = 0xE01A;
	static final int STEM = 0xE210;
	static final int NARROW_STAVES = 0xE020;
	static final int NOTE_DOWN = 0xE1D6;
	static final int NOTE_UP = 0xE1D5;
	static final int ACCIDENTAL_SHARP = 0xE262;
	static final int ACCIDENTAL_FLAT = 0xE260;
	static final int BARLINE_START = 0xE034;
	static final int BARLINE_END = 0xE034;
	static final int CLEF = 0xE050;

	private final Font font;
	private final Object metadata;

	public SymbolFactory() {
		metadata = parseFontMetadata();
		font = loadFont("bravura/otf/Bravura.otf", 80);
	}

	public Label createBarlineEnd() {
		return makeLabel(BARLINE_END);
	}

	public Label createBarlineStart() {
		return makeLabel(BARLINE_START);
	}

	public Label createClef() {
		Label clef = makeLabel(CLEF);
		clef.setTranslateY(-font.getSize() / 4);
		return clef;
	}

	public Label createFlat() {
		return makeLabel(ACCIDENTAL_FLAT);
	}

	public Label createNarrowStaves() {
		return makeLabel(NARROW_STAVES);
	}

	public Label createNote(boolean isUpperHalf) {
		return makeLabel(isUpperHalf ? NOTE_DOWN : NOTE_UP);
	}

	public Label createSharp() {
		return makeLabel(ACCIDENTAL_SHARP);
	}

	public Label createSingleStave() {
		Label stave = makeLabel(STAVE);
		stave.setTranslateY(2 * font.getSize() / 4);
		return stave;
	}

	public Label createStaves() {
		return makeLabel(STAVES);
	}

	public Label createStem() {
		return makeLabel(STEM);
	}

	public double getFontSize() {
		return font.getSize();
	}

	protected Label makeLabel(String symbol) {
		Label label = new Label(symbol);
		label.setFont(font);
		return label;
	}

	private Font loadFont(String path, int size) {
		ClassLoader classLoader = SymbolFactory.class.getClassLoader();
		URL url = classLoader.getResource("fonts/" + path);
		return Font.loadFont(url.toExternalForm(), size);
	}

	@SuppressWarnings("unused")
	private String lookup(String name) {
		String jsonPath = "$..glyphs[?(@.name == '" + name + "')].codepoint";
		List<String> matches = JsonPath.read(metadata, jsonPath);

		if (matches.isEmpty()) {
			throw new IllegalArgumentException("font metadata did not contain a glyph named " + name);
		}

		char symbol = (char) Integer.parseInt(matches.get(0).substring(2), 16);
		return Character.toString(symbol);
	}

	private Label makeLabel(int codepoint) {
		return makeLabel(Character.toString((char) codepoint));
	}

	private Object parseFontMetadata() {
		ClassLoader classLoader = SymbolFactory.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream("fonts/bravura/bravura_metadata.json");
		return Configuration.defaultConfiguration().jsonProvider().parse(is, "utf8");
	}
}
