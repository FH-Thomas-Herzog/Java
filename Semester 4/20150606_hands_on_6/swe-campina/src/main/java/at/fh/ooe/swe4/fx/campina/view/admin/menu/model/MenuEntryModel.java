package at.fh.ooe.swe4.fx.campina.view.admin.menu.model;

import java.math.BigDecimal;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import at.fh.ooe.swe4.fx.campina.jpa.Menu;
import at.fh.ooe.swe4.fx.campina.jpa.MenuEntry;
import at.fh.ooe.swe4.fx.campina.view.annotation.FormField;
import at.fh.ooe.swe4.fx.campina.view.annotation.SelectFormField;
import at.fh.ooe.swe4.fx.campina.view.model.AbstractModel;
import at.fh.ooe.swe4.fx.campina.view.util.FormUtils.FormFieldType;

public class MenuEntryModel extends AbstractModel<Integer, MenuEntry> {

	public ObservableList<Menu>	menus;

	public static class MenuConverter extends StringConverter<Menu> {

		@Override
		public String toString(Menu object) {
			return (object.getId() == null) ? "Bitte Wählen" : object.getLabel();
		}

		@Override
		public Menu fromString(String string) {
			throw new UnsupportedOperationException("From text not supported by this converter");
		}

	}

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

	@Override
	public void reset() {
		final MenuEntry entry = new MenuEntry();
		entry.setPrice(BigDecimal.ZERO);
		entry.setMenu(new Menu());
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
			type = FormFieldType.SELECT)
	public Menu getMenu() {
		return getEntity().getMenu();
	}

	public void setMenu(Menu menu) {
		getEntity().setMenu(menu);
	}

	@SelectFormField(target = "menu",
			converter = MenuConverter.class)
	public ObservableList<Menu> getMenus() {
		return menus;
	}
}
