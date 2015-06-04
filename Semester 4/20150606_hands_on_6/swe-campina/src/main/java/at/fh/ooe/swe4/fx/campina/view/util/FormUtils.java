package at.fh.ooe.swe4.fx.campina.view.util;

import javafx.scene.Node;
import at.fh.ooe.swe4.fx.campina.view.constants.FormFieldType;

public class FormUtils {

	public FormUtils() {
	}

	public static interface Validator<T extends Node> {
		public boolean valid(FormFieldType type, T inst);
	}

	public static class RequiredValidator<T extends Node> implements Validator<T> {
		@Override
		public boolean valid(FormFieldType type, T node) {
			final Object value = FormFieldType.getFormFieldValue(type, node);
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
