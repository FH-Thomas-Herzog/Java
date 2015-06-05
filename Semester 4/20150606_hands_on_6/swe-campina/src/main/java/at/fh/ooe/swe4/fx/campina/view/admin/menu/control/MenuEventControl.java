package at.fh.ooe.swe4.fx.campina.view.admin.menu.control;

import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import org.apache.commons.lang.StringUtils;

import at.fh.ooe.swe4.fx.campina.jpa.EntityCache;
import at.fh.ooe.swe4.fx.campina.jpa.Menu;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.model.MenuModel;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.part.MenuTabViewHandler;
import at.fh.ooe.swe4.fx.campina.view.admin.user.model.UserModel;
import at.fh.ooe.swe4.fx.campina.view.api.FormContext;

/**
 * The event control for the {@link Menu} entity.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class MenuEventControl {

	public MenuEventControl() {
		// TODO Auto-generated constructor stub
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
			final Menu menu;
			if (ctx.model.getId() != null) {
				menu = ctx.model.getEntity();
				EntityCache.menuCache.add(menu);
			} else {
				menu = ctx.model.getEntity();
				menu.setId(EntityCache.menuCache.size() + 1);
				EntityCache.menuCache.add(menu);
			}
			ctx.model.prepare(menu);
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
			ctx.getNode(MenuTabViewHandler.MENU_DELETE_BUTTON_ID)
				.setVisible(Boolean.TRUE);
			final Menu menu = ctx.model.getEntity();
			EntityCache.menuCache.remove(menu);
			EntityCache.deleteForMenuId(ctx.model.getEntity()
													.getId());
			ctx.formHandler.resetForm(ctx);
			ctx.model.reset();
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
		for (Menu menu : EntityCache.menuCache) {
			final MenuModel model = new MenuModel();
			model.prepare(menu);
			list.add(model);
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
