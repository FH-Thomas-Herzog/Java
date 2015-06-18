package at.fh.ooe.swe4.campina.fx.view.api;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import at.fh.ooe.swe4.campina.fx.view.form.FormHandler;

/**
 * This class represents the form context and provides access to the model, form
 * handler and the backing scene, which makes it easy to separate action logic
 * from view build logic.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 31, 2015
 * @param <T>
 */
public class FormContext<T extends AbstractViewModel> {

	public final String						id;
	public final FormHandler<T>				formHandler;
	public T								model;
	public final Scene						scene;
	public boolean							valid				= Boolean.TRUE;
	public Map<String, ObservableList<?>>	observableModelMap	= new ConcurrentHashMap<>();
	public Map<String, Node>				nodeMap				= new ConcurrentHashMap<>();

	/**
	 * @param id
	 * @param formHandler
	 * @param model
	 * @param scene
	 */
	public FormContext(final String id, final FormHandler<T> formHandler, final T model, final Scene scene) {
		Objects.requireNonNull(id);
		Objects.requireNonNull(formHandler);
		Objects.requireNonNull(model);
		Objects.requireNonNull(scene);

		this.id = id;
		this.model = model;
		this.formHandler = formHandler;
		this.scene = scene;
	}

	/**
	 * Puts an {@link ObservableList} to this context. An existing list would be
	 * overwritten if bakced map has already such key set.
	 * 
	 * @param key
	 *            the key of the list
	 * @param list
	 *            the list
	 */
	public<T> void putObserable(final String key, final ObservableList<?> list) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(list);

		observableModelMap.put(key, list);
	}

	/**
	 * Gets the backed {@link ObservableList} instance if managed by this
	 * context.
	 * 
	 * @param key
	 *            the key to get mapped list
	 * @return the found list, null otherwise
	 */
	public ObservableList<?> getObserable(final String key) {
		Objects.requireNonNull(key);

		return observableModelMap.get(key);
	}

	/**
	 * Puts a node to the backed list.
	 * 
	 * @param key
	 *            the key to map the node to
	 * @param node
	 *            the node to be managed by this context.
	 */
	public void putNode(final String key, final Node node) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(node);

		nodeMap.put(key, node);
	}

	/**
	 * Gets a managed node of this context.
	 * 
	 * @param key
	 *            the key to get its mapped node
	 * @return the found node, null otherwise
	 */
	public Node getNode(final String key) {
		Objects.requireNonNull(key);

		return nodeMap.get(key);
	}
}
