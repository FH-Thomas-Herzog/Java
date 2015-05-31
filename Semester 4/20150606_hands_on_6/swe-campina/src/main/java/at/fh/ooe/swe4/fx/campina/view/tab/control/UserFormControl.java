package at.fh.ooe.swe4.fx.campina.view.tab.control;

import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;
import at.fh.ooe.swe4.fx.campina.view.tab.model.UserModel;

public class UserFormControl {

	public UserFormControl() {
	}

	/**
	 * Handles the new action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void handleNewAction(ActionEvent event) {
		final FormContext<UserModel> ctx = getCtx((Node) event.getTarget());
		ctx.formHandler.resetForm(ctx.scene);
		ctx.model = new UserModel();
	}

	/**
	 * Handles the save action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void handleSaveAction(ActionEvent event) {
		final FormContext<UserModel> ctx = getCtx((Node) event.getTarget());
		ctx.formHandler.fillModel(ctx.scene, ctx.model);

		// TODO: Save/Update user on database
	}

	/**
	 * Handles the delete action of the form.
	 * 
	 * @param event
	 *            the {@link ActionEvent}
	 */
	public void handleDeleteAction(ActionEvent event) {
		final FormContext<UserModel> ctx = getCtx((Node) event.getTarget());
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
	public void handleBlockAction(ActionEvent event) {
		final FormContext<UserModel> ctx = getCtx((Node) event.getTarget());

		// TODO: Block user on database by setting blocked flag
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
