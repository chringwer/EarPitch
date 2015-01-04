package earpitch.util;

import java.io.IOException;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LayoutUtil {
    public static <T> T load(Object controller) {
        String fileName = controller.getClass().getSimpleName();
        return load(fileName + ".fxml", fileName + ".css", controller);
    }

    public static <T> T load(String fxmlFile, String cssFile, Object controller) {
        URL fxml = controller.getClass().getResource(fxmlFile);
        FXMLLoader loader = new FXMLLoader(fxml);
        loader.setController(controller);

        if (controller instanceof Node) {
            loader.setRoot(controller);
        }

        try {
            T node = loader.load();

            URL css = controller.getClass().getResource(cssFile);
            if (css != null) {
                ObservableList<String> stylesheets = FXCollections.emptyObservableList();

                if (node instanceof Scene) {
                    stylesheets = ((Scene) node).getStylesheets();
                } else if (node instanceof Parent) {
                    stylesheets = ((Parent) node).getStylesheets();
                }

                stylesheets.add(css.toExternalForm());
            }

            return node;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
