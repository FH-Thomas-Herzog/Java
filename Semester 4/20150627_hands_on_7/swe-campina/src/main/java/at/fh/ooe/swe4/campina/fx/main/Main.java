package at.fh.ooe.swe4.campina.fx.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import at.fh.ooe.swe4.campina.fx.rmi.service.locator.ServiceLocator;
import at.fh.ooe.swe4.campina.fx.view.scene.MainSceneViewHandler;
import at.fh.ooe.swe4.campina.service.api.LoginEventService;

public class Main extends Application {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		final MainSceneViewHandler def = new MainSceneViewHandler();
		final Scene scene = def.createNode();
		def.initHandler();
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(700);
		primaryStage.setMinHeight(300);
		primaryStage.setTitle("Campina");

		primaryStage.show();
	}

	public static void main(String args[]) throws Throwable {

		final LoginEventService service = ServiceLocator.getService(LoginEventService.class);
		service.save(null);
		service.delete(null);
		// launch(args);
	}

}
