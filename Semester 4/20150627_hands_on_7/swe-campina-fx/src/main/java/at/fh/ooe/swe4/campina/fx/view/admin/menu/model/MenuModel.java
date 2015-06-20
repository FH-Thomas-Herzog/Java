package at.fh.ooe.swe4.campina.fx.view.admin.menu.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import at.fh.ooe.swe4.campina.fx.view.annotation.FormField;
import at.fh.ooe.swe4.campina.fx.view.annotation.SelectFormField;
import at.fh.ooe.swe4.campina.fx.view.api.AbstractViewModel;
import at.fh.ooe.swe4.campina.fx.view.form.FormUtils.FormFieldType;
import at.fh.ooe.swe4.campina.persistence.api.Menu;
import at.fh.ooe.swe4.campina.persistence.constants.Day;

/**
 * The view model which backs the {@link Menu} entity.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class MenuModel extends AbstractViewModel<Integer, Menu> {

	private static final ObservableList<Day>	days	= FXCollections.observableArrayList();

	static {
		days.add((Day) null);
		days.addAll(Arrays.asList(Day.values()));
	}

	/**
	 * The converter for the {@link Day} type.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 5, 2015
	 */
	public static class DayConverter extends StringConverter<Day> {

		private static final String	PLEASE_CHOOSE	= "Bitte wählen";

		public DayConverter() {
			super();
		}

		@Override
		public String toString(Day object) {
			return (object == null) ? PLEASE_CHOOSE : object.label;
		}

		@Override
		public Day fromString(String string) {
			return (PLEASE_CHOOSE.equals(string)) ? null : Day.fromLabel(string);
		}

	}

	/**
	 * The converter fro the {@link MenuModel} type.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 5, 2015
	 */
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

	/**
	 * 
	 */
	public MenuModel() {
		super();
	}

	/**
	 * @param entity
	 */
	public MenuModel(Menu entity) {
		super(entity);
	}

	@Override
	public void reset() {
		prepare(new Menu());
	}

	@Override
	public void prepare(Menu entity) {
		super.prepare(entity);
		entity.setDay(days.get(days.indexOf(entity.getDay())));
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
			type = FormFieldType.SELECT,
			valueClass = Day.class)
	public Day getDay() {
		return getEntity().getDay();
	}

	public void setDay(Day day) {
		getEntity().setDay(day);
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
