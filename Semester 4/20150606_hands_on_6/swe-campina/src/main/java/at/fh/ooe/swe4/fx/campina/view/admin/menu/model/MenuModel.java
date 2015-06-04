package at.fh.ooe.swe4.fx.campina.view.admin.menu.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import at.fh.ooe.swe4.fx.campina.jpa.Menu;
import at.fh.ooe.swe4.fx.campina.jpa.MenuEntry;
import at.fh.ooe.swe4.fx.campina.jpa.conctants.Day;
import at.fh.ooe.swe4.fx.campina.view.annotation.FormField;
import at.fh.ooe.swe4.fx.campina.view.annotation.SelectFormField;
import at.fh.ooe.swe4.fx.campina.view.constants.FormFieldType;
import at.fh.ooe.swe4.fx.campina.view.model.AbstractModel;

public class MenuModel extends AbstractModel<Integer, Menu> {

	private final ObservableList<Day>	days	= FXCollections.observableArrayList(Arrays.asList(Day.values()));

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

	public static class MenuModelConverter extends StringConverter<MenuModel> {

		@Override
		public String toString(MenuModel object) {
			Objects.requireNonNull(object);
			return (object.getId() == null) ? "Neues Menu" : object.getLabel();
		}

		@Override
		public MenuModel fromString(String string) {
			throw new UnsupportedOperationException("Form text not supported by this converter");
		}

	}

	@Override
	public void reset() {
		prepare(new Menu());
	}

	@Override
	public void prepare(Menu entity) {
		super.prepare(entity);
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

	public SortedSet<MenuEntry> getMenuEntries() {
		return getEntity().getEntries();
	}

}
