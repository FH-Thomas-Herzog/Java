package at.fh.ooe.swe4.fx.campina.component.builder.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.MenuItem;
import at.fh.ooe.swe4.fx.campina.component.builder.api.AbstractFxComponentBuilder;
import at.fh.ooe.swe4.fx.campina.component.builder.exception.DuplicateKeyException;

public class MenuItemBuilder extends AbstractFxComponentBuilder<MenuItem, MenuItemBuilder> {

	private Set<String>	ids;

	public MenuItemBuilder start() {
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
		for (Entry<EventType, List<EventHandler>> event : events.entrySet()) {
			for (EventHandler handler : event.getValue()) {
				item.addEventHandler(event.getKey(), handler);
			}
		}
		return item;
	}

	public MenuItemBuilder end() {
		ids = null;
		events = null;
		return this;
	}

	@Override
	protected void checkIfStarted() {
		if (ids == null) {
			throw new IllegalStateException("Builder not inititalized");
		}
	}
}
