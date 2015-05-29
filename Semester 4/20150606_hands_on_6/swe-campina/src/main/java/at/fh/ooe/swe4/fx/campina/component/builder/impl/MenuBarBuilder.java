package at.fh.ooe.swe4.fx.campina.component.builder.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class MenuBarBuilder {

	private Set<Menu>	menus;

	public MenuBarBuilder start() {
		if (menus != null) {
			throw new IllegalStateException("Builder already initialized");
		}
		menus = new HashSet<>();
		return this;
	}

	public MenuBarBuilder addMenu(final Menu menu) {
		checkIfStarted();
		Objects.requireNonNull(menu, "Cannto add null menu to menu bar");

		menus.add(menu);
		return this;
	}

	public MenuBar build() {
		checkIfStarted();
		final MenuBar bar = new MenuBar();
		bar.getMenus()
			.addAll(menus);
		return bar;
	}

	public MenuBarBuilder end() {
		checkIfStarted();
		menus = null;
		return this;
	}

	private void checkIfStarted() {
		if (menus == null) {
			throw new IllegalStateException("Buuilder not inititalized");
		}
	}
}
