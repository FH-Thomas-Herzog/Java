package at.fh.ooe.swe4.campina.fx.main;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import at.fh.ooe.swe4.campina.fx.view.scene.MainSceneViewHandler;
import at.fh.ooe.swe4.campina.service.api.LoginEventService;
import at.fh.ooe.swe4.campina.service.api.spec.factory.RmiServiceFactory;

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

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		RmiServiceFactory f = (RmiServiceFactory) Naming.lookup("rmi://localhost:50555/RmiServiceFactory");
		for (int i = 0; i < 100000; i++) {
			LoginEventService es = f.createService(LoginEventService.class);
			es.save(null);
			es.delete(null);
		}
		// launch(args);
	}

}
