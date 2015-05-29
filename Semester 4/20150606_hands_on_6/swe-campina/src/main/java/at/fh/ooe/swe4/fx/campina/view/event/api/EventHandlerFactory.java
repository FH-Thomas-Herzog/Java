package at.fh.ooe.swe4.fx.campina.view.event.api;

import java.io.Serializable;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public interface EventHandlerFactory extends Serializable {

	public <T extends Event> Map<EventType, EventHandler> registerEventHandler(final String id);
}
