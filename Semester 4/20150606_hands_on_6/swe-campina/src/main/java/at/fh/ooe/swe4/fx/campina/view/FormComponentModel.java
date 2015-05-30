package at.fh.ooe.swe4.fx.campina.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormComponentModel {

	public final Label		label;
	public final Node		field;
	public final TextField	errorMessage;

	public FormComponentModel(Label label, Node field, TextField errorMessage) {
		super();
		this.label = label;
		this.field = field;
		this.errorMessage = errorMessage;
	}
}
