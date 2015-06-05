package at.fh.ooe.swe4.campina.fx.view.admin.login.control;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import org.apache.commons.lang.StringUtils;

import at.fh.ooe.swe4.campina.fx.view.admin.login.model.LoginModel;
import at.fh.ooe.swe4.campina.fx.view.admin.login.part.LoginTabViewHandler;
import at.fh.ooe.swe4.campina.fx.view.api.FormContext;
import at.fh.ooe.swe4.campina.jpa.User;

/**
 * This class contains all of the action logic:
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class LoginEventControl {

	private User	loggedUser	= null;

	/**
	 * 
	 */
	public LoginEventControl() {
	}

	/**
	 * Handles the login event
	 * 
	 * @param eventthe
	 *            {@link ActionEvent}
	 */
	public void handleLogin(final ActionEvent event) {
		final FormContext<LoginModel> ctx = (FormContext<LoginModel>) ((Node) event.getSource()).getUserData();
		populateFormMessage(null, ctx);
		ctx.formHandler.validateForm(ctx);

		if (ctx.valid) {
			ctx.formHandler.fillModel(ctx);

			// TODO: Search for username and password on db
			loggedUser = EntityCache.isValidLogin(ctx.model);
			// increase counter
			if (loggedUser == null) {
				ctx.model.increaseCounter();
				if (ctx.model.getCounter() >= LoginModel.MAX_COUNTER) {
					populateFormMessage(LoginModel.LOGIN_BLOCKED_MESSAGE, ctx);
					((Button) ctx.getNode(LoginTabViewHandler.LOGIN_BUTTON_ID)).setDisable(Boolean.TRUE);
				} else {
					populateFormMessage(String.format(LoginModel.LOGIN_FAILED_MESSAGE_PATTERN, (LoginModel.MAX_COUNTER - ctx.model.getCounter())), ctx);
				}
			}
		}
		// for invalid
		else {
			populateFormMessage("Formular ungültig !!! Bitte Eingaben prüfen", ctx);
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
	private void populateFormMessage(final String message, final FormContext<LoginModel> ctx) {
		final TextFlow flow = ((TextFlow) ctx.getNode(LoginTabViewHandler.FORM_MESSAGE));
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
