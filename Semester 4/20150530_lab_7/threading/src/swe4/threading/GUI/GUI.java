package swe4.threading.GUI;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class GUI extends Application {

  private Button startButton;
  private ProgressIndicator progressIndicator;

  @Override
  public void start(Stage primaryStage) {
    progressIndicator = new ProgressIndicator(0);
    progressIndicator.setPadding(new Insets(10));
    startButton = new Button("Start");

    startButton.setOnAction(event -> {
      // while work is done here (event thread) the
      // GUI cannot respond to other events e.g. resizing
      startButton.setDisable(true);
      try {
        for (int i = 1; i <= 100; i++) {
          progressIndicator.setProgress(i / 100.0);
          Thread.sleep(100);
        }
      } catch (InterruptedException x) {}
      startButton.setDisable(false);
    });

    GridPane rootPane = new GridPane();
    rootPane.add(progressIndicator, 0, 0);
    rootPane.add(startButton, 0, 1);

    GridPane.setVgrow(progressIndicator, Priority.ALWAYS);
    GridPane.setHgrow(progressIndicator, Priority.ALWAYS);
    GridPane.setHalignment(startButton, HPos.CENTER);
    GridPane.setMargin(startButton, new Insets(0, 10, 10, 0));

    Scene scene = new Scene(rootPane, 200, 200);

    primaryStage.setScene(scene);
    primaryStage.setMinWidth(140);
    primaryStage.setMinHeight(160);
    primaryStage.setTitle("Thread Test");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
