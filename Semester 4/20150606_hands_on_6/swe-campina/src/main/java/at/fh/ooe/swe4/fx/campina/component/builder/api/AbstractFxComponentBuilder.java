package at.fh.ooe.swe4.fx.campina.component.builder.api;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import at.fh.ooe.swe4.fx.campina.view.event.api.EventHandlerFactory;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

@SuppressWarnings({
					"unchecked", "rawtypes" })
public abstract class AbstractFxComponentBuilder<T, B extends AbstractFxComponentBuilder<T, B>> {

	public Map<EventType, EventHandler>	events;

	public AbstractFxComponentBuilder() {
	}

	public B registerEventHandlers(
			final Map<EventType, EventHandler> handlers) {
		checkIfStarted();
		Objects.requireNonNull(handlers, "Cannot add event handlers because provided map is null");

		for (Entry<EventType, EventHandler> entry : handlers.entrySet()) {
			registerEventHandler(entry.getKey(), entry.getValue());
		}

		return (B) this;
	}

	public B registerEventHandler(
			final EventType type, final EventHandler handler) {
		checkIfStarted();
		Objects.requireNonNull(type,
								"Need type for adding event handlers");
		Objects.requireNonNull(handler, "Cannot add null event handler");

		events.put(type, handler);
		return (B) this;
	}

	protected abstract void checkIfStarted();

}
