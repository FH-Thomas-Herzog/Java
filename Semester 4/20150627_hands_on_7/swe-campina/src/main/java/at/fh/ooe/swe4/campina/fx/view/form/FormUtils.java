package at.fh.ooe.swe4.campina.fx.view.form;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.converter.BigDecimalStringConverter;

/**
 * Utility class for handling form fields.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class FormUtils {

	private FormUtils() {
	}

	/**
	 * Enumeration whcih specifies the supported JavaFX components.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 5, 2015
	 */
	public enum FormFieldType {
		INPUT_TEXT(String.class, TextField.class),
		DATE_PICKER(Calendar.class, DatePicker.class),
		DECIMAL(BigDecimal.class, TextField.class),
		SELECT(Object.class, ChoiceBox.class);

		public final Class<?>				valueClass;
		public final Class<? extends Node>	nodeClass;

		/**
		 * @param valueClass
		 * @param nodeClass
		 */
		private FormFieldType(Class<?> valueClass, Class<? extends Node> nodeClass) {
			this.valueClass = valueClass;
			this.nodeClass = nodeClass;
		}
	}

	/**
	 * Interface which marks an Validator
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 5, 2015
	 * @param <T>
	 *            the {@link Node} type this validator is for
	 */
	public static interface FormFieldValidator<T extends Node> {
		/**
		 * Answers the question if the current instance is valid.
		 * 
		 * @param type
		 * @param inst
		 * @return true if valid, false otherwise
		 */
		public boolean valid(FormFieldType type, T inst);
	}

	/**
	 * A required validator for Form Fields.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 5, 2015
	 * @param <T>
	 *            the {@link Node} type fo the to validate node
	 */
	public static class RequiredFormFieldValidator<T extends Node> implements FormFieldValidator<T> {

		@Override
		public boolean valid(FormFieldType type, T node) {
			final Object value = getFormFieldValue(type, node);
			if (value == null) {
				return Boolean.FALSE;
			}
			if (value instanceof String) {
				return !((String) value).trim()
										.isEmpty();
			}
			return Boolean.TRUE;
		}
	}

	/**
	 * Creates a node regarding the given {@link FormFieldType}
	 * 
	 * @param type
	 *            the type of the form field
	 * @return the new corresponding node instance
	 */
	public static Node create(final FormFieldType type) {
		Objects.requireNonNull(type);

		switch (type) {
		case INPUT_TEXT:
			return new TextField();
		case DATE_PICKER:
			return new DatePicker();
		case DECIMAL:
			return new TextField();
		case SELECT:
			return new ChoiceBox<>();
		default:
			throw new IllegalArgumentException("FormFieldType: '" + type.name() + "' is not managed here");
		}
	}

	/**
	 * Gets the node value.
	 * 
	 * @param type
	 *            the type of the form field
	 * @param node
	 *            the node to get value from
	 * 
	 * @return the node's value
	 * @throws NullPointerException
	 *             if the given node is null
	 * @throws IllegalArgumentException
	 *             if the node is of an unmanaged type
	 */
	public static Object getFormFieldValue(FormFieldType type, final Node node) {
		Objects.requireNonNull(node, "Need node to get its value");

		switch (type) {
		case INPUT_TEXT:
			return ((TextField) node).getText();
		case DATE_PICKER:
			final DatePicker picker = (DatePicker) node;
			final Calendar cal;
			LocalDate localDate = picker.getValue();
			if (localDate != null) {
				Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
				cal = Calendar.getInstance();
				cal.setTime(Date.from(instant));
			} else {
				cal = null;
			}
			return cal;
		case DECIMAL:
			final String value = ((TextField) node).getText();
			if ((value != null) && (!value.trim()
											.isEmpty())) {
				return new BigDecimal(value);
			}
			return (BigDecimal) null;
		case SELECT:
			final ChoiceBox<?> box = (ChoiceBox<?>) node;
			return box.getSelectionModel()
						.getSelectedItem();
		default:
			break;
		}
		throw new IllegalArgumentException("Nod of type '" + node.getClass()
																	.getName() + "' unknown");
	}

	/**
	 * Resets the node value by setting it to null.
	 * 
	 * @param type
	 *            the form field type
	 * @param node
	 *            the node to be reset
	 * @see FormFieldType#setFormValue(Node, Object)
	 */
	public static void resetFormValue(final FormFieldType type, final Node node) {
		setFormValue(type, node, null);
	}

	/**
	 * Sets a value on the given node.
	 * 
	 * @param type
	 *            the form field type
	 * @param node
	 *            the node to set value on
	 * @param value
	 *            the value to be set. (Model held type)
	 * @throws NullPointerException
	 *             if the given node is null
	 * @throws IllegalArgumentException
	 *             if the node is of an unmanaged type
	 */
	public static void setFormValue(final FormFieldType type, final Node node, final Object value) {
		Objects.requireNonNull(node, "Need node to get its value");

		switch (type) {

		case INPUT_TEXT:
			((TextField) node).setText((String) value);
			return;

		case DATE_PICKER:
			final LocalDate localDate;
			if (value != null) {
				Date date = new Date();
				Instant instant = date.toInstant();
				localDate = instant.atZone(ZoneId.systemDefault())
									.toLocalDate();
			} else {
				localDate = null;
			}
			((DatePicker) node).setValue(localDate);
			return;
		case DECIMAL:
			String formatted = null;
			if (value != null) {
				final NumberFormat nf = NumberFormat.getCurrencyInstance();
				nf.setMaximumFractionDigits(2);
				nf.setMinimumFractionDigits(1);
				nf.setRoundingMode(RoundingMode.UNNECESSARY);
				nf.setMinimumIntegerDigits(1);
				nf.setMaximumFractionDigits(3);
				formatted = nf.format(((BigDecimal) value).doubleValue());
				formatted = new BigDecimalStringConverter().toString((BigDecimal) value);
			}
			((TextField) node).setText(formatted);
			return;
		case SELECT:
			final ChoiceBox<Object> box = (ChoiceBox<Object>) node;
			box.getSelectionModel()
				.select(box.getItems()
							.indexOf(value));
			return;
		default:
		}
		throw new IllegalArgumentException("Nod of type '" + node.getClass()
																	.getName() + "' unknown");
	}
}
