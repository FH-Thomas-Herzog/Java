package at.fh.ooe.swe4.fx.campina.component.builder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import at.fh.ooe.swe4.fx.campina.view.FormComponentModel;
import at.fh.ooe.swe4.fx.campina.view.api.FormComponentSpec;

public class FormFieldBuilder {

	private List<FormComponentSpec>	formSpec;

	public FormFieldBuilder() {
		super();
	}

	public FormFieldBuilder init(List<FormComponentSpec> formSpec) {
		Objects.requireNonNull(formSpec, "Need an list of FormFields provided");

		this.formSpec = formSpec;

		return this;
	}

	public FormFieldBuilder end() {
		this.formSpec = null;

		return this;
	}

	public List<FormComponentModel> toNodeList() {
		final List<FormComponentModel> fields = new ArrayList<>();

		for (FormComponentSpec field : formSpec) {
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

			fields.add(new FormComponentModel(label, node, fieldMessage));

		}

		return fields;
	}
}
