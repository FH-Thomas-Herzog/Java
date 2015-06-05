package at.fh.ooe.swe4.campina.fx.component.builder.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import at.fh.ooe.swe4.campina.fx.component.builder.api.AbstractFxComponentBuilder;

/**
 * This is the buidler for menu bars
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class MenuBarBuilder extends AbstractFxComponentBuilder<MenuBar, MenuBarBuilder> {

	private Set<Menu>	menus;

	/**
	 * Starts the buidler
	 * 
	 * @return the current instance
	 */
	public MenuBarBuilder start() {
		if (menus != null) {
			throw new IllegalStateException("Builder already initialized");
		}
		menus = new HashSet<>();
		return this;
	}

	/**
	 * Ads an menu to this builder
	 * 
	 * @param menu
	 *            the menu for the menu bar
	 * @return the current instance
	 */
	public MenuBarBuilder addMenu(final Menu menu) {
		checkIfStarted();
		Objects.requireNonNull(menu, "Cannto add null menu to menu bar");

		menus.add(menu);
		return this;
	}

	/**
	 * Buidls anew menu bar isntance with the ormerly set menus.
	 * 
	 * @return the menu bar instance
	 */
	public MenuBar build() {
		checkIfStarted();
		final MenuBar bar = new MenuBar();
		bar.getMenus()
			.addAll(menus);
		return bar;
	}

	/**
	 * Ends this builder. Will need to be restarted if reused.
	 * 
	 * @return the current instance
	 */
	public MenuBarBuilder end() {
		checkIfStarted();
		menus = null;
		return this;
	}

	@Override
	protected void checkIfStarted() {
		if (menus == null) {
			throw new IllegalStateException("Buuilder not inititalized");
		}
	}
}
