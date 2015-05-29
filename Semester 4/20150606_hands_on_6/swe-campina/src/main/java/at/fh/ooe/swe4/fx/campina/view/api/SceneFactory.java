package at.fh.ooe.swe4.fx.campina.view.api;

import java.io.Serializable;

import javafx.scene.Scene;

/**
 * This interface specifies the scene factories.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 29, 2015
 */
public interface SceneFactory extends Serializable {

	/**
	 * Creates a new {@link Scene} instance.
	 * 
	 * @return the newly created scene
	 */
	public Scene createScene();
}
