package at.fh.ooe.swe4.fx.campina.component.builder.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import at.fh.ooe.swe4.fx.campina.view.annotation.FormField;
import at.fh.ooe.swe4.fx.campina.view.constants.FormFieldType;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;
import at.fh.ooe.swe4.fx.campina.view.model.AbstractModel;
import at.fh.ooe.swe4.fx.campina.view.util.FormUtils.RequiredValidator;
import at.fh.ooe.swe4.fx.campina.view.util.FormUtils.Validator;

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
		public final String		globalPrefix;
		public final String		methodGetterName;
		public final String		methodSetterName;
		public final FormField	field;

		/**
		 * @param globalPrefix
		 *            TODO
		 * @param methodName
		 * @param field
		 */
		public FormFieldResolvedModel(String globalPrefix, String methodName, FormField field) {
			super();
			Objects.requireNonNull(globalPrefix);
			Objects.requireNonNull(methodName);
			Objects.requireNonNull(field);

			this.globalPrefix = globalPrefix;
			this.methodGetterName = methodName;
			this.methodSetterName = methodName.replace("get", "set");
			this.id = "field-" + methodName.substring(3, methodName.length())
											.toLowerCase();
			;
			this.field = field;
		}

		public String toMessageId() {
			Objects.requireNonNull(id);

			return globalPrefix + "-message-" + id;
		}

		public String toLabelId() {
			return globalPrefix + "-label-" + id;
		}

		public String toNodeId() {
			return globalPrefix + "-node-" + id;
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
		final ColumnConstraints messageColConst = new ColumnConstraints(2);
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
		gridPane.getColumnConstraints()
				.add(messageColConst);

		// the form fields defined in the model
		final List<FormFieldResolvedModel> models = createResolvedModels(modelClass, ctx);

		// generate form fields
		for (int i = 0; i < models.size(); i++) {
			final FormFieldResolvedModel model = models.get(i);

			// form field label
			final Text labelText = new Text(model.field.label());
			labelText.setId(model.toLabelId());
			labelText.setUserData(ctx);

			// form field message
			final Text messageText = new Text("");
			messageText.setId(model.toMessageId());
			messageText.setVisible(Boolean.FALSE);
			messageText.setUserData(ctx);

			// form field
			final Node node = model.field.type()
											.create();

			node.setId(model.toNodeId());
			node.setUserData(ctx);
			System.out.println("create: " + model.toNodeId());
			System.out.println("create: " + node.getId());

			// TODO: register form field events

			// set form fields on grid
			gridPane.add(labelText, 0, i);
			gridPane.add(node, 1, i);
			gridPane.add(messageText, 2, i);
		}

		return gridPane;
	}

	/**
	 * Fills the model with the form field set values.
	 * 
	 * @param ctx
	 *            TODO
	 * 
	 * @return the current instance
	 * @throws NullPointerException
	 *             if either the scene or model is null
	 * @throws IllegalStateException
	 *             if the model defines form fields which could not be found on
	 *             the given scene.<br>
	 *             If the reflective invocation of the models setter fails<br>
	 *             If the builder hasn't been started yet
	 */
	public FormHandler<T> fillModel(FormContext<T> ctx) {
		checkIfStarted();
		Objects.requireNonNull(ctx, "Need form context to search for form fields");

		final List<FormFieldResolvedModel> models = createResolvedModels(modelClass, ctx);
		for (FormFieldResolvedModel fieldModel : models) {
			final Node node = ctx.scene.lookup("#" + fieldModel.toNodeId());
			if (node == null) {
				throw new IllegalStateException("Scene does not contain form field with id");
			}
			try {
				final Method method;
				method = ctx.model.getClass()
									.getMethod(fieldModel.methodSetterName, fieldModel.field.type().valueClass);
				method.invoke(ctx.model, FormFieldType.getFormFieldValue(node));
			} catch (Throwable e) {
				throw new IllegalStateException("Could not set model value", e);
			}
		}
		return this;
	}

	/**
	 * Fills the form with the model provided set values.
	 * 
	 * @param ctx
	 *            TODO
	 * 
	 * @return the current instance
	 * @throws NullPointerException
	 *             if either the scene or model is null
	 * @throws IllegalStateException
	 *             if the model defines form fields which could not be found on
	 *             the given scene.<br>
	 *             If the reflective invocation of the models getter fails<br>
	 *             If the builder hasn't been started yet
	 */
	public FormHandler<T> fillForm(FormContext<T> ctx) {
		checkIfStarted();
		Objects.requireNonNull(ctx, "Need context to search for form fields");

		final List<FormFieldResolvedModel> models = createResolvedModels(modelClass, ctx);
		for (FormFieldResolvedModel fieldModel : models) {
			final Node node = ctx.scene.lookup("#" + fieldModel.toNodeId());
			if (node == null) {
				throw new IllegalStateException("Scene does not contain form field with id");
			}
			try {
				final Object value = ctx.model.getClass()
												.getMethod(fieldModel.methodGetterName)
												.invoke(ctx.model);
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
	 * @param ctx
	 *            TODO
	 * 
	 * @return the current instance
	 * @throws NullPointerException
	 *             if the scene is null
	 * @throws IllegalStateException
	 *             if a model has a for field defined but it could not be found
	 *             on the scene
	 */
	public FormHandler<T> resetForm(FormContext<T> ctx) {
		checkIfStarted();
		Objects.requireNonNull(ctx.scene, "Need to scene to search for form fields");

		final List<FormFieldResolvedModel> models = createResolvedModels(modelClass, ctx);
		for (FormFieldResolvedModel fieldModel : models) {
			final Node node = ctx.scene.lookup("#" + fieldModel.toNodeId());
			if (node == null) {
				throw new IllegalStateException("Scene does not contain form field with id");
			}
			FormFieldType.resetFormValue(node);
			final Text messageNode = (Text) ctx.scene.lookup("#" + fieldModel.toMessageId());
			messageNode.setVisible(Boolean.FALSE);
		}
		return this;
	}

	public FormHandler<T> validateForm(final FormContext<T> ctx) {
		checkIfStarted();
		Objects.requireNonNull(ctx, "Need form context to validate form");

		final Validator<Node> requiredValidator = new RequiredValidator<Node>();
		final List<FormFieldResolvedModel> models = createResolvedModels(modelClass, ctx);
		for (FormFieldResolvedModel fieldModel : models) {
			final Node node = ctx.scene.lookup("#" + fieldModel.toNodeId());
			if (node == null) {
				throw new IllegalStateException("Scene does not contain form field with id");
			}
			if (fieldModel.field.required()) {
				System.out.println("validate: " + fieldModel.toNodeId());
				if (!requiredValidator.valid(node)) {
					ctx.valid = Boolean.FALSE;
					final Text messageNode = (Text) ctx.scene.lookup("#" + fieldModel.toMessageId());
					messageNode.setVisible(Boolean.TRUE);
					messageNode.setText(fieldModel.field.requiredMessage());
				}
			}
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
	 * @param ctx
	 *            TODO
	 * @return the resolved form fields represented by the created model
	 * @throws NullPointerException
	 *             if the model class is null
	 */
	private List<FormFieldResolvedModel> createResolvedModels(final Class<T> clazz, FormContext<T> ctx) {
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
				models.add(new FormFieldResolvedModel(ctx.id, methodName, field));
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
