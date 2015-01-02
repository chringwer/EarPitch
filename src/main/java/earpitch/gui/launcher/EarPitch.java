package earpitch.gui.launcher;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import earpitch.gui.Training;

public class EarPitch extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("EarPitch");

        stage.setScene(new Scene(Training.createRoot()));

        stage.setWidth(800);
        stage.setHeight(600);

        stage.show();
    }
}
