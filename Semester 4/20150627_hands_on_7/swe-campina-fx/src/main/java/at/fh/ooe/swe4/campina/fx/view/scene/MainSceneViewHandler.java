package at.fh.ooe.swe4.campina.fx.view.scene;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import at.fh.ooe.swe4.campina.fx.component.builder.impl.MenuBarBuilder;
import at.fh.ooe.swe4.campina.fx.component.builder.impl.MenuBuilder;
import at.fh.ooe.swe4.campina.fx.component.builder.impl.MenuItemBuilder;
import at.fh.ooe.swe4.campina.fx.view.admin.login.part.LoginTabViewHandler;
import at.fh.ooe.swe4.campina.fx.view.admin.menu.part.MenuTabViewHandler;
import at.fh.ooe.swe4.campina.fx.view.admin.order.part.OrderTabViewHandler;
import at.fh.ooe.swe4.campina.fx.view.admin.user.part.UserTabviewHandler;
import at.fh.ooe.swe4.campina.fx.view.api.EventHandlerFactory;
import at.fh.ooe.swe4.campina.fx.view.api.ViewHandler;

/**
 * The view ahdnler for the main scene.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 4, 2015
 */
public class MainSceneViewHandler implements ViewHandler<Scene> {

	private static final long	serialVersionUID	= -7630460091326559133L;

	private UserTabviewHandler	userTab;
	private LoginTabViewHandler	loginTab;
	private MenuTabViewHandler	menuTab;
	private OrderTabViewHandler	orderTab;

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
		CLOSE(3, "item-close", "Schließen", MenuDefinition.FILE);

		public final String								id;
		public final String								label;
		public final Integer							ordinal;
		public final MenuDefinition						menu;
		public static final EventHandlerFactory<String>	eventHandlerFactory	= new MainSceneEventHandlerFactory();

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
	public MainSceneViewHandler() {
	}

	@Override
	public Scene createNode() {
		final Pane rootPane = new VBox();
		final Pane menuBarBox = new VBox();
		final TabPane tabPane = new TabPane();

		rootPane.setId("root-pane");
		menuBarBox.setId("menu-bar-box");
		tabPane.setId("tab-pane");

		final Scene scene = new Scene(rootPane);
		this.userTab = new UserTabviewHandler(scene);
		this.loginTab = new LoginTabViewHandler(scene);
		this.menuTab = new MenuTabViewHandler(scene);
		this.orderTab = new OrderTabViewHandler(scene);

		prepareMenuBox(menuBarBox);
		prepareTabs(tabPane);

		rootPane.getChildren()
				.add(menuBarBox);
		rootPane.getChildren()
				.add(tabPane);

		return scene;
	}

	@Override
	public void initHandler() {
		// this.loginTab.init();
		this.userTab.initHandler();
		this.menuTab.initHandler();
		this.orderTab.initHandler();
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

	public void prepareTabs(final TabPane tabPane) {
		Objects.requireNonNull(tabPane, "Cannot prepare null content box");

		final Tab login = loginTab.createNode();
		final Tab user = userTab.createNode();
		final Tab menu = menuTab.createNode();
		final Tab order = orderTab.createNode();
		// user.setDisable(Boolean.TRUE);
		// menu.setDisable(Boolean.TRUE);
		// order.setDisable(Boolean.TRUE);

		tabPane.getTabs()
				.add(login);
		tabPane.getTabs()
				.add(user);
		tabPane.getTabs()
				.add(menu);
		tabPane.getTabs()
				.add(order);
	}

	@Override
	public String getId() {
		return "main-scene";
	}
}
