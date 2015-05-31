package at.fh.ooe.swe4.fx.campina.view.user.control;

import java.util.Objects;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import at.fh.ooe.swe4.fx.campina.jpa.User;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;
import at.fh.ooe.swe4.fx.campina.view.user.model.UserModel;

public class UserFormControl {

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
		final FormContext<UserModel> ctx = getCtx((Node) event.getSource());
		ctx.formHandler.resetForm(ctx.scene);
		ctx.model = new UserModel();
	}

	/**
	 * Handles the save action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void handleSaveAction(final ActionEvent event) {
		final FormContext<UserModel> ctx = getCtx((Node) event.getSource());
		ctx.formHandler.fillModel(ctx.scene, ctx.model);

		// TODO: Save/Update user on database
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

		// reset model
		ctx.model = new UserModel();
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
	public void handleUserSelection(ObservableValue<? extends User> observable, User oldValue, User newValue) {
		System.out.println("hello selected");
	}

	public void handleUserSelection(final MouseEvent event) {
		final ChoiceBox<User> userList = ((ChoiceBox<User>) event.getSource());
		final FormContext<UserModel> ctx = getCtx(userList);
		final User user = userList.getSelectionModel()
									.getSelectedItem();

		// Selection present
		if (user != null) {
			// TODO: Update user mode with user values
		}
		// No selection present
		else {
			ctx.formHandler.resetForm(ctx.scene);
			ctx.model = new UserModel();
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
