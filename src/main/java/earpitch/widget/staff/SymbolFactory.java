package earpitch.widget.staff;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

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
        font = loadFont("bravura/otf/Bravura.otf", 60);
    }

    public Text createBarlineEnd() {
        return makeText(BARLINE_END);
    }

    public Text createBarlineStart() {
        return makeText(BARLINE_START);
    }

    public Text createClef() {
        return makeText(CLEF);
    }

    public Text createFlat() {
        Text flat = makeText(ACCIDENTAL_FLAT);
        flat.setTranslateY(-1.5 * getLineHeight());
        return flat;
    }

    public Text createNarrowStaves() {
        return makeText(NARROW_STAVES);
    }

    public Text createNote(boolean isUpperHalf) {
        Text note = makeText(isUpperHalf ? NOTE_DOWN : NOTE_UP);
        double offset = 3 * getLineHeight();
        double origin = note.getTranslateY();
        note.setTranslateY(origin + (isUpperHalf ? offset : -offset));
        return note;
    }

    public Text createSharp() {
        return makeText(ACCIDENTAL_SHARP);
    }

    public Text createSingleStave() {
        return makeText(STAVE);
    }

    public Text createStaves() {
        return makeText(STAVES);
    }

    public Text createStem() {
        return makeText(STEM);
    }

    public double getFontSize() {
        return font.getSize();
    }

    protected Text makeText(String symbol) {
        Text text = new Text(symbol);
        text.setFont(font);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setTranslateY(-font.getSize() * 0.05);
        return text;
    }

    private double getLineHeight() {
        double lineHeight = font.getSize() / 8;
        return lineHeight;
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

    private Text makeText(int codepoint) {
        return makeText(Character.toString((char) codepoint));
    }

    private Object parseFontMetadata() {
        ClassLoader classLoader = SymbolFactory.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("fonts/bravura/bravura_metadata.json");
        return Configuration.defaultConfiguration().jsonProvider().parse(is, "utf8");
    }
}
