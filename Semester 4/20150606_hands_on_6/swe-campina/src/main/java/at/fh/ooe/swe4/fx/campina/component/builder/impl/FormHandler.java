package at.fh.ooe.swe4.fx.campina.component.builder.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import at.fh.ooe.swe4.fx.campina.view.annotation.FormField;
import at.fh.ooe.swe4.fx.campina.view.annotation.SelectFormField;
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

		public final String					id;
		public final String					globalPrefix;
		public final String					methodGetterName;
		public final String					methodSetterName;
		public final FormField				field;
		private final Map<Object, Object>	additionalData	= new HashMap<>();

		public FormFieldResolvedModel(String globalPrefix, String methodName) {
			super();
			this.globalPrefix = globalPrefix;
			this.methodGetterName = methodName;
			this.methodSetterName = methodName.replace("get", "set");
			this.id = "field-" + methodName.substring(3, methodName.length())
											.toLowerCase();

			this.field = null;
		}

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

			if (!methodName.startsWith("get")) {
				throw new IllegalArgumentException("Method must be a valid java bean getter");
			}
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

		public <T> void putData(final Object
				key, T instance) {
			Objects.requireNonNull(key);
			Objects.requireNonNull(instance);

			additionalData.put(key, instance);
		}

		public <T> T getData(final Object key) {
			Objects.requireNonNull(key);

			return (T) additionalData.get(key);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FormFieldResolvedModel other = (FormFieldResolvedModel) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
	}

	private boolean	started	= Boolean.FALSE;

	/**
	 * Empty default constructor
	 */
	public FormHandler() {
		super();
	}

	/**
	 * Initializes this builder.
	 * 
	 * @return the current instance
	 * @throws NullPointerException
	 *             if the given model class is null
	 * @throws IllegalStateException
	 *             if the builder is already started
	 */
	public FormHandler<T> init() {
		if (started) {
			throw new IllegalStateException("Handler needs to be end before restarted");
		}

		started = Boolean.TRUE;

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

		this.started = Boolean.FALSE;
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
		final List<FormFieldResolvedModel> models = createResolvedModels(ctx);

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
			// Handing for select type
			if (model.field.type()
							.equals(FormFieldType.SELECT)) {
				final ObservableList<Object> list = model.getData(model.id);
				final SelectFormField select = model.getData(SelectFormField.class);
				StringConverter<Object> converter = null;
				if (!select.converter()
							.equals(StringConverter.class)) {
					try {
						converter = select.converter()
											.newInstance();
					} catch (Throwable e) {
						throw new IllegalStateException("Could not isntantiate select string converter '" + select.converter()
																													.getName() + "'", e);
					}
				}
				final ChoiceBox<Object> box = (ChoiceBox<Object>) node;
				box.setItems(list);
				if (converter != null) {
					box.setConverter(converter);
				}
			}

			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			// Dev logging here
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			System.out.println(node.getId());
			System.out.println(messageText.getId());

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

		final List<FormFieldResolvedModel> models = createResolvedModels(ctx);
		for (FormFieldResolvedModel fieldModel : models) {
			final Node node = ctx.scene.lookup("#" + fieldModel.toNodeId());
			if (node == null) {
				throw new IllegalStateException("Scene does not contain form field with id");
			}
			try {
				final Method method;
				method = ctx.model.getClass()
									.getMethod(fieldModel.methodSetterName, fieldModel.field.type().valueClass);
				method.invoke(ctx.model, FormFieldType.getFormFieldValue(fieldModel.field.type(), node));
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

		final List<FormFieldResolvedModel> models = createResolvedModels(ctx);
		for (FormFieldResolvedModel fieldModel : models) {
			final Node node = ctx.scene.lookup("#" + fieldModel.toNodeId());
			if (node == null) {
				throw new IllegalStateException("Scene does not contain form field with id");
			}
			try {
				final Object value = ctx.model.getClass()
												.getMethod(fieldModel.methodGetterName)
												.invoke(ctx.model);
				FormFieldType.setFormValue(fieldModel.field.type(), node, value);
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

		final List<FormFieldResolvedModel> models = createResolvedModels(ctx);
		for (FormFieldResolvedModel fieldModel : models) {
			final Node node = ctx.scene.lookup("#" + fieldModel.toNodeId());
			if (node == null) {
				throw new IllegalStateException("Scene does not contain form field with id");
			}
			FormFieldType.resetFormValue(fieldModel.field.type(), node);
			final Text messageNode = (Text) ctx.scene.lookup("#" + fieldModel.toMessageId());
			messageNode.setVisible(Boolean.FALSE);
		}
		return this;
	}

	public FormHandler<T> validateForm(final FormContext<T> ctx) {
		checkIfStarted();
		Objects.requireNonNull(ctx, "Need form context to validate form");

		// expected valid
		ctx.valid = Boolean.TRUE;
		// required validator
		final Validator<Node> requiredValidator = new RequiredValidator<Node>();
		// the form field models
		final List<FormFieldResolvedModel> models = createResolvedModels(ctx);
		for (FormFieldResolvedModel fieldModel : models) {
			final Node node = ctx.scene.lookup("#" + fieldModel.toNodeId());
			if (node == null) {
				throw new IllegalStateException("Scene does not contain form field with id");
			}
			final Text messageNode = (Text) ctx.scene.lookup("#" + fieldModel.toMessageId());
			// need type validation
			if (FormFieldType.DECIMAL.equals(fieldModel.field.type())) {
				try {
					FormFieldType.getFormFieldValue(FormFieldType.DECIMAL, node);
					messageNode.setVisible(Boolean.FALSE);
					messageNode.setText("");
				} catch (NumberFormatException e) {
					ctx.valid = Boolean.FALSE;
					messageNode.setVisible(Boolean.TRUE);
					messageNode.setText("Keine gültige Nummer");
					continue;
				}
			}
			// need required validation
			if (fieldModel.field.required()) {
				// is invalid
				if (!requiredValidator.valid(fieldModel.field.type(), node)) {
					ctx.valid = Boolean.FALSE;
					messageNode.setVisible(Boolean.TRUE);
					messageNode.setText(fieldModel.field.requiredMessage());
				}
				// reset if valid
				else {
					messageNode.setText("");
				}
			}
		}
		return this;
	}

	private void checkIfStarted() {
		if (!started) {
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
	private List<FormFieldResolvedModel> createResolvedModels(FormContext<T> ctx) {
		Objects.requireNonNull(ctx, "Contextmust not be null");

		final T model = ctx.model;
		final List<FormFieldResolvedModel> models = new ArrayList<>();
		final Method[] methods = model.getClass()
										.getDeclaredMethods();

		// form field annotations
		for (Method method : methods) {
			final FormField field = method.getAnnotation(FormField.class);
			FormFieldResolvedModel fieldModel;
			final String methodName = method.getName();
			if (field != null) {
				if (!methodName.startsWith("get")) {
					throw new IllegalStateException("FormField annotated method must be a valid getter method '" + methodName
							+ "'");
				}
				fieldModel = new FormFieldResolvedModel(ctx.id, methodName, field);
				models.add(fieldModel);
			}
		}

		// additional annotations
		for (Method method : methods) {

			final SelectFormField select = method.getAnnotation(SelectFormField.class);
			FormFieldResolvedModel fieldModel;
			final String methodName = method.getName();
			System.out.println(methodName);

			if (select != null) {
				int index = -1;
				final String target = select.target();
				final String targetGetter = new StringBuilder("get").append(target.substring(0, 1)
																					.toUpperCase())
																	.append(target.substring(1, target.length()))
																	.toString();
				if ((index = models.indexOf(new FormFieldResolvedModel(ctx.id, targetGetter))) == -1) {
					throw new IllegalStateException("SelectFormField target '" + targetGetter + "' field model '" + model.getClass()
																															.getName() + "' not found");
				}
				fieldModel = models.get(index);
				try {
					final ObservableList<Object> dataList = (ObservableList<Object>) method.invoke(model);
					fieldModel.putData(fieldModel.id, dataList);
					fieldModel.putData(select.annotationType(), select);
				} catch (Throwable e) {
					throw new IllegalStateException("Cannot retrieve select data (? extends Iterable<?>)", e);
				}
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

	private <T extends Annotation> T hasAnnotation(final AnnotatedElement element, Class<T> clazz) {
		Objects.requireNonNull(element);

		final Annotation[] annotations = element.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType()
							.equals(clazz)) {
				return (T) annotation;
			}
		}

		return null;
	}
}
