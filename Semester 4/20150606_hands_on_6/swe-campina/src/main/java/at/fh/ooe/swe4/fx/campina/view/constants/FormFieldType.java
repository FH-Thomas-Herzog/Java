package at.fh.ooe.swe4.fx.campina.view.constants;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public enum FormFieldType {
	INPUT_TEXT(String.class, TextField.class),
	DATE_PICKER(Calendar.class, DatePicker.class);

	public final Class<?>				valueClass;
	public final Class<? extends Node>	nodeClass;

	private FormFieldType(Class<?> valueClass, Class<? extends Node> nodeClass) {
		this.valueClass = valueClass;
		this.nodeClass = nodeClass;
	}

	public Node create() {
		switch (this) {
		case INPUT_TEXT:
			return new TextField();
		case DATE_PICKER:
			return new DatePicker();
		default:
			throw new IllegalArgumentException("FormFieldType: '" + this.name() + "' is not managed here");
		}
	}

	/**
	 * Gets the node value.
	 * 
	 * @param node
	 *            the node to get value from
	 * @return the node's value
	 * @throws NullPointerException
	 *             if the given node is null
	 * @throws IllegalArgumentException
	 *             if the node is of an unmanaged type
	 */
	public static Object getFormFieldValue(final Node node) {
		Objects.requireNonNull(node, "Need node to get its value");

		if (node instanceof TextField) {
			return ((TextField) node).getText();
		}
		if (node instanceof DatePicker) {
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
	public static void resetFormValue(final Node node) {
		setFormValue(node, null);
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
	public static void setFormValue(final Node node, final Object value) {
		Objects.requireNonNull(node, "Need node to get its value");

		if (node instanceof TextField) {
			((TextField) node).setText((String) value);
			return;
		}
		if (node instanceof DatePicker) {
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
		}
		throw new IllegalArgumentException("Nod of type '" + node.getClass()
																	.getName() + "' unknown");
	}
}
