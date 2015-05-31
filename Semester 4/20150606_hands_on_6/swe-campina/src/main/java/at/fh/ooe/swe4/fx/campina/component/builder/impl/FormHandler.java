package at.fh.ooe.swe4.fx.campina.component.builder.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import at.fh.ooe.swe4.fx.campina.view.annotation.FormField;
import at.fh.ooe.swe4.fx.campina.view.constants.FormFieldType;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;
import at.fh.ooe.swe4.fx.campina.view.model.AbstractModel;

/**
 * This class is a for field handler which handles the form fields related to
 * the model class the handler is for. <br>
 * This implementation uses fluent api.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 31, 2015
 * @param <T>
 */
public class FormHandler<T extends AbstractModel> {

	/**
	 * Model which holds the resolved form field information.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date May 31, 2015
	 */
	private static class FormFieldResolvedModel {

		public final String		id;
		public final String		methodGetterName;
		public final String		methodSetterName;
		public final FormField	field;

		/**
		 * @param methodName
		 * @param field
		 */
		public FormFieldResolvedModel(String methodName, FormField field) {
			super();
			this.methodGetterName = methodName;
			this.methodSetterName = methodName.replace("get", "set");
			this.id = "field-" + methodName.substring(3, (methodName.length() - 1))
											.toLowerCase();
			;
			this.field = field;
		}
	}

	private Class<T>	modelClass;

	/**
	 * Empty default constructor
	 */
	public FormHandler() {
		super();
	}

	/**
	 * Initializes this builder.
	 * 
	 * @param modelClass
	 *            the backed model class this builder is for
	 * @return the current instance
	 * @throws NullPointerException
	 *             if the given model class is null
	 * @throws IllegalStateException
	 *             if the builder is already started
	 */
	public FormHandler<T> init(Class<T> modelClass) {
		Objects.requireNonNull(modelClass, "Model class with @FormField annotations must be provided");

		if (this.modelClass != null) {
			throw new IllegalStateException("Builder is already started");
		}

		this.modelClass = modelClass;
		return this;
	}

	/**
	 * Ands this builder.
	 * 
	 * @return the current instance
	 * @throws IllegalStateException
	 *             if the builder hasn't been started before
	 */
	public FormHandler<T> end() {
		checkIfStarted();

		this.modelClass = null;
		return this;
	}

	/**
	 * Generates the grid which holds the form and its fields.
	 * 
	 * @param ctx
	 *            the backing form context which gets set on each form field
	 *            node (label, field, message)
	 * @return the generated grid
	 * @throws NullPointerException
	 *             if the given id is null
	 * @throws IllegalStateException
	 *             if the builder hasn't been started before
	 */
	public GridPane generateFormGrid(FormContext<T> ctx) {
		checkIfStarted();
		Objects.requireNonNull(ctx, "Form Context must be given");

		// column constrains, should be replaced by css
		final ColumnConstraints labelColConst = new ColumnConstraints(2);
		labelColConst.setPrefWidth(150);
		final ColumnConstraints valueColConst = new ColumnConstraints(2);
		valueColConst.setPrefWidth(250);

		// the grid which holds the form
		final GridPane gridPane = new GridPane();
		gridPane.setId(ctx.id + "-grid-pane");
		gridPane.setHgap(2);
		gridPane.setUserData(ctx);
		gridPane.getColumnConstraints()
				.add(labelColConst);
		gridPane.getColumnConstraints()
				.add(valueColConst);

		// the form fields defined in the model
		final List<FormFieldResolvedModel> models = createResolvedModels(modelClass);

		// generate form fields
		for (int i = 0; i < models.size(); i++) {
			final FormFieldResolvedModel model = models.get(i);

			// form field label
			final Text labelText = new Text(model.field.label());
			labelText.setId("label-" + model.id);
			labelText.setUserData(ctx);

			// form field message
			final Text messageText = new Text("");
			messageText.setId("message-" + model.id);
			messageText.setVisible(Boolean.FALSE);
			messageText.setUserData(ctx);

			// form field
			final Node node = model.field.type()
											.create();

			node.setId(model.id);
			node.setUserData(ctx);

			// TODO: register form field events

			// set form fields on grid
			gridPane.add(new Text(), 0, i);
			gridPane.add(messageText, 1, i);
			gridPane.add(labelText, 0, (i + 1));
			gridPane.add(node, 1, (i + 1));
		}

		return gridPane;
	}

	/**
	 * Fills the model with the form field set values.
	 * 
	 * @param scene
	 *            the {@link Scene} instance where the form resides
	 * @param model
	 *            the model where the form field values will be set
	 * @return the current instance
	 * @throws NullPointerException
	 *             if either the scene or model is null
	 * @throws IllegalStateException
	 *             if the model defines form fields which could not be found on
	 *             the given scene.<br>
	 *             If the reflective invocation of the models setter fails<br>
	 *             If the builder hasn't been started yet
	 */
	public FormHandler<T> fillModel(final Scene scene, final T model) {
		checkIfStarted();
		Objects.requireNonNull(scene, "Need to scene to search for form fields");
		Objects.requireNonNull(model, "Need model isntance which get filled");

		final List<FormFieldResolvedModel> models = createResolvedModels(modelClass);
		for (FormFieldResolvedModel fieldModel : models) {
			final Node node = scene.lookup("#" + fieldModel.id);
			if (node == null) {
				throw new IllegalStateException("Scene does not contain form field with id");
			}
			try {
				final Method method;
				method = model.getClass()
								.getMethod(fieldModel.methodSetterName, fieldModel.field.type().valueClass);
				method.invoke(model, FormFieldType.getFormFieldValue(node));
			} catch (Throwable e) {
				throw new IllegalStateException("Could not set model value", e);
			}
		}
		return this;
	}

	/**
	 * Fills the form with the model provided set values.
	 * 
	 * @param scene
	 *            the {@link Scene} instance where the form resides
	 * @param model
	 *            the model which provides the form field values
	 * @return the current instance
	 * @throws NullPointerException
	 *             if either the scene or model is null
	 * @throws IllegalStateException
	 *             if the model defines form fields which could not be found on
	 *             the given scene.<br>
	 *             If the reflective invocation of the models getter fails<br>
	 *             If the builder hasn't been started yet
	 */
	public FormHandler<T> fillForm(final Scene scene, final T model) {
		checkIfStarted();
		Objects.requireNonNull(scene, "Need to scene to search for form fields");
		Objects.requireNonNull(model, "Need model isntance which get filled");

		final List<FormFieldResolvedModel> models = createResolvedModels(modelClass);
		for (FormFieldResolvedModel fieldModel : models) {
			final Node node = scene.lookup("#" + fieldModel.id);
			if (node == null) {
				throw new IllegalStateException("Scene does not contain form field with id");
			}
			try {
				final Object value = model.getClass()
											.getMethod(fieldModel.methodGetterName)
											.invoke(model);
				FormFieldType.setFormValue(node, value);
			} catch (Throwable e) {
				throw new IllegalStateException("Could not set model value", e);
			}
		}
		return this;
	}

	/**
	 * Resets the form by searching all form elements in the given scene. It
	 * sets all values to null.
	 * 
	 * @param scene
	 *            the {@link Scene} instance where the form resides
	 * @return the current instance
	 * @throws NullPointerException
	 *             if the scene is null
	 * @throws IllegalStateException
	 *             if a model has a for field defined but it could not be found
	 *             on the scene
	 */
	public FormHandler<T> resetForm(final Scene scene) {
		checkIfStarted();
		Objects.requireNonNull(scene, "Need to scene to search for form fields");

		final List<FormFieldResolvedModel> models = createResolvedModels(modelClass);
		for (FormFieldResolvedModel fieldModel : models) {
			final Node node = scene.lookup("#" + fieldModel.id);
			if (node == null) {
				throw new IllegalStateException("Scene does not contain form field with id");
			}
			FormFieldType.resetFormValue(node);
		}
		return this;
	}

	private void checkIfStarted() {
		if (modelClass == null) {
			throw new IllegalStateException("Builder not started");
		}
	}

	/**
	 * Creates a list of resolved models for the given model class.
	 * 
	 * @param clazz
	 *            the class of the model which provides {@link FormFieldType}
	 *            annotated methods
	 * @return the resolved form fields represented by the created model
	 * @throws NullPointerException
	 *             if the model class is null
	 */
	private List<FormFieldResolvedModel> createResolvedModels(final Class<T> clazz) {
		Objects.requireNonNull(clazz, "Class must not be null");

		final List<FormFieldResolvedModel> models = new ArrayList<>();
		final Method[] methods = clazz.getMethods();

		// iterate over all methods an resolve annotated ones
		for (Method method : methods) {
			final FormField field = method.getAnnotation(FormField.class);
			if (field != null) {
				final String methodName = method.getName();
				if (!methodName.startsWith("get")) {
					throw new IllegalStateException("FormField annotated method must be a valid getter method '" + methodName
							+ "'");
				}
				models.add(new FormFieldResolvedModel(methodName, field));
			}
		}

		// Sort models by annotation provided ordinal
		Collections.sort(models, new Comparator<FormFieldResolvedModel>() {
			@Override
			public int compare(FormFieldResolvedModel o1, FormFieldResolvedModel o2) {
				return Integer.valueOf(o1.field.ordinal())
								.compareTo(o2.field.ordinal());
			}
		});
		return models;
	}
}
