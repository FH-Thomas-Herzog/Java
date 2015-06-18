package at.fh.ooe.swe4.campina.fx.view.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

/**
 * Specifies an EventHandlerFactory which provides a mpa of event handlers
 * mapped to their event Type.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 * @param <T>
 *            the key type
 */
public interface EventHandlerFactory<K> extends Serializable {

	/**
	 * registers the events for an given id which should point to an
	 * EventTarget.
	 * 
	 * @param id
	 *            the event target id
	 * @return the map containing the registered events
	 */
	public <T extends Event> Map<EventType, List<EventHandler>> registerEventHandler(final K id);
}
