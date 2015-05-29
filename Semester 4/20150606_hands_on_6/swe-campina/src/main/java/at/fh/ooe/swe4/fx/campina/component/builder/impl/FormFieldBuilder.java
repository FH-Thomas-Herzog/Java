package at.fh.ooe.swe4.fx.campina.component.builder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import at.fh.ooe.swe4.fx.campina.view.tab.UserTab.FormField;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormFieldBuilder {

	private List<FormField>	formSpec;

	public static class FormFieldModel {

		public final Label		label;
		public final Node		field;
		public final TextField	errorMessage;

		public FormFieldModel(Label label, Node field, TextField errorMessage) {
			super();
			this.label = label;
			this.field = field;
			this.errorMessage = errorMessage;
		}
	}

	public FormFieldBuilder() {
		super();
	}

	public FormFieldBuilder init(List<FormField> formSpec) {
		Objects.requireNonNull(formSpec, "Need an list of FormFields provided");

		this.formSpec = formSpec;

		return this;
	}

	public FormFieldBuilder end() {
		this.formSpec = null;

		return this;
	}

	public List<FormFieldModel> toNodeList() {
		final List<FormFieldModel> fields = new ArrayList<>();

		for (FormField field : formSpec) {
			final Label label;
			final Node node;
			final TextField fieldMessage;

			// current form field
			node = field.getFormFieldType()
						.create();

			// Create the form field
			fieldMessage = new TextField();
			fieldMessage.setId(node.getId() + "-message");
			fieldMessage.setEditable(Boolean.FALSE);
			fieldMessage.setVisible(Boolean.FALSE);

			// create label for form field
			label = new Label(field.getLabel());
			label.setId(field.getId() + "-label");

			// set id on field
			node.setId(field.getId());

			// TODO: Register form field events

			fields.add(new FormFieldModel(label, node, fieldMessage));

		}

		return fields;
	}
}
