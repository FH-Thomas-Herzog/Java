package at.fh.ooe.swe4.fx.campina.view.context;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import at.fh.ooe.swe4.fx.campina.component.builder.impl.FormHandler;
import at.fh.ooe.swe4.fx.campina.view.model.AbstractModel;

/**
 * This class represents the form context and provides access to the model, form
 * handler and the backing scene, which makes it easy to separate action logic
 * from view build logic.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 31, 2015
 * @param <T>
 */
public class FormContext<T extends AbstractModel> {

	public final String						id;
	public final FormHandler<T>				formHandler;
	public T								model;
	public final Scene						scene;
	public boolean							valid				= Boolean.TRUE;
	public Map<String, ObservableList<T>>	observableModelMap	= new ConcurrentHashMap<>();
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

	public void putObserable(final String key, final ObservableList<T> list) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(list);

		observableModelMap.put(key, list);
	}

	public ObservableList<T> getObserable(final String key) {
		Objects.requireNonNull(key);

		return observableModelMap.get(key);
	}

	public void putNode(final String key, final Node node) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(node);

		nodeMap.put(key, node);
	}

	public Node getNode(final String key) {
		Objects.requireNonNull(key);

		return nodeMap.get(key);
	}
}
