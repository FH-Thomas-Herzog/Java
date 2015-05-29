package at.fh.ooe.swe4.fx.campina.component.builder.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import at.fh.ooe.swe4.fx.campina.component.builder.api.AbstractFxComponentBuilder;

public class MenuBuilder extends AbstractFxComponentBuilder<Menu, MenuBuilder> {

	public List<MenuItem>	items	= null;

	public MenuBuilder start() {
		if (items != null) {
			throw new IllegalStateException("Builder already initialized");
		}

		items = new ArrayList<>();
		events = new HashMap<>();
		return this;
	}

	public MenuBuilder end() {
		checkIfStarted();
		items = null;
		return this;
	}

	public Menu build(final String id, final String label) {
		checkIfStarted();
		Objects.requireNonNull(label, "Cannot create menu with null id");
		Objects.requireNonNull(label, "Cannot create menu with null label");

		final Menu menu = new Menu(label);
		menu.setId(id);
		for (MenuItem item : items) {
			menu.getItems()
				.add(item);
			item.setId(new StringBuilder(item.getParentMenu()
												.getId()).append(":")
															.append(item.getId())
															.toString());
		}

		for (Entry<EventType, EventHandler> event : events.entrySet()) {
			menu.addEventHandler(event.getKey(), event.getValue());
		}
		return menu;
	}

	public MenuBuilder addItem(final MenuItem item) {
		checkIfStarted();
		Objects.requireNonNull(item, "Cannot add nul item");

		items.add(item);
		return this;
	}

	protected void checkIfStarted() {
		if (items == null) {
			throw new IllegalStateException("Builder not initialized");
		}
	}
}
