package at.fh.ooe.swe4.fx.campina.view.admin.menu.model;

import java.util.Arrays;
import java.util.List;

import javafx.util.StringConverter;
import at.fh.ooe.swe4.fx.campina.jpa.Menu;
import at.fh.ooe.swe4.fx.campina.jpa.conctants.Day;
import at.fh.ooe.swe4.fx.campina.view.annotation.FormField;
import at.fh.ooe.swe4.fx.campina.view.annotation.SelectFormField;
import at.fh.ooe.swe4.fx.campina.view.constants.FormFieldType;
import at.fh.ooe.swe4.fx.campina.view.model.AbstractModel;

public class MenuModel extends AbstractModel<Integer, Menu> {

	private final List<Day>	days	= Arrays.asList(Day.values());

	public static class DayConverter extends StringConverter<Day> {

		private static final String	PLEASE_CHOOSE	= "Bitte wählen";

		public DayConverter() {
			super();
		}

		@Override
		public String toString(Day object) {
			return (object == null || (Day.UNSET.equals(object))) ? PLEASE_CHOOSE : object.label;
		}

		@Override
		public Day fromString(String string) {
			return (PLEASE_CHOOSE.equals(string)) ? null : Day.fromLabel(string);
		}

	}

	@Override
	public void reset() {
		prepare(new Menu());
	}

	@FormField(id = "menu-label",
			label = "Label",
			ordinal = 1,
			required = true,
			requiredMessage = "Bitte Menu Label angeben",
			type = FormFieldType.INPUT_TEXT)
	public String getLabel() {
		return getEntity().getLabel();
	}

	public void setLabel(String label) {
		getEntity().setLabel(label);
	}

	@FormField(id = "menu-day",
			label = "Tag",
			ordinal = 6,
			required = true,
			requiredMessage = "Bitte Tag wählen",
			type = FormFieldType.SELECT)
	public Day getDay() {
		return Day.UNSET;
	}

	public void setLabel(Day day) {
		System.out.println(day);
	}

	@SelectFormField(target = "day",
			converter = DayConverter.class)
	public List<Day> getDays() {
		return days;
	}
}
