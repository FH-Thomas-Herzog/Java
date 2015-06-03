package at.fh.ooe.swe4.fx.campina.view.util;

import java.util.Objects;

import at.fh.ooe.swe4.fx.campina.view.constants.FormFieldType;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;
import at.fh.ooe.swe4.fx.campina.view.model.AbstractModel;
import javafx.scene.Node;

public class FormUtils {

	public FormUtils() {
	}

	public static interface Validator<T extends Node> {
		public boolean valid(T inst);
	}

	public static class RequiredValidator<T extends Node> implements Validator<T> {
		@Override
		public boolean valid(T node) {
			final Object value = FormFieldType.getFormFieldValue(node);
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

}
