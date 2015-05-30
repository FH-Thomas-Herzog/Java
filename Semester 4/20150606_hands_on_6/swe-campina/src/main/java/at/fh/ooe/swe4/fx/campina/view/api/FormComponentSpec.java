package at.fh.ooe.swe4.fx.campina.view.api;

import at.fh.ooe.swe4.fx.campina.view.tab.UserTab.FormFieldType;

public interface FormComponentSpec extends Component {

	public String getLabel();

	public Object getDefaultValue();

	public FormFieldType getFormFieldType();
}
