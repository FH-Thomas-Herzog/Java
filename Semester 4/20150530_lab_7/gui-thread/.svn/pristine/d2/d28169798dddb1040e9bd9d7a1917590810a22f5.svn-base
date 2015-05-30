package swe4.threading;

import swe4.util.Util;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class GUIThreadWithJavaFXTask extends Application {
  
  private Button            startButton;
  private ProgressIndicator progressIndicator;

  private class Worker extends Task<Void> {

    @Override
    public Void call() {
      for (int i = 1; i <= 100; i++) {
        if (isCancelled())
          break;
        
        updateProgress(i, 100.0);
        Util.sleep(100);
      }
      
      return null;
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
      Worker worker = new Worker();
      Thread workerThread = new Thread(worker);
      workerThread.setDaemon(true); // daemon thread does not prevent JVM from
                                    // exiting when UI Thread is terminated.
      worker.setOnSucceeded(e -> {
        startButton.setDisable(false);
      });
      workerThread.start();
      
      progressIndicator.progressProperty().bind(worker.progressProperty());
      startButton.setDisable(true);
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