package at.fh.ooe.swe4.fx.campina.view.user.control;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import at.fh.ooe.swe4.fx.campina.jpa.User;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;
import at.fh.ooe.swe4.fx.campina.view.user.model.UserModel;
import at.fh.ooe.swe4.fx.campina.view.user.part.UserTab;

public class UserFormControl {

	public static Set<User>	userCache	= new HashSet<>(100);

	public UserFormControl() {
		userCache.add(new User(1, "Thomas", "Herzog", "t.herzog@bla.bla"));
		userCache.add(new User(2, "Hugo", "Fichtner", "h.fichtner@bla.bla"));
		userCache.add(new User(3, "Christian", "Beikov", "c.beickov@bla.bla"));
		userCache.add(new User(4, "Rainer", "Rudolf", "r.rudolf@bla.bla"));
		userCache.add(new User(5, "Bernd", "Maier", "b.maier@bla.bla"));
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
		final FormContext<UserModel> ctx = getCtx((Node) event.getSource());
		ctx.formHandler.resetForm(ctx.scene);
		ctx.model = new UserModel();
		ctx.model.reset();
		((ChoiceBox<UserModel>) ctx.getNode(UserTab.USER_SELECTION_KEY)).getSelectionModel()
																		.select(new UserModel());
	}

	/**
	 * Handles the save action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void handleSaveAction(final ActionEvent event) {
		final FormContext<UserModel> ctx = getCtx((Node) event.getSource());
		ctx.formHandler.validateForm(ctx.scene);
		if (ctx.valid) {
			ctx.formHandler.fillModel(ctx.scene, ctx.model);

			// TODO: Persist entity here
			final User user = ctx.model.getEntity();
			if (!userCache.contains(user)) {
				user.setId(userCache.size() + 1);
			}
			ctx.model.prepare(user);
			UserFormControl.userCache.add(user);
		}
		event.consume();
		handleUserLoad(ctx.getObserable(UserTab.USER_SELECTION_KEY));
		((ChoiceBox<UserModel>) ctx.getNode(UserTab.USER_SELECTION_KEY)).getSelectionModel()
																		.select(ctx.model);
	}

	/**
	 * Handles the delete action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void handleDeleteAction(final ActionEvent event) {
		final FormContext<UserModel> ctx = getCtx((Node) event.getSource());
		ctx.formHandler.resetForm(ctx.scene);

		// TODO: Delete user from database
		final ChoiceBox<UserModel> userSelection = ((ChoiceBox<UserModel>) ctx.getNode(UserTab.USER_SELECTION_KEY));
		final UserModel model = userSelection.getSelectionModel()
												.getSelectedItem();

		// TODO: Delete entity from db here
		if (model.getId() != null) {
			userCache.remove(model.getEntity());
			handleUserLoad(ctx.getObserable(UserTab.USER_SELECTION_KEY));
		}

		// reset model
		ctx.model = new UserModel();
		ctx.formHandler.resetForm(ctx.scene);

		userSelection.getSelectionModel()
						.select(ctx.model);

	}

	/**
	 * Handles the block action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void handleBlockAction(final ActionEvent event) {
		final FormContext<UserModel> ctx = getCtx((Node) event.getSource());

		// TODO: Block user on database by setting blocked flag
	}

	// #############################################################
	// Selection controls
	// #############################################################
	public void handleUserSelection(final FormContext<UserModel> ctx, final UserModel user) {
		// Selection present
		if (user != null) {
			ctx.model = new UserModel(user.getEntity());
			ctx.formHandler.resetForm(ctx.scene);
			ctx.formHandler.fillForm(ctx.scene, ctx.model);
		}
		// No selection present
		else {
			ctx.formHandler.resetForm(ctx.scene);
			ctx.model = new UserModel();
		}
	}

	// #############################################################
	// Load controls
	// #############################################################
	public void handleUserLoad(final ObservableList<UserModel> userList) {
		Objects.requireNonNull(userList);

		userList.clear();
		userList.add(new UserModel());
		for (User user : userCache) {
			userList.add(new UserModel(user));
		}
	}

	/**
	 * Gets the used context from the node
	 * 
	 * @param node
	 *            the node retrieve context from
	 * @return the found context, null otherwise
	 */
	private <T> T getCtx(final Node node) {
		Objects.requireNonNull(node, "Node must node be null");

		return (T) node.getUserData();
	}
}
