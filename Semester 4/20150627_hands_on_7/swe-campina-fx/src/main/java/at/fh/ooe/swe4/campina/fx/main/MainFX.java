package at.fh.ooe.swe4.campina.fx.main;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import at.fh.ooe.swe4.campina.dao.api.MenuDao;
import at.fh.ooe.swe4.campina.dao.api.MenuEntryDao;
import at.fh.ooe.swe4.campina.dao.api.OrderDao;
import at.fh.ooe.swe4.campina.dao.api.UserDao;
import at.fh.ooe.swe4.campina.fx.rmi.service.locator.DaoLocator;
import at.fh.ooe.swe4.campina.fx.view.scene.MainSceneViewHandler;
import at.fh.ooe.swe4.campina.persistence.api.entity.Menu;
import at.fh.ooe.swe4.campina.persistence.api.entity.MenuEntry;
import at.fh.ooe.swe4.campina.persistence.api.entity.Order;
import at.fh.ooe.swe4.campina.persistence.api.entity.User;
import at.fh.ooe.swe4.campina.persistence.api.entity.constants.Day;

/**
 * Main class which starts the JavaFX application.<br>
 * It creates test data if the user who is tried to be create does not exists.
 * Otherwise it is assumed that the test data already exist.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 21, 2015
 */
public class MainFX extends Application {

	/**
	 * 
	 */
	public MainFX() {
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

	/**
	 * Prepares the test data for the application.
	 */
	private static void prepareData() {
		User user = new User();
		user.setFirstName("THomas");
		user.setLastName("Herzog");
		user.setAdminFlag(Boolean.TRUE);
		user.setBlockedFlag(Boolean.FALSE);
		user.setEmail("thomas.herzog@students.fh-hangeberg.at");
		user.setPassword("x");
		user.setUsername("cchet");
		try {
			user = DaoLocator.getDao(UserDao.class)
								.save(user);

			Menu menu = new Menu();
			menu.setDay(Day.FRIDAY);
			menu.setLabel("Freitagsmenu");
			menu = DaoLocator.getDao(MenuDao.class)
								.save(menu);

			MenuEntry menuEntry = new MenuEntry();
			menuEntry.setLabel("Fisch");
			menuEntry.setMenu(menu);
			menuEntry.setOrdinal(1);
			menuEntry.setPrice(BigDecimal.ONE);
			menuEntry = DaoLocator.getDao(MenuEntryDao.class)
									.save(menuEntry);

			final Random r = new Random(System.currentTimeMillis());
			for (int i = 0; i < 10; i++) {
				Order order = new Order();
				order.setMenuEntry(menuEntry);
				final Calendar orderDate = Calendar.getInstance();
				orderDate.add(Calendar.DAY_OF_YEAR, (r.nextInt(30) + 1));
				final Calendar collectDate = (Calendar) orderDate.clone();
				collectDate.add(Calendar.DAY_OF_YEAR, (r.nextInt(10) + 1));
				order.setOrderDate(orderDate);
				order.setCollectDate(collectDate);
				order.setUser(user);
				order = DaoLocator.getDao(OrderDao.class)
									.save(order);
			}
		} catch (RemoteException e) {

		}

	}

	/**
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String args[]) throws Throwable {
		prepareData();
		launch(args);
	}

}
