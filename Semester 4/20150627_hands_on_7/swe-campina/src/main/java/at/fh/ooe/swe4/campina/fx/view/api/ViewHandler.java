package at.fh.ooe.swe4.campina.fx.view.api;

import javafx.scene.Scene;

/**
 * Specifies an handler for a view or view part.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 3, 2015
 * @param <N>
 *            the type of the component to create and manage
 */
public interface ViewHandler<N> extends IdHolder<String> {

	/**
	 * Creates the scene part.
	 * 
	 * @return the root node of this scene part.
	 */
	public N createNode();

	/**
	 * Initializes this part right after the {@link Scene} holds this part.
	 */
	public void initHandler();
}
