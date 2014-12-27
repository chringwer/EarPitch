package earpitch;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import earpitch.view.MelodyChallengeView;

public class EarPitch extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		RandomMelodyLesson lesson = new RandomMelodyLesson(4);

		MelodyChallengeView view = new MelodyChallengeView();
		view.setChallenge(lesson.nextChallenge());

		Button button = new Button("Refresh");
		button.setOnAction((e) -> view.setChallenge(lesson.nextChallenge()));

		BorderPane pane = new BorderPane();
		pane.setCenter(view);
		pane.setBottom(button);

		Scene scene = new Scene(pane);
		stage.setScene(scene);

		stage.setWidth(800);
		stage.setHeight(600);

		stage.show();
	}
}
