package at.fh.ooe.swe4.campina.fx.component.builder.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import at.fh.ooe.swe4.campina.fx.component.builder.api.AbstractFxComponentBuilder;

/**
 * This is the buidler for {@link Menu}
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class MenuBuilder extends AbstractFxComponentBuilder<Menu, MenuBuilder> {

	public List<MenuItem>	items	= null;

	/**
	 * Starts the builder
	 * 
	 * @return the current instance
	 */
	public MenuBuilder start() {
		if (items != null) {
			throw new IllegalStateException("Builder already initialized");
		}

		items = new ArrayList<>();
		events = new HashMap<>();
		return this;
	}

	/**
	 * Ends the builder. Needs to be restarted i reused.
	 * 
	 * @return the current instance
	 */
	public MenuBuilder end() {
		checkIfStarted();
		items = null;
		return this;
	}

	/**
	 * Builds the menu instance.
	 * 
	 * @param id
	 *            the id of the menu instance
	 * @param label
	 *            the label of the menu instance
	 * @return the menu instance
	 */
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

		for (Entry<EventType, List<EventHandler>> event : events.entrySet()) {
			for (EventHandler handler : event.getValue()) {
				menu.addEventHandler(event.getKey(), handler);
			}
		}
		return menu;
	}

	/**
	 * Adds an item to this builder
	 * 
	 * @param item
	 *            the item to be added
	 * @return the current instance
	 */
	public MenuBuilder addItem(final MenuItem item) {
		checkIfStarted();
		Objects.requireNonNull(item, "Cannot add nul item");

		items.add(item);
		return this;
	}

	@Override
	protected void checkIfStarted() {
		if (items == null) {
			throw new IllegalStateException("Builder not initialized");
		}
	}
}
