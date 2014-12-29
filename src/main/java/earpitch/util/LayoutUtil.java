package earpitch.util;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

public class LayoutUtil {
	public static <T> T load(Object controller) {
		return load(controller.getClass().getSimpleName() + ".fxml", controller);
	}

	public static <T> T load(String fileName, Object controller) {
		URL fxml = controller.getClass().getResource(fileName);
		FXMLLoader loader = new FXMLLoader(fxml);
		loader.setController(controller);

		try {
			return loader.load();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
