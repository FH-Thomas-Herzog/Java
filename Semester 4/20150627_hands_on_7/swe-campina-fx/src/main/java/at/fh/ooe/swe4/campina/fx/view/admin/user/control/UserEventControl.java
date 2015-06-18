package at.fh.ooe.swe4.campina.fx.view.admin.user.control;

import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import org.apache.commons.lang.StringUtils;

import at.fh.ooe.swe4.campina.fx.view.admin.user.model.UserModel;
import at.fh.ooe.swe4.campina.fx.view.admin.user.part.UserTabviewHandler;
import at.fh.ooe.swe4.campina.fx.view.api.FormContext;
import at.fh.ooe.swe4.campina.jdbc.User;

/**
 * The control bean for the user tab.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 3, 2015
 */
public class UserEventControl {

	/**
	 * Creates test data since we have no back-end yet
	 */
	public UserEventControl() {
	}

	// #############################################################
	// Button controls
	// #############################################################
	/**
	 * Handles the new action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void handleNewAction(final ActionEvent event) {
		final FormContext<UserModel> ctx = (FormContext<UserModel>) ((Node) event.getSource()).getUserData();
		// clear former set message
		populateFormMessage(null, ctx);
		// reset form
		ctx.formHandler.resetForm(ctx);
		// create new user model with new user entity
		ctx.model.reset();
		// hide buttons
		setButtonVisibility(ctx, Boolean.FALSE);
		// reload users
		handleUserReload(ctx);
	}

	/**
	 * Handles the save action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void handleSaveAction(final ActionEvent event) {
		final FormContext<UserModel> ctx = (FormContext<UserModel>) ((Node) event.getSource()).getUserData();
		// clear former set messages
		populateFormMessage(null, ctx);
		// validate form
		ctx.formHandler.validateForm(ctx);
		// is valid
		if (ctx.valid) {
			// fill model with form data
			ctx.formHandler.fillModel(ctx);

			// TODO: Persist entity here
			final User user = ctx.model.getEntity();

			// if not already managed increase id by size + 1
			if (!EntityCache.userCache.contains(user)) {
				user.setId(EntityCache.userCache.size() + 1);
			} 
			// save model in backed list for testing
			EntityCache.userCache.add(user);
			// init model with new saved user
			ctx.model.prepare(user);
			// enable buttons
			setButtonVisibility(ctx, Boolean.TRUE);
			// reload data from db (now backing list)
			handleUserReload(ctx);
		} else {
			populateFormMessage("Formular ungültig !!! Bitte Eingaben prüfen", ctx);
		}
		event.consume();

	}

	/**
	 * Handles the delete action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void handleDeleteAction(final ActionEvent event) {
		final FormContext<UserModel> ctx = (FormContext<UserModel>) ((Node) event.getSource()).getUserData();
		// clear former set message
		populateFormMessage(null, ctx);
		// reset the form
		ctx.formHandler.resetForm(ctx);

		final UserModel model = ((ChoiceBox<UserModel>) ctx.getNode(UserTabviewHandler.USER_SELECTION_KEY)).getSelectionModel()
																											.getSelectedItem();

		// TODO: Delete entity from db here

		// existing user gets deleted
		if (model.getId() != null) {
			EntityCache.userCache.remove(model.getEntity());
		}

		// reset model
		ctx.model = new UserModel();
		// disable buttons
		setButtonVisibility(ctx, Boolean.FALSE);
		// reload users
		handleUserReload(ctx);
	}

	/**
	 * Handles the block action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void handleBlockAction(final ActionEvent event) {
		final FormContext<UserModel> ctx = (FormContext<UserModel>) ((Node) event.getSource()).getUserData();
		// clear old set message
		populateFormMessage(null, ctx);
		// selected user model
		final UserModel model = ((ChoiceBox<UserModel>) ctx.getNode(UserTabviewHandler.USER_SELECTION_KEY)).getSelectionModel()
																											.getSelectedItem();

		final Button blockButton = (Button) ctx.getNode(UserTabviewHandler.BLOCK_BUTTON_ID);
		final User user = model.getEntity();

		// invert user blocked state
		user.setBlockedFlag(!model.getEntity()
									.getBlockedFlag());

		ctx.model.prepare(user);
		ctx.formHandler.fillForm(ctx);

		// TODO: Update blocked flag on db

		// got blocked
		if (model.getEntity()
					.getBlockedFlag()) {
			blockButton.setText("Freigeben");
		}
		// got freed
		else {
			blockButton.setText("Blockieren");
		}
		// user reload
		handleUserReload(ctx);
	}

	// #############################################################
	// Selection controls
	// #############################################################
	public void handleUserSelection(final FormContext<UserModel> ctx, final UserModel user) {
		// clear former set message
		populateFormMessage(null, ctx);
		// Selection present
		if (user.getId() != null) {
			ctx.model.prepare(user.getEntity());
			ctx.formHandler.fillForm(ctx);
			setButtonVisibility(ctx, Boolean.TRUE);
		}
		// No selection present
		else {
			ctx.model.reset();
			ctx.formHandler.fillForm(ctx);
			setButtonVisibility(ctx, Boolean.FALSE);
		}
	}

	// #############################################################
	// Load controls
	// #############################################################
	/**
	 * Handles the load of the user for the selection
	 * 
	 * @param userList
	 *            the {@link ObservableList} to add users to
	 */
	public void handleUserReload(final FormContext<UserModel> ctx) {
		Objects.requireNonNull(ctx);

		final ObservableList<UserModel> userList = (ObservableList<UserModel>) ctx.getObserable(UserTabviewHandler.USER_SELECTION_KEY);
		userList.clear();
		userList.add(new UserModel());
		for (User user : EntityCache.userCache) {
			userList.add(new UserModel(user));
		}

		userList.set(userList.indexOf(ctx.model), ctx.model);

		((ChoiceBox<UserModel>) ctx.getNode(UserTabviewHandler.USER_SELECTION_KEY)).getSelectionModel()
																					.select(ctx.model);
	}

	/**
	 * Sets the button visibility of these buttons which required persistent
	 * user.
	 * 
	 * @param ctx
	 *            the form context
	 * @param visible
	 *            the new visible flag
	 */
	private void setButtonVisibility(final FormContext<UserModel> ctx, final boolean visible) {
		Objects.requireNonNull(ctx);

		ctx.getNode(UserTabviewHandler.DELETE_BUTTON_ID)
			.setVisible(visible);
		ctx.getNode(UserTabviewHandler.BLOCK_BUTTON_ID)
			.setVisible(visible);
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
	private void populateFormMessage(final String message, final FormContext<UserModel> ctx) {
		final TextFlow flow = ((TextFlow) ctx.getNode(UserTabviewHandler.FORM_MESSAGE));
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
