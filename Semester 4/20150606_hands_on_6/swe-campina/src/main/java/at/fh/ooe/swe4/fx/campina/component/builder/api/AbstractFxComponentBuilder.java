package at.fh.ooe.swe4.fx.campina.component.builder.api;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javafx.event.EventHandler;
import javafx.event.EventType;

/**
 * This is the base builder for all component buidlers.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 * @param <T>
 *            the Component this builder is for
 * @param <B>
 *            the actual buidler type
 */
@SuppressWarnings({
					"unchecked", "rawtypes" })
public abstract class AbstractFxComponentBuilder<T, B extends AbstractFxComponentBuilder<T, B>> {

	public Map<EventType, List<EventHandler>>	events;

	/**
	 * 
	 */
	public AbstractFxComponentBuilder() {
	}

	/**
	 * Registers the event handlers
	 * 
	 * @param handlers
	 *            the handlers to register
	 * @return the current instance
	 */
	public B registerEventHandlers(
			final Map<EventType, List<EventHandler>> handlers) {
		checkIfStarted();
		Objects.requireNonNull(handlers, "Cannot add event handlers because provided map is null");

		for (Entry<EventType, List<EventHandler>> entry : handlers.entrySet()) {
			events.put(entry.getKey(), entry.getValue());
		}

		return (B) this;
	}

	/**
	 * Ensures that the handler has been porperly started.
	 */
	protected abstract void checkIfStarted();

}
