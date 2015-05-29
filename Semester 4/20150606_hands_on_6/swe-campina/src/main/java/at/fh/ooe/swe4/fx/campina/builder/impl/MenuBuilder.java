package at.fh.ooe.swe4.fx.campina.builder.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MenuBuilder {

    public Set<MenuItem> items = null;
    public Map<EventType, EventHandler> events;

    public MenuBuilder init() {
	if (items != null) {
	    throw new IllegalStateException("Builder already initialized");
	}

	items = new HashSet<MenuItem>();
	events = new HashMap<>();
	return this;
    }

    public MenuBuilder end() {
	checkIfInitialized();
	items = null;
	return this;
    }

    public Menu build(final String label) {
	checkIfInitialized();
	Objects.requireNonNull(label, "Menu label text must be given");

	final Menu menu = new Menu(label);
	menu.getItems().addAll(items);
	for (Entry<EventType, EventHandler> event : events.entrySet()) {
	    menu.addEventHandler(event.getKey(), event.getValue());
	}
	return menu;
    }

    public MenuBuilder addItem(final MenuItem item) {
	checkIfInitialized();
	Objects.requireNonNull(item, "Cannot add nul item");

	items.add(item);
	return this;
    }

    public <T extends Event> MenuBuilder addEventHandler(
	    final EventType<T> type, final EventHandler<T> handler) {
	checkIfInitialized();
	Objects.requireNonNull(type,
		"Need event type for adding event handlers");
	Objects.requireNonNull(handler, "Cannot add null event handler");

	events.put(type, handler);
	return this;
    }

    private void checkIfInitialized() {
	if (items == null) {
	    throw new IllegalStateException("Builder not initialized");
	}
    }
}
