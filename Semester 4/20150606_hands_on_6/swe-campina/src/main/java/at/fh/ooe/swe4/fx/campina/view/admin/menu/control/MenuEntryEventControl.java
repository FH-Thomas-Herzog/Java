package at.fh.ooe.swe4.fx.campina.view.admin.menu.control;

import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import at.fh.ooe.swe4.fx.campina.jpa.EntityCache;
import at.fh.ooe.swe4.fx.campina.jpa.Menu;
import at.fh.ooe.swe4.fx.campina.jpa.MenuEntry;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.model.MenuEntryModel;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.part.MenuTabViewHandler;
import at.fh.ooe.swe4.fx.campina.view.api.FormContext;

/**
 * The event handler for the {@link MenuEntry} entity.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class MenuEntryEventControl {

	public MenuEntryEventControl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Saves a {@link MenuEntry}
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void saveMenuEntry(final ActionEvent event) {

	}

	/**
	 * Deletes a {@link MenuEntry}
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void deleteMenuEntry(final ActionEvent event) {

	}

	/**
	 * Handles the {@link MenuEntry} reload
	 */
	public void handleMenuEntryLoad(final FormContext<MenuEntryModel> ctx) {
		Objects.requireNonNull(ctx);

		final ObservableList<MenuEntryModel> list = (ObservableList<MenuEntryModel>) ctx.getObserable(MenuTabViewHandler.MENU_ENTRY_SELECTION_KEY);
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

	/**
	 * Handles the {@link Menu} reload.
	 * 
	 * @param ctx
	 *            teh form context
	 */
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
