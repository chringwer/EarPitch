package earpitch.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class EarPitch extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("EarPitch");

        stage.setScene(Training.createScene());

        stage.setWidth(800);
        stage.setHeight(600);

        stage.show();
    }
}
