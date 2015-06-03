package at.fh.ooe.swe4.fx.campina.view.user.control;

import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import org.apache.commons.lang.StringUtils;

import at.fh.ooe.swe4.fx.campina.jpa.EntityCache;
import at.fh.ooe.swe4.fx.campina.jpa.User;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;
import at.fh.ooe.swe4.fx.campina.view.user.model.UserModel;
import at.fh.ooe.swe4.fx.campina.view.user.part.UserTab;

/**
 * The control bean for the user tab form.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 3, 2015
 */
public class UserFormControl {

	/**
	 * Creates test data since we have no back-end yet
	 */
	public UserFormControl() {
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
		ctx.model = new UserModel();
		// sets a new user entity and inits model
		ctx.model.reset();
		// set this model as selected ('Please choose')
		((ChoiceBox<UserModel>) ctx.getNode(UserTab.USER_SELECTION_KEY)).getSelectionModel()
																		.select(new UserModel());

		// hide buttons
		setButtonVisibility(ctx, Boolean.FALSE);
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
			// init model with new saved user
			ctx.model.prepare(user);
			// save model in backed list for testing
			EntityCache.userCache.add(user);
			// reload data from db (now backing list)
			handleUserLoad(ctx.getObserable(UserTab.USER_SELECTION_KEY));
			// set this model selected
			((ChoiceBox<UserModel>) ctx.getNode(UserTab.USER_SELECTION_KEY)).getSelectionModel()
																			.select(ctx.model);
			// enable buttons
			setButtonVisibility(ctx, Boolean.TRUE);
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

		final ChoiceBox<UserModel> userSelection = ((ChoiceBox<UserModel>) ctx.getNode(UserTab.USER_SELECTION_KEY));
		final UserModel model = userSelection.getSelectionModel()
												.getSelectedItem();

		// TODO: Delete entity from db here

		// existing user gets deleted
		if (model.getId() != null) {
			EntityCache.userCache.remove(model.getEntity());
			handleUserLoad(ctx.getObserable(UserTab.USER_SELECTION_KEY));
		}

		// reset model
		ctx.model = new UserModel();
		// set new user as selected
		userSelection.getSelectionModel()
						.select(ctx.model);
		// disable buttons
		setButtonVisibility(ctx, Boolean.FALSE);
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
		final ChoiceBox<UserModel> userSelection = ((ChoiceBox<UserModel>) ctx.getNode(UserTab.USER_SELECTION_KEY));
		final UserModel model = userSelection.getSelectionModel()
												.getSelectedItem();
		final Button blockButton = (Button) ctx.getNode(UserTab.BLOCK_BUTTON_ID);
		final User user = model.getEntity();

		// invert user blocked state
		user.setBlockedFlag(!model.getEntity()
									.getBlockedFlag());

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
	}

	// #############################################################
	// Selection controls
	// #############################################################
	public void handleUserSelection(final FormContext<UserModel> ctx, final UserModel user) {
		// clear former set message
		populateFormMessage(null, ctx);
		// Selection present
		if (user.getId() != null) {
			ctx.model = new UserModel(user.getEntity());
			ctx.formHandler.resetForm(ctx);
			ctx.formHandler.fillForm(ctx);
			setButtonVisibility(ctx, Boolean.TRUE);
		}
		// No selection present
		else {
			ctx.formHandler.resetForm(ctx);
			ctx.model = new UserModel();
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
	public void handleUserLoad(final ObservableList<UserModel> userList) {
		Objects.requireNonNull(userList);

		userList.clear();
		userList.add(new UserModel());
		for (User user : EntityCache.userCache) {
			userList.add(new UserModel(user));
		}
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

		ctx.getNode(UserTab.DELETE_BUTTON_ID)
			.setVisible(visible);
		ctx.getNode(UserTab.BLOCK_BUTTON_ID)
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
		final TextFlow flow = ((TextFlow) ctx.getNode(UserTab.FORM_MESSAGE));
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
