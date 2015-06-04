package at.fh.ooe.swe4.fx.campina.view.admin.menu.control;

import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import at.fh.ooe.swe4.fx.campina.jpa.EntityCache;
import at.fh.ooe.swe4.fx.campina.jpa.Menu;
import at.fh.ooe.swe4.fx.campina.jpa.MenuEntry;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.model.MenuEntryModel;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.model.MenuModel;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.tab.MenuTab;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;

public class MenuEntryFormControl {

	public MenuEntryFormControl() {
		// TODO Auto-generated constructor stub
	}

	public void saveMenuEntry(final ActionEvent event) {

	}

	public void deleteMenuEntry(final ActionEvent event) {

	}

	public void handleMenuEntryLoad(final FormContext<MenuEntryModel> ctx) {
		Objects.requireNonNull(ctx);

		final ObservableList<MenuEntryModel> list = (ObservableList<MenuEntryModel>) ctx.getObserable(MenuTab.MENU_ENTRY_SELECTION_KEY);
		list.clear();
		list.add(new MenuEntryModel());
		for (Menu menu : EntityCache.menuCache) {
			for (MenuEntry entry : menu.getEntries()) {
				final MenuEntryModel model = new MenuEntryModel();
				model.prepare(entry);
				list.add(model);
			}
		}
	}

	public void handleMenuLoad(final FormContext<MenuEntryModel> ctx) {
		Objects.requireNonNull(ctx);

		ctx.model.getMenus()
					.clear();
		ctx.model.getMenus()
					.add(new Menu());
		for (Menu menu : EntityCache.menuCache) {
			ctx.model.getMenus()
						.add(menu);
		}
	}
}
