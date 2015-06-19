package at.fh.ooe.swe4.campina.fx.view.admin.menu.control;

import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import org.apache.commons.lang.StringUtils;

import at.fh.ooe.swe4.campina.fx.view.admin.menu.model.MenuEntryModel;
import at.fh.ooe.swe4.campina.fx.view.admin.menu.part.MenuTabViewHandler;
import at.fh.ooe.swe4.campina.fx.view.api.FormContext;
import at.fh.ooe.swe4.campina.persistence.api.MenuEntry;

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
	 * Handles the new action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void newAction(final ActionEvent event) {
		final FormContext<MenuEntryModel> ctx = (FormContext<MenuEntryModel>) ((Node) event.getSource()).getUserData();
		// clear former set message
		populateFormMessage(null, ctx);
		// reset form
		ctx.formHandler.resetForm(ctx);
		// create new user model with new user entity
		ctx.model.reset();
		// hide buttons
		ctx.getNode(MenuTabViewHandler.MENU_ENTRY_DELETE_BUTTON_ID)
			.setVisible(Boolean.FALSE);
		// reload users
		handleMenuEntryReload(ctx);
	}

	/**
	 * Saves a {@link MenuEntry}
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void saveMenuEntry(final ActionEvent event) {
		final FormContext<MenuEntryModel> ctx = (FormContext<MenuEntryModel>) ((Node) event.getSource()).getUserData();
		ctx.getNode(MenuTabViewHandler.MENU_ENTRY_DELETE_BUTTON_ID)
			.setVisible(Boolean.TRUE);
		ctx.formHandler.validateForm(ctx);
		ctx.formHandler.fillModel(ctx);

		if (ctx.valid) {
			final MenuEntry entry;
			if (ctx.model.getId() != null) {
				entry = ctx.model.getEntity();
				EntityCache.menuEntryCache.add(entry);
				EntityCache.byMenuId(entry.getMenu()
											.getId())
							.getEntries()
							.add(entry);
			} else {
				entry = ctx.model.getEntity();
				entry.setId(EntityCache.menuEntryCache.size() + 1);
				entry.setOrdinal(entry.getId());
				EntityCache.menuEntryCache.add(entry);
			}
			ctx.model.prepare(entry);
		} else {
			populateFormMessage("Formular ungültig !! Bitte Eingaben prüfen", ctx);
		}
		handleMenuEntryReload(ctx);
	}

	/**
	 * Deletes a {@link MenuEntry}
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void deleteMenuEntry(final ActionEvent event) {
		final FormContext<MenuEntryModel> ctx = (FormContext<MenuEntryModel>) ((Node) event.getSource()).getUserData();

		if (ctx.model.getId() != null) {
			ctx.getNode(MenuTabViewHandler.MENU_ENTRY_DELETE_BUTTON_ID)
				.setVisible(Boolean.TRUE);
			final MenuEntry entry = ctx.model.getEntity();
			EntityCache.menuEntryCache.remove(entry);
			EntityCache.byMenuId(ctx.model.getEntity()
											.getMenu()
											.getId())
						.getEntries()
						.remove(entry);
			ctx.formHandler.resetForm(ctx);
			ctx.model.reset();
		}
		handleMenuEntryReload(ctx);
	}

	/**
	 * Handles the {@link MenuEntry} reload
	 */
	public void handleMenuEntryReload(final FormContext<MenuEntryModel> ctx) {
		Objects.requireNonNull(ctx);

		final ObservableList<MenuEntryModel> list = (ObservableList<MenuEntryModel>) ctx.getObserable(MenuTabViewHandler.MENU_ENTRY_SELECTION_KEY);
		list.clear();
		list.add(new MenuEntryModel());
		for (MenuEntry entry : EntityCache.menuEntryCache) {
			final MenuEntryModel model = new MenuEntryModel();
			model.prepare(entry);
			list.add(model);
		}

		// need to replace observed instance
		list.set(list.indexOf(ctx.model), ctx.model);

		// need to select current context hold model
		((ChoiceBox<MenuEntryModel>) ctx.getNode(MenuTabViewHandler.MENU_ENTRY_SELECTION_KEY)).getSelectionModel()
																								.select(ctx.model);
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
					.add(null);
		for (Menu menu : EntityCache.menuCache) {
			ctx.model.getMenus()
						.add(menu);
		}

		ctx.model.getMenus()
					.set(ctx.model.getMenus()
									.indexOf(ctx.model.getMenu()), ctx.model.getMenu());
	}

	/**
	 * Populates a message to the message box. <br>
	 * If message is null the actual set message will be cleared
	 * 
	 * @param message
	 *            the message to populate
	 * @param ctx
	 *            the form context
	 */
	private void populateFormMessage(final String message, final FormContext<MenuEntryModel> ctx) {
		final TextFlow flow = ((TextFlow) ctx.getNode(MenuTabViewHandler.MENU_ENTRY_FORM_MESSAGE));
		flow.getChildren()
			.clear();
		flow.setPrefHeight(0);
		if (!StringUtils.isEmpty(message)) {
			flow.getChildren()
				.add(new Text(message));
			flow.setPrefHeight(30);
		}
	}
}
