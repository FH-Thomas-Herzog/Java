package swe4.threading;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class GUIThreadJavaFX extends Application {
  
  private Button            startButton;
  private ProgressIndicator progressIndicator;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    progressIndicator = new ProgressIndicator(0);
    progressIndicator.setPadding(new Insets(15));
    startButton = new Button("Start");

    GridPane rootPane = new GridPane();
    rootPane.add(progressIndicator, 0, 0);
    rootPane.add(startButton, 0, 1);

    GridPane.setVgrow(progressIndicator, Priority.ALWAYS);
    GridPane.setHgrow(progressIndicator, Priority.ALWAYS);

    GridPane.setHalignment(startButton, HPos.CENTER);
    GridPane.setMargin(startButton, new Insets(0, 10, 10, 10));

    Scene scene = new Scene(rootPane, 200, 200);

    primaryStage.setScene(scene);
    primaryStage.setMinWidth(140);
    primaryStage.setMinHeight(160);
    primaryStage.setTitle("Thread Test");
    primaryStage.show();
  }
}