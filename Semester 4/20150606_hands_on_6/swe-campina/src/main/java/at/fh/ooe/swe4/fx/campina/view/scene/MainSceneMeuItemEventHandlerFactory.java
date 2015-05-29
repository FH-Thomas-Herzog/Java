package at.fh.ooe.swe4.fx.campina.view.scene;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import at.fh.ooe.swe4.fx.campina.view.event.api.EventHandlerFactory;
import at.fh.ooe.swe4.fx.campina.view.scene.MainSceneModel.MenuItemDefinition;

public class MainSceneMeuItemEventHandlerFactory implements EventHandlerFactory {

	private static final long	serialVersionUID	= 6992457824162264209L;

	public MainSceneMeuItemEventHandlerFactory() {
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<EventType, EventHandler> registerEventHandler(final String id) {
		Objects.requireNonNull(id, "Cannot register event for null id");

		final Map<EventType, EventHandler> eventHandlers = new HashMap<>();

		// new user event
		if (id.equals(MenuItemDefinition.CLOSE.id)) {
			eventHandlers.put(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.exit(0);
				}
			});
		}

		return eventHandlers;
	}
}
