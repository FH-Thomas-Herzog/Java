package at.fh.ooe.swe4.fx.campina.view.admin.login.part;

import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import at.fh.ooe.swe4.fx.campina.view.admin.login.control.LoginEventControl;
import at.fh.ooe.swe4.fx.campina.view.admin.login.model.LoginModel;
import at.fh.ooe.swe4.fx.campina.view.api.FormContext;
import at.fh.ooe.swe4.fx.campina.view.api.ViewHandler;
import at.fh.ooe.swe4.fx.campina.view.form.FormHandler;

/**
 * {@link ViewHandler} implementation for the login tab.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class LoginTabViewHandler implements ViewHandler<Tab> {

	/**
	 * The form builder for the user form
	 */
	private final FormHandler<LoginModel>	formHandler;
	/**
	 * The user form controller
	 */
	private final LoginEventControl			loginControl;
	/**
	 * The form context for the user form
	 */
	final FormContext<LoginModel>			ctx;

	// ###########################################################################
	// Ids of nodes and form backed model class
	// ###########################################################################
	public static final String				LOGIN_FORM_ID	= "login-form";
	public static final String				FORM_MESSAGE	= "login-form-message";
	public static final String				LOGIN_BUTTON_ID	= "login-button-login";

	/**
	 * @param scene
	 */
	public LoginTabViewHandler(final Scene scene) {
		Objects.requireNonNull(scene);

		final LoginModel model = new LoginModel();
		model.reset();
		this.formHandler = new FormHandler<LoginModel>().init();
		this.loginControl = new LoginEventControl();
		this.ctx = new FormContext<LoginModel>(LOGIN_FORM_ID, formHandler, model, scene);
	}

	// ###########################################################################
	// ScenePart methods
	// ###########################################################################
	@Override
	public String getId() {
		return "login-tab";
	}

	@Override
	public Tab createNode() {
		// Form message text
		final TextFlow flow = new TextFlow();
		flow.setId(FORM_MESSAGE);
		flow.getChildren()
			.add(new Text(String.format(LoginModel.LOGIN_GREETING_MESSAGE_PATTERN, LoginModel.MAX_COUNTER)));
		flow.setStyle("-fx-font-size: 20pt");
		ctx.putNode(FORM_MESSAGE, flow);

		// Form
		final Node form = formHandler.generateFormGrid(ctx);

		// Buttons
		final Button loginButton = new Button();
		loginButton.setId(LOGIN_BUTTON_ID);
		loginButton.setText("Login");
		loginButton.setUserData(ctx);
		loginButton.setOnAction(loginControl::handleLogin);
		ctx.putNode(LOGIN_BUTTON_ID, loginButton);

		final GridPane pane = new GridPane();
		pane.setId("user-tab-content");

		// left part of tab
		final GridPane mainGrid = new GridPane();
		mainGrid.setId("login-form-container");
		mainGrid.add(flow, 0, 0);
		mainGrid.add(form, 0, 1);
		mainGrid.add(loginButton, 0, 2);
		mainGrid.setPrefHeight(500);
		pane.add(mainGrid, 0, 0);

		final Tab tab = new Tab(getId());
		tab.setText("Login");
		tab.setClosable(false);
		tab.setContent(pane);

		return tab;
	}

	@Override
	public void initHandler() {
		ctx.formHandler.fillForm(ctx);
	}
}
