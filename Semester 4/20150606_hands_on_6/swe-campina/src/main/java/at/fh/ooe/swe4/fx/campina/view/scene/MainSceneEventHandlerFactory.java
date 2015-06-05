package at.fh.ooe.swe4.fx.campina.view.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import at.fh.ooe.swe4.fx.campina.view.api.EventHandlerFactory;
import at.fh.ooe.swe4.fx.campina.view.scene.MainSceneViewHandler.MenuItemDefinition;

/**
 * The event handler factory for the main scene.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class MainSceneEventHandlerFactory implements EventHandlerFactory<String> {

	private static final long	serialVersionUID	= 6992457824162264209L;

	/**
	 * 
	 */
	public MainSceneEventHandlerFactory() {
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<EventType, List<EventHandler>> registerEventHandler(final String id) {
		Objects.requireNonNull(id, "Cannot register event for null id");

		final Map<EventType, List<EventHandler>> eventHandlers = new HashMap<>();
		eventHandlers.put(ActionEvent.ACTION, new ArrayList<EventHandler>());

		// new user event
		if (id.equals(MenuItemDefinition.CLOSE.id)) {
			eventHandlers.get(ActionEvent.ACTION)
							.add(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									System.exit(0);
								}
							});
		}

		return eventHandlers;
	}
}
