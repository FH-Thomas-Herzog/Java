package at.fh.ooe.swe4.campina.fx.view.admin.menu.control;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import at.fh.ooe.swe4.campina.dao.api.MenuDao;
import at.fh.ooe.swe4.campina.dao.api.MenuEntryDao;
import at.fh.ooe.swe4.campina.fx.rmi.service.locator.DaoLocator;
import at.fh.ooe.swe4.campina.fx.view.admin.menu.model.MenuEntryModel;
import at.fh.ooe.swe4.campina.fx.view.admin.menu.part.MenuTabViewHandler;
import at.fh.ooe.swe4.campina.fx.view.api.FormContext;
import at.fh.ooe.swe4.campina.persistence.api.entity.Menu;
import at.fh.ooe.swe4.campina.persistence.api.entity.MenuEntry;

/**
 * The event handler for the {@link MenuEntry} entity.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class MenuEntryEventControl {

	private final MenuEntryDao	dao		= DaoLocator.getDao(MenuEntryDao.class);
	private final MenuDao		menuDao	= DaoLocator.getDao(MenuDao.class);
	private static final Logger	log		= Logger.getLogger(MenuEntryEventControl.class);

	public MenuEntryEventControl() {
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
			try {
				final MenuEntry menuEntry = dao.save(ctx.model.getEntity());
				ctx.model.prepare(dao.byId(menuEntry.getId()));
				ctx.formHandler.fillForm(ctx);
			} catch (RemoteException e) {
				log.error("Menu entry saving failed", e);
				populateFormMessage("Menu Eintrag konnte nicht gespeichert werden", ctx);
			}
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
			try {
				dao.delete(ctx.model.getEntity());
				ctx.model.reset();
				ctx.formHandler.fillForm(ctx);
			} catch (RemoteException e) {
				log.error("Could not delete menu entry", e);
				populateFormMessage("Konnte Menu Enintrag nicht löschen", ctx);
			}
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
		List<MenuEntry> entries = new ArrayList<>();
		boolean found = Boolean.FALSE;
		try {
			entries = dao.getAll();
			for (MenuEntry menuEntry : entries) {
				if (menuEntry.equals(ctx.model.getEntity())) {
					found = Boolean.TRUE;
				}
				final MenuEntryModel model = new MenuEntryModel();
				model.prepare(menuEntry);
				list.add(model);
			}
		} catch (RemoteException e) {
			log.error("Could ot load all menu entries", e);
		}

		if (!found) {
			ctx.model.reset();
			ctx.formHandler.fillForm(ctx);
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
		try {
			ctx.model.getMenus()
						.addAll(menuDao.getAll());
			ctx.model.getMenus()
						.set(ctx.model.getMenus()
										.indexOf(ctx.model.getMenu()), ctx.model.getMenu());
		} catch (RemoteException e) {
			log.error("Cannot load referencing menu entry menus", e);
		}
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
