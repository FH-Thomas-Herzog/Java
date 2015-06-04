
package swe4.gui;

import java.util.logging.LogManager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ScribbleFxMain extends Application {

	private Button					leftButton, rightButton, upButton, downButton;
	private ColorPicker				colorPicker;
	private ScribbleCanvas			canvas;
	private ListView<String>		messageList;
	private ObservableList<String>	messages	= FXCollections.observableArrayList();

	private class ButtonEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			ScribbleFxMain.this.handleButtonEvent(event);
		}
	}

	private class KeyEventHandler implements EventHandler<KeyEvent> {
		@Override
		public void handle(KeyEvent event) {
			ScribbleFxMain.this.handleKeyEvent(event);
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			// create components
			Pane controlPane = createControlPane();
			messageList = createMessageList();
			canvas = createCanvas();
			colorPicker.setValue(Color.AQUA); // adds canvas current color for
												// start

			HBox topPane = new HBox(controlPane, messageList);
			topPane.setId("top-pane");

			canvas = createCanvas();
			VBox rootPane = new VBox(createMenuBar(primaryStage), topPane, canvas);
			rootPane.setId("root-pane");
			rootPane.setOnKeyPressed(new KeyEventHandler());

			Scene scene = new Scene(rootPane, 500, 500);
			scene.getStylesheets()
					.add(getClass().getResource("css/scribble-fx.css")
									.toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setMinWidth(400);
			primaryStage.setMinHeight(400);
			primaryStage.setTitle("ScribbleF");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleKeyEvent(KeyEvent event) {
		appendMessage("Key" + event.getCode() + "pressed");
		switch (event.getCode()) {
		case UP:
			canvas.addLineSegment(Direction.UP);
			event.consume();
			break;
		case DOWN:
			canvas.addLineSegment(Direction.DOWN);
			event.consume();
			break;
		case LEFT:
			canvas.addLineSegment(Direction.LEFT);
			event.consume();
			break;
		case RIGHT:
			canvas.addLineSegment(Direction.RIGHT);
			event.consume();
			break;
		default:
			break;
		}
	}

	private void appendMessage(String string) {
		messages.add(string);
		messageList.scrollTo(messages.size());
	}

	public void handleButtonEvent(ActionEvent event) {
		if (event.getTarget() instanceof Button) {
			final Button button = ((Button) event.getTarget());
			appendMessage("Button: " + button.getId() + " pressed");
			if (button == upButton) {
				canvas.addLineSegment(Direction.UP);
			} else if (button == downButton) {
				canvas.addLineSegment(Direction.DOWN);
			} else if (button == leftButton) {
				canvas.addLineSegment(Direction.LEFT);
			} else if (button == rightButton) {
				canvas.addLineSegment(Direction.RIGHT);
			}
		}
	}

	private ScribbleCanvas createCanvas() {
		ScribbleCanvas canvas = new ScribbleCanvas();
		canvas.setId("canvas");
		VBox.setVgrow(canvas, Priority.ALWAYS);
		VBox.setMargin(canvas, new Insets(0, 10, 10, 10));

		canvas.setOnMouseClicked(h ->
			{
				Point2D p = new Point2D(h.getX(), h.getY());
				appendMessage("Mouse clicked at: " + p + ".");
				canvas.addLineSegment(p);
			});
		return canvas;
	}

	private MenuBar createMenuBar(final Stage stage) {
		final PreferencesDialog dialog = new PreferencesDialog(stage);
		MenuItem item1 = new MenuItem("Preferences...");
		// event handling for menu entries
		item1.setOnAction(event -> dialog.show());

		Menu settingsMenu = new Menu("Settings");
		settingsMenu.getItems()
					.add(item1);

		MenuBar bar = new MenuBar();
		bar.getMenus()
			.add(settingsMenu);

		return bar;
	}

	/**
	 * 
	 * @return
	 */
	private ListView<String> createMessageList() {
		ListView<String> messageList = new ListView<>();
		messageList.setItems(messages);
		messageList.setId("message-list");

		// Register message list
		HBox.setHgrow(messageList, Priority.ALWAYS);
		return messageList;
	}

	private Pane createControlPane() {
		leftButton = createIconButton("left-button", "css/button-left.png");
		rightButton = createIconButton("right-button", "css/button-right.png");
		upButton = createIconButton("up-button", "css/button-up.png");
		downButton = createIconButton("down-button", "css/button-down.png");

		GridPane buttonPane = new GridPane();
		buttonPane.setId("button-pane");
		buttonPane.add(leftButton, 0, 1);
		buttonPane.add(upButton, 1, 0);
		buttonPane.add(rightButton, 2, 1);
		buttonPane.add(downButton, 1, 2);

		// add event handling goes here
		// Variant 1, registered on each button
		// EventHandler<ActionEvent> handler = new ButtonEventHandler();
		// leftButton.addEventHandler(ActionEvent.ACTION, handler);
		// rightButton.addEventHandler(ActionEvent.ACTION, handler);
		// upButton.addEventHandler(ActionEvent.ACTION, handler);
		// downButton.addEventHandler(ActionEvent.ACTION, handler);

		// Variant 2
		// buttonPane.addEventHandler(ActionEvent.ACTION, new
		// ButtonEventHandler());

		// Variant 3, inner class
		// buttonPane.addEventHandler(ActionEvent.ACTION,
		// new EventHandler<ActionEvent>() {
		// public void handle(ActionEvent event) {
		// ScribbleFxMain.this.handleButtonEvent(event);
		// };
		// }
		// );

		// Variant 4, lambda
		buttonPane.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> ScribbleFxMain.this.handleButtonEvent(event));

		Label colorLabel = new Label("Color ...");
		colorPicker = new ColorPicker();
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				appendMessage("color: " + colorPicker.getValue() + " selected");
				canvas.setLineColor(colorPicker.getValue());
			};
		});

		HBox colorPane = new HBox(colorLabel, colorPicker);
		colorPane.setId("color-pane");

		VBox controlPane = new VBox();
		controlPane.setId("control-pane");
		controlPane.getChildren()
					.addAll(buttonPane, colorPane);

		return buttonPane;
	}

	private Button createIconButton(String id, String icon) {
		Button button = new Button();
		button.setId(id);

		// Image image = new Image(getClass().getResourceAsStream("icon"));
		// button.setGraphic(new ImageView(image));

		return button;
	}

	public static void main(String[] args) {
		LogManager.getLogManager()
					.reset();
		launch(args);
	}

}
