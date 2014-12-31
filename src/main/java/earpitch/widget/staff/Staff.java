package earpitch.widget.staff;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import earpitch.Pitch;

public class Staff extends HBox {
    private final SymbolFactory symbolFactory;
    private ObservableList<Node> container;
    private int cursor = 0;
    private int size;

    public Staff() {
        this(4);
    }

    public Staff(int size) {
        this(new SymbolFactory(), size);
    }

    public Staff(SymbolFactory symbolFactory, int size) {
        this.symbolFactory = symbolFactory;
        this.size = size;

        setMinHeight(symbolFactory.getFontSize() * 2.5);
        setAlignment(Pos.TOP_CENTER);

        HBox noteContainer = createNoteContainer(size);
        container = noteContainer.getChildren();

        getChildren().addAll(createStartSymbol(), noteContainer, createEndSymbol());
    }

    public void addNote(Pitch pitch) {
        insert(new Note(symbolFactory, pitch));
    }

    public void clear() {
        cursor = 0;
        for (int i = 0; i < container.size(); i++) {
            insert(createPlaceholder());
        }
        cursor = 0;
    }

    private Node createEndSymbol() {
        return new StackPane() {
            {
                setAlignment(Pos.CENTER_RIGHT);
                getChildren().addAll(symbolFactory.createNarrowStaves(), symbolFactory.createBarlineEnd());
            }
        };
    }

    private HBox createNoteContainer(int size) {
        return new HBox() {
            {
                for (int i = 0; i < size; i++) {
                    getChildren().add(createPlaceholder());
                }
            }
        };
    }

    private StackPane createPlaceholder() {
        return new StackPane(symbolFactory.createStaves());
    }

    private Node createStartSymbol() {
        return new HBox() {
            {
                setSpacing(0);
                setAlignment(Pos.CENTER);

                StackPane start = new StackPane(symbolFactory.createNarrowStaves(), symbolFactory.createBarlineStart());
                start.setAlignment(Pos.CENTER_LEFT);

                StackPane clef = new StackPane(symbolFactory.createStaves(), symbolFactory.createClef());
                clef.setAlignment(Pos.CENTER_LEFT);

                getChildren().addAll(start, clef, symbolFactory.createNarrowStaves());
            }
        };
    }

    private void insert(Node element) {
        if (cursor >= size) {
            clear();
        }

        container.remove(cursor);
        container.add(cursor, element);
        cursor++;
    }
}
