package at.fh.ooe.swe4.campina.fx.view.admin.menu.control;

import java.rmi.Naming;
import java.rmi.RemoteException;
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
import at.fh.ooe.swe4.campina.fx.view.admin.menu.model.MenuModel;
import at.fh.ooe.swe4.campina.fx.view.admin.menu.part.MenuTabViewHandler;
import at.fh.ooe.swe4.campina.fx.view.api.FormContext;
import at.fh.ooe.swe4.campina.persistence.api.Menu;
import at.fh.ooe.swe4.campina.rmi.api.factory.RmiServiceFactory;

/**
 * The event control for the {@link Menu} entity.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class MenuEventControl {

	private final MenuDao		dao;
	private static final Logger	log	= Logger.getLogger(MenuEventControl.class);

	public MenuEventControl() {
		try {
			final String name = "rmi://localhost:50555/" + RmiServiceFactory.class.getSimpleName();
			this.dao = ((RmiServiceFactory) Naming.lookup(name)).createService(MenuDao.class);
		} catch (Throwable e) {
			throw new IllegalStateException("Could not obtains MenuDao bean", e);
		}
	}

	/**
	 * Handles the new action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void newAction(final ActionEvent event) {
		final FormContext<MenuModel> ctx = (FormContext<MenuModel>) ((Node) event.getSource()).getUserData();
		// clear former set message
		populateFormMessage(null, ctx);
		// reset form
		ctx.formHandler.resetForm(ctx);
		// create new user model with new user entity
		ctx.model.reset();
		// hide buttons
		ctx.getNode(MenuTabViewHandler.MENU_DELETE_BUTTON_ID)
			.setVisible(Boolean.FALSE);
		// reload users
		handleMenuReload(ctx);
	}

	/**
	 * Saves a menu
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void saveMenu(final ActionEvent event) {
		final FormContext<MenuModel> ctx = (FormContext<MenuModel>) ((Node) event.getSource()).getUserData();
		ctx.getNode(MenuTabViewHandler.MENU_DELETE_BUTTON_ID)
			.setVisible(Boolean.TRUE);
		ctx.formHandler.validateForm(ctx);
		ctx.formHandler.fillModel(ctx);

		if (ctx.valid) {
			try {
				final Menu menu = dao.save(ctx.model.getEntity());
				ctx.model.prepare(dao.byId(menu.getId()));
				ctx.formHandler.fillForm(ctx);
			} catch (RemoteException e) {
				log.error("Could not save Menu", e);
			}
			handleMenuReload(ctx);
		} else {
			populateFormMessage("Formular ungültig !! Bitte Eingaben prüfen", ctx);
			handleMenuReload(ctx);
		}
	}

	/**
	 * Deletes an menu
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void deleteMenu(final ActionEvent event) {
		final FormContext<MenuModel> ctx = (FormContext<MenuModel>) ((Node) event.getSource()).getUserData();

		if (ctx.model.getId() != null) {
			try {
				dao.delete(ctx.model.getEntity());
			} catch (RemoteException e) {
				log.error("Could not delete menu", e);
			}
			ctx.model.reset();
			ctx.formHandler.fillForm(ctx);
		}
		handleMenuReload(ctx);
	}

	/**
	 * The handles the load of the menus. Resets the current ctx.model in the
	 * observed list and set this model as selected
	 * 
	 * @param ctx
	 *            the form context
	 */
	public void handleMenuReload(final FormContext<MenuModel> ctx) {
		Objects.requireNonNull(ctx);

		final ObservableList<MenuModel> list = (ObservableList<MenuModel>) ctx.getObserable(MenuTabViewHandler.MENU_SELECTION_KEY);
		list.clear();
		list.add(new MenuModel());

		boolean found = Boolean.FALSE;
		try {
			final List<Menu> menus = dao.getAll();
			for (Menu menu : menus) {
				if (menu.equals(ctx.model.getEntity())) {
					found = Boolean.TRUE;
				}
				final MenuModel model = new MenuModel();
				model.prepare(menu);
				list.add(model);
			}
		} catch (RemoteException e) {
			log.error("Could not load menus", e);
		}

		if (!found) {
			ctx.model.reset();
			ctx.formHandler.fillForm(ctx);
		}

		// need to replace observed instance
		list.set(list.indexOf(ctx.model), ctx.model);

		// need to select current context hold model
		((ChoiceBox<MenuModel>) ctx.getNode(MenuTabViewHandler.MENU_SELECTION_KEY)).getSelectionModel()
																					.select(ctx.model);
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
	private void populateFormMessage(final String message, final FormContext<MenuModel> ctx) {
		final TextFlow flow = ((TextFlow) ctx.getNode(MenuTabViewHandler.MENU_FORM_MESSAGE));
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
