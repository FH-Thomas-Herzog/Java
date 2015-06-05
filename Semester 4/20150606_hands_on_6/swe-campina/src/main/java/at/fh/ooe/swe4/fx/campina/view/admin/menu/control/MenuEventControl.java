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
			if (ctx.model.getId() != null) {
				// TODO: update on database
			} else {
				final Menu menu = ctx.model.getEntity();
				menu.setId(EntityCache.menuCache.size() + 1);
				EntityCache.menuCache.add(menu);
			}
			((ChoiceBox<MenuModel>) ctx.getNode(MenuTabViewHandler.MENU_SELECTION_KEY)).getSelectionModel()
																			.select(ctx.model);
		} else {
			populateFormMessage("Formular ungültig !! Bitte Eingaben prüfen", ctx);
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
			EntityCache.menuCache.remove(ctx.model.getEntity());
			ctx.formHandler.resetForm(ctx);
			ctx.model.reset();
			ctx.formHandler.fillForm(ctx);
		}
		((ChoiceBox<MenuModel>) ctx.getNode(MenuTabViewHandler.MENU_SELECTION_KEY)).getSelectionModel()
																		.select(ctx.model);

	}

	/**
	 * The handles the load of the menus
	 * 
	 * @param ctx
	 *            the form context
	 */
	public void handleMenuLoad(final FormContext<MenuModel> ctx) {
		Objects.requireNonNull(ctx);

		final ObservableList<MenuModel> list = (ObservableList<MenuModel>) ctx.getObserable(MenuTabViewHandler.MENU_SELECTION_KEY);
		list.clear();
		list.add(new MenuModel());
		for (Menu menu : EntityCache.menuCache) {
			final MenuModel model = new MenuModel();
			model.prepare(menu);
			list.add(model);
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
