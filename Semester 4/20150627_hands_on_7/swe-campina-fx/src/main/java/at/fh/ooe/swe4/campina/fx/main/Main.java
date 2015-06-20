package at.fh.ooe.swe4.campina.fx.main;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import at.fh.ooe.swe4.campina.dao.api.UserDao;
import at.fh.ooe.swe4.campina.fx.view.scene.MainSceneViewHandler;
import at.fh.ooe.swe4.campina.persistence.api.User;
import at.fh.ooe.swe4.campina.rmi.api.factory.RmiServiceFactory;

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

//		if (System.getSecurityManager() == null) {
//			System.setSecurityManager(new RMISecurityManager());
//		}
//		RmiServiceFactory f = (RmiServiceFactory) Naming.lookup("rmi://localhost:50555/RmiServiceFactory");
//		UserDao es = f.createService(UserDao.class);
//
//		final User user = new User();
//		user.setId(9);
//		user.setFirstName("markus");
//		user.setLastName("maierhofer");
//		user.setUsername("marki");
//		user.setEmail("marki@t.at");
//		user.setPassword("xxxxxxx");
//		user.setAdminFlag(Boolean.TRUE);
//		user.setBlockedFlag(Boolean.FALSE);
//		es.save(user);
		
		 launch(args);
	}

}
