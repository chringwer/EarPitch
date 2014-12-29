package earpitch;

import javafx.application.Application;
import javafx.stage.Stage;
import earpitch.scene.Training;

public class EarPitch extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("EarPitch");

		stage.setScene(Training.createScene());

		stage.setWidth(1024);
		stage.setHeight(800);

		stage.show();
	}
}
