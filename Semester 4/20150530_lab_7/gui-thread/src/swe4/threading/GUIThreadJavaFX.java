package swe4.threading;

import swe4.util.Util;
import javafx.application.Application;
import javafx.application.Platform;
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

  private class Worker extends Thread {
    public Worker() {
      super("Worker");
    }

    // version 2
    public void run() {
      for (int i = 1; i <= 100; i++) {
        final double progress = i / 100.0;
        // variant 1: unsafe because JavaFX components are not thread-safe.
        // progressIndicator.setProgress(progress);
        
        // variant 2: updating of label is delegated UI thread.
        Platform.runLater(() -> progressIndicator.setProgress(progress));
        Util.sleep(100);
      }
      // variant 2
      Platform.runLater(() -> startButton.setDisable(false));
      // variant 1
      //startButton.setDisable(false);
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    progressIndicator = new ProgressIndicator(0);
    progressIndicator.setPadding(new Insets(15));
    startButton = new Button("Start");

    startButton.setOnAction(event -> {
      // version 2
      // prevent starting of additional worker threads.
     //   startButton.setDisable(true);
        Worker worker = new Worker();
        worker.setDaemon(true); // daemon thread does not prevent JVM
//                                 from exiting when UI Thread is terminated.
        worker.start();

        // version 1
        // if the work is done here the GUI cannot react to
        // user interaction (e.g. resizing)
//        for (int i = 1; i <= 100; i++) {
//          progressIndicator.setProgress(i / 100.0);
//          Util.sleep(100);
//        }
        startButton.setDisable(false);
      });

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