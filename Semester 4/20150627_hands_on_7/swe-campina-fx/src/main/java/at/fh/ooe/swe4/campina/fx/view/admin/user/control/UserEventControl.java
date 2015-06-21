package at.fh.ooe.swe4.campina.fx.view.admin.user.control;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import at.fh.ooe.swe4.campina.dao.api.UserDao;
import at.fh.ooe.swe4.campina.dao.exception.EmailAlreadyUsedException;
import at.fh.ooe.swe4.campina.dao.exception.UsernameAlreadyUsedException;
import at.fh.ooe.swe4.campina.fx.rmi.service.locator.DaoLocator;
import at.fh.ooe.swe4.campina.fx.view.admin.user.model.UserModel;
import at.fh.ooe.swe4.campina.fx.view.admin.user.part.UserTabviewHandler;
import at.fh.ooe.swe4.campina.fx.view.api.FormContext;
import at.fh.ooe.swe4.campina.persistence.api.entity.User;
import at.fh.ooe.swe4.campina.rmi.api.factory.RmiDaoFactory;

/**
 * The control bean for the user tab.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 3, 2015
 */
public class UserEventControl {

	private final UserDao		dao;
	private static final Logger	log	= Logger.getLogger(UserEventControl.class);

	/**
	 * Creates test data since we have no back-end yet
	 */
	public UserEventControl() {
		dao = DaoLocator.getDao(UserDao.class);
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
		// validate form
		ctx.formHandler.validateForm(ctx);
		// is valid
		if (ctx.valid) {
			// fill model with form data
			ctx.formHandler.fillModel(ctx);

			// TODO: Persist entity here
			User user = ctx.model.getEntity();
			try {
				user = dao.save(user);
				user = dao.byId(user.getId());
				populateFormMessage(null, ctx);
			} catch (RemoteException e) {
				log.error("Could not save user", e);
				if (e.detail != null) {
					if (e.detail instanceof EmailAlreadyUsedException) {
						populateFormMessage("Email bereits vergeben", ctx);
					} else if (e.detail instanceof UsernameAlreadyUsedException) {
						populateFormMessage("Bentuezrname bereits vergeben", ctx);
					}
				}
			}
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
			try {
				dao.delete(model.getEntity());
			} catch (RemoteException e) {
				log.error("Could not delete user", e);
				populateFormMessage("Benutzer konnte nicht gelöscht werden", ctx);
			}
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
		// selected user model
		final UserModel model = ((ChoiceBox<UserModel>) ctx.getNode(UserTabviewHandler.USER_SELECTION_KEY)).getSelectionModel()
																											.getSelectedItem();

		final Button blockButton = (Button) ctx.getNode(UserTabviewHandler.BLOCK_BUTTON_ID);
		User user = model.getEntity();

		// invert user blocked state
		user.setBlockedFlag(!model.getEntity()
									.getBlockedFlag());
		try {
			user = dao.save(user);
			user = dao.byId(user.getId());
			populateFormMessage(null, ctx);
		} catch (RemoteException e) {
			log.error("Could not block user", e);
			populateFormMessage("Konnte Benutzer nicht sperren", ctx);
		}

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
		// clear former set message if new user
		if (!ctx.model.equals(user)) {
			populateFormMessage(null, ctx);
		}
		// Selection present
		if (user.getId() != null) {
			try {
				final User userDB = dao.byId(user.getId());
				user.prepare(userDB);
				ctx.model.prepare(user.getEntity());
				ctx.formHandler.fillForm(ctx);
				setButtonVisibility(ctx, Boolean.TRUE);
			} catch (RemoteException e) {
				log.error("Could not load selected user", e);
				ctx.model.reset();
				ctx.formHandler.fillForm(ctx);
				setButtonVisibility(ctx, Boolean.FALSE);
			}
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
		boolean found = Boolean.FALSE;
		try {
			final List<User> users = dao.getAll();
			for (User user : users) {
				if (user.equals(ctx.model.getEntity())) {
					found = Boolean.TRUE;
				}
				userList.add(new UserModel(user));
			}
		} catch (RemoteException e) {
			log.error("Coul not load users", e);
			// TODO: handle exception
		}

		if (!found) {
			ctx.model.reset();
			ctx.formHandler.fillForm(ctx);
			setButtonVisibility(ctx, Boolean.FALSE);
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
