package at.fh.ooe.swe4.campina.fx.view.admin.menu.model;

import java.math.BigDecimal;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import at.fh.ooe.swe4.campina.fx.view.annotation.FormField;
import at.fh.ooe.swe4.campina.fx.view.annotation.SelectFormField;
import at.fh.ooe.swe4.campina.fx.view.api.AbstractViewModel;
import at.fh.ooe.swe4.campina.fx.view.form.FormUtils.FormFieldType;
import at.fh.ooe.swe4.campina.persistence.api.MenuEntry;

/**
 * The view model which backs the {@link MenuEntry} entity
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class MenuEntryModel extends AbstractViewModel<Integer, MenuEntry> {

	public ObservableList<Menu>	menus;

	/**
	 * The converter for the {@link Menu} type
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 5, 2015
	 */
	public static class MenuConverter extends StringConverter<Menu> {

		@Override
		public String toString(Menu object) {
			return (object == null) ? "Bitte Wählen" : object.getLabel();
		}

		@Override
		public Menu fromString(String string) {
			throw new UnsupportedOperationException("From text not supported by this converter");
		}

	}

	/**
	 * The converter for the {@link MenuEntryModel} type.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 5, 2015
	 */
	public static class MenuEntryModelConverter extends StringConverter<MenuEntryModel> {

		@Override
		public String toString(MenuEntryModel object) {
			Objects.requireNonNull(object);
			return (object.getId() == null) ? "Neuer Menu Eintrag" : object.getLabel();
		}

		@Override
		public MenuEntryModel fromString(String string) {
			throw new UnsupportedOperationException("Form text not supported by this converter");
		}

	}

	/**
	 * 
	 */
	public MenuEntryModel() {
		super();
	}

	/**
	 * @param entity
	 */
	public MenuEntryModel(MenuEntry entity) {
		super(entity);
	}

	@Override
	public void reset() {
		final MenuEntry entry = new MenuEntry();
		entry.setPrice(BigDecimal.ZERO);
		entry.setOrdinal(0);
		menus = FXCollections.observableArrayList();
		menus.add(new Menu());
		prepare(entry);

	}

	@FormField(id = "menu-entry-label",
			label = "Beschreibung",
			ordinal = 1,
			required = true,
			requiredMessage = "Bitte Beschreibung angeben",
			type = FormFieldType.INPUT_TEXT)
	public String getLabel() {
		return getEntity().getLabel();
	}

	public void setLabel(String label) {
		getEntity().setLabel(label);
	}

	@FormField(id = "menu-entry-price",
			label = "Preis",
			ordinal = 2,
			required = true,
			requiredMessage = "Bitte Preis angeben",
			type = FormFieldType.DECIMAL)
	public BigDecimal getPrice() {
		return getEntity().getPrice();
	}

	public void setPrice(BigDecimal price) {
		getEntity().setPrice(price);
	}

	@FormField(id = "menu-entry-menu",
			label = "Menu",
			ordinal = 3,
			required = true,
			requiredMessage = "Bitte Menu wählen",
			type = FormFieldType.SELECT,
			valueClass = Menu.class)
	public Menu getMenu() {
		return getEntity().getMenu();
	}

	public void setMenu(Menu menu) {
		getEntity().setMenu(menu);
		if (menu != null) {
			getEntity().setOrdinal(menu.getEntries()
										.size() + 1);
		}
	}

	@SelectFormField(target = "menu",
			converter = MenuConverter.class)
	public ObservableList<Menu> getMenus() {
		return menus;
	}
}
