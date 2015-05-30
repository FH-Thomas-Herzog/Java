package at.fh.ooe.swe4.fx.campina.view.tab;

import javafx.scene.control.TextField;

public class UserTab {

	public static interface Node {

		public String getId();
	}

	public static interface FormField extends Node {

		public String getLabel();

		public Object getDefaultValue();

		public FormFieldType getFormFieldType();
	}

	public static enum FormFieldType {
		INPUT_TEXT;

		public <T extends Node> T create() {
			switch (this) {
			case INPUT_TEXT:
				return (T) new TextField();
			default:
				throw new IllegalArgumentException("FormFieldType: '" + this.name() + "' is not managed here");
			}
		}
	}

	private static enum UserForm implements FormField {
		NAME("user-name", "Name", null, Boolean.TRUE, FormFieldType.INPUT_TEXT);

		public final String			id;
		public final String			label;
		public final FormFieldType	type;
		public final Object			defaultValue;
		public final boolean		required;

		private UserForm(String id, String label, Object defaultValue, boolean required, FormFieldType type) {
			this.id = id;
			this.label = label;
			this.defaultValue = defaultValue;
			this.required = required;
			this.type = type;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public String getLabel() {
			return label;
		}

		@Override
		public Object getDefaultValue() {
			return defaultValue;
		}

		@Override
		public FormFieldType getFormFieldType() {
			return type;
		}

	}

	public UserTab() {
		// TODO Auto-generated constructor stub
	}

}