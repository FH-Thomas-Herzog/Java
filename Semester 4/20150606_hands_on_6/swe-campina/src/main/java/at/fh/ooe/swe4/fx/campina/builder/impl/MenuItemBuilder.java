package at.fh.ooe.swe4.fx.campina.builder.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import at.fh.ooe.swe4.fx.campina.builder.exception.DuplicateKeyException;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.MenuItem;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class MenuItemBuilder {

    private Set<String> ids;
    public Map<EventType, EventHandler> events;

    public MenuItemBuilder init() {
	ids = new HashSet<String>();
	events = new HashMap<>();
	return this;
    }

    public MenuItemBuilder removeFromContext(final String id) {
	checkIfStarted();
	Objects.requireNonNull(id,
		"Cannot remove item fron context with null id");

	ids.remove(id);
	return this;
    }

    public <T extends Event> MenuItemBuilder addEventHandler(
	    final EventType<T> type, final EventHandler<T> handler) {
	checkIfStarted();
	Objects.requireNonNull(type,
		"Need event type for adding event handlers");
	Objects.requireNonNull(handler, "Cannot add null event handler");

	events.put(type, handler);
	return this;
    }

    public MenuItem build(final String id, final String label) {
	checkIfStarted();
	Objects.requireNonNull(id, "Cannot create item for null id");
	Objects.requireNonNull(label, "Cannot create item for null label");

	if (!ids.add(id)) {
	    throw new DuplicateKeyException(
		    "Builder has already built an item with this id");
	}

	final MenuItem item = new MenuItem(label);
	item.setId(id);
	for (Entry<EventType, EventHandler> event : events.entrySet()) {
	    item.addEventHandler(event.getKey(), event.getValue());
	}
	return item;
    }

    public MenuItemBuilder end() {
	ids = null;
	events = null;
	return this;
    }

    private void checkIfStarted() {
	if (ids == null) {
	    throw new IllegalStateException("Builder not inititalized");
	}
    }
}
