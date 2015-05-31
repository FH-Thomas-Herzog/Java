package at.fh.ooe.swe4.fx.campina.view.context;

import java.util.Objects;

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

	public final String			id;
	public final FormHandler<T>	formHandler;
	public T					model;
	public final Scene			scene;

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
}
