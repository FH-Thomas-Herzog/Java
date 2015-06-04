package at.fh.ooe.swe4.fx.campina.view.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import at.fh.ooe.swe4.fx.campina.view.api.IdHolder;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.converter.BigDecimalStringConverter;

public class FormUtils {

	private FormUtils() {
	}

	public enum FormFieldType {
		INPUT_TEXT(String.class, TextField.class),
		DATE_PICKER(Calendar.class, DatePicker.class),
		DECIMAL(BigDecimal.class, TextField.class),
		SELECT(Object.class, ChoiceBox.class);

		public final Class<?>				valueClass;
		public final Class<? extends Node>	nodeClass;

		private FormFieldType(Class<?> valueClass, Class<? extends Node> nodeClass) {
			this.valueClass = valueClass;
			this.nodeClass = nodeClass;
		}
	}

	public static interface Validator<T extends Node> {
		public boolean valid(FormFieldType type, T inst);
	}

	public static class RequiredValidator<T extends Node> implements Validator<T> {
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
	 *            TODO
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
	 * Resets the node value by setting it to null
	 * 
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
				.select(value);
			return;
		default:
		}
		throw new IllegalArgumentException("Nod of type '" + node.getClass()
																	.getName() + "' unknown");
	}
}
