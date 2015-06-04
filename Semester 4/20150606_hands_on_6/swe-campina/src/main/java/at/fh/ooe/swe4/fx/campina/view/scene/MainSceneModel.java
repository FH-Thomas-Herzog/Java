package at.fh.ooe.swe4.fx.campina.view.scene;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import at.fh.ooe.swe4.fx.campina.component.builder.impl.MenuBarBuilder;
import at.fh.ooe.swe4.fx.campina.component.builder.impl.MenuBuilder;
import at.fh.ooe.swe4.fx.campina.component.builder.impl.MenuItemBuilder;
import at.fh.ooe.swe4.fx.campina.view.admin.login.part.LoginTab;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.tab.MenuTab;
import at.fh.ooe.swe4.fx.campina.view.admin.user.part.UserTab;
import at.fh.ooe.swe4.fx.campina.view.api.SceneFactory;
import at.fh.ooe.swe4.fx.campina.view.event.api.EventHandlerFactory;

/**
 * css of roman for javafx
 * https://github.com/romanlum/StudyCode/tree/master/SWO4/uebungMoodle6
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 4, 2015
 */
public class MainSceneModel extends Application implements SceneFactory {

	private static final long	serialVersionUID	= -7630460091326559133L;

	public final Pane			rootPane			= new VBox();
	public final Pane			menuBarBox			= new VBox();
	public final TabPane		tabPane				= new TabPane();

	private UserTab				userTab;
	private LoginTab			loginTab;
	private MenuTab				menuTab;

	/**
	 * Enumeration which specifies the menus placed in the menu bar.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 29, 2015
	 */
	public static enum MenuDefinition {
		FILE(1, "menu-file", "Dateien");

		public final int						ordinal;
		public final String						id;
		public final String						label;
		public static final EventHandlerFactory	eventHandlerFactory	= null;

		/**
		 * @param ordinal
		 * @param id
		 * @param label
		 */
		private MenuDefinition(int ordinal, String id, String label) {
			this.ordinal = ordinal;
			this.id = id;
			this.label = label;
		}
	}

	/**
	 * This enumeration specifies the menu items for the specified menus.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 29, 2015
	 */
	public static enum MenuItemDefinition {
		/*-- User menu items --*/
		SAVE(1, "item-save", "Speichern", MenuDefinition.FILE),
		SAVE_AS(2, "item-save-as", "Speichern als", MenuDefinition.FILE),
		CLOSE(3, "item-close", "Schließen", MenuDefinition.FILE);

		public final String						id;
		public final String						label;
		public final Integer					ordinal;
		public final MenuDefinition				menu;
		public static final EventHandlerFactory	eventHandlerFactory	= new MainSceneMeuItemEventHandlerFactory();

		/**
		 * @param ordinal
		 * @param id
		 * @param label
		 * @param menu
		 */
		private MenuItemDefinition(int ordinal, String id, String label, MenuDefinition menu) {
			this.id = id;
			this.label = label;
			this.ordinal = ordinal;
			this.menu = menu;
		}

		/**
		 * Filters the provided enumerations for the given menu.
		 * 
		 * @param menu
		 *            the menu to get its specified items
		 * @return the menu related items, an empty list otherwise
		 */
		public static final List<MenuItemDefinition> filterForMenu(final MenuDefinition menu) {
			final List<MenuItemDefinition> items;
			if (menu != null) {
				items = Arrays.asList(MenuItemDefinition.values())
								.stream()
								.filter((el) -> menu.equals(el.menu))
								.collect(Collectors.toList());
				Collections.sort(items, (o1, o2) -> o1.ordinal.compareTo(o2.ordinal));
			} else {
				items = Collections.EMPTY_LIST;
			}
			return items;
		}
	}

	/**
	 * Creates a main scene definition instance.<br>
	 * The main components ids are set here.
	 */
	public MainSceneModel() {
		rootPane.setId("root-pane");
		menuBarBox.setId("menu-bar-box");
		tabPane.setId("tab-pane");
	}

	@Override
	public Scene createScene() {
		final Scene scene = new Scene(rootPane);
		this.userTab = new UserTab(scene);
		this.loginTab = new LoginTab(scene);
		this.menuTab = new MenuTab(scene);

		prepareMenuBox(menuBarBox);
		prepareTabs(tabPane, scene);

		rootPane.getChildren()
				.add(menuBarBox);
		rootPane.getChildren()
				.add(tabPane);

		return scene;
	}

	public void initScene() {
		// this.loginTab.init();
		this.userTab.init();
		this.menuTab.init();
	}

	/**
	 * Prepares the menu box by adding all menus and its items.
	 * 
	 * @param menuBox
	 *            the menu box to provide
	 */
	public void prepareMenuBox(final Pane menuBox) {
		Objects.requireNonNull(menuBox, "Cannot prepare null menu box");

		MenuBarBuilder mbb = new MenuBarBuilder();
		MenuBuilder mb = new MenuBuilder();
		MenuItemBuilder mib = new MenuItemBuilder();

		mbb.start();
		for (MenuDefinition menu : MenuDefinition.values()) {
			mb.start();
			if (MenuDefinition.eventHandlerFactory != null) {
				mb.registerEventHandlers(MenuItemDefinition.eventHandlerFactory.registerEventHandler(menu.id));
			}
			for (MenuItemDefinition item : MenuItemDefinition.filterForMenu(menu)) {
				mib.start();
				if (MenuItemDefinition.eventHandlerFactory != null) {
					mib.registerEventHandlers(MenuItemDefinition.eventHandlerFactory.registerEventHandler(item.id));
				}
				mb.addItem(mib.build(item.id, item.label));
				mib.end();
			}
			mbb.addMenu(mb.build(menu.id, menu.label));
			mb.end();
		}
		menuBox.getChildren()
				.add(mbb.build());
		mbb.end();
	}

	public void prepareTabs(final TabPane contentBox, Scene scene) {
		Objects.requireNonNull(contentBox, "Cannot prepare null content box");

		final Tab login = loginTab.create();
		final Tab user = userTab.create();
		final Tab menu = menuTab.create();
		// user.setDisable(Boolean.TRUE);
		// menu.setDisable(Boolean.TRUE);
		//
		// tabPane.getTabs()
		// .add(login);
		tabPane.getTabs()
				.add(user);
		tabPane.getTabs()
				.add(menu);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final MainSceneModel def = new MainSceneModel();
		final Scene scene = def.createScene();
		scene.getStylesheets()
				.add(getClass().getResource("css/main.css")
								.toExternalForm());
		def.initScene();
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(700);
		primaryStage.setMinHeight(300);
		primaryStage.setTitle("Campina");

		primaryStage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
}
