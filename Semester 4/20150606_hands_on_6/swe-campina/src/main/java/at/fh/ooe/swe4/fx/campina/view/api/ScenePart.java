package at.fh.ooe.swe4.fx.campina.view.api;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;

/**
 * Specifies an {@link Scene} part which is a bean which provides node to be
 * placed on a scene.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 3, 2015
 * @param <N>
 *            the type of the node to create.<br>
 *            Not extends Node because {@link Tab}for instance is no instance of
 *            {@link Node}.
 */
public interface ScenePart<N> extends IdHolder<String> {

	public N create();

	public void init();
}
