package at.fh.ooe.swe4.fx.campina.view.admin.menu.control;

import java.util.Objects;

import javafx.collections.ObservableList;
import at.fh.ooe.swe4.fx.campina.jpa.EntityCache;
import at.fh.ooe.swe4.fx.campina.jpa.Menu;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.model.MenuModel;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.tab.MenuTab;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;

public class MenuFormControl {

	public MenuFormControl() {
		// TODO Auto-generated constructor stub
	}

	public void handleMenuLoad(final FormContext<MenuModel> ctx) {
		Objects.requireNonNull(ctx);

		final ObservableList<MenuModel> list = (ObservableList<MenuModel>) ctx.getObserable(MenuTab.MENU_SELECTION_KEY);
		list.clear();
		list.add(new MenuModel());
		for (Menu menu : EntityCache.menuCache) {
			final MenuModel model = new MenuModel();
			model.prepare(menu);
			list.add(model);
		}
	}
}
