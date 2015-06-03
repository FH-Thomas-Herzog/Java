package at.fh.ooe.swe4.fx.campina.view.login.part;

import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import at.fh.ooe.swe4.fx.campina.component.builder.impl.FormHandler;
import at.fh.ooe.swe4.fx.campina.view.api.ScenePart;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;
import at.fh.ooe.swe4.fx.campina.view.login.control.LoginEventControl;
import at.fh.ooe.swe4.fx.campina.view.login.model.LoginModel;

public class LoginTab implements ScenePart<Tab> {

	/**
	 * The form builder for the user form
	 */
	private final FormHandler<LoginModel>	formHandler;
	/**
	 * The user form controller
	 */
	private final LoginEventControl			loginFormControl;
	/**
	 * The form context for the user form
	 */
	final FormContext<LoginModel>			fCtx;

	// ###########################################################################
	// Ids of nodes and form backed model class
	// ###########################################################################
	public static final String				LOGIN_FORM_ID	= "login-form";
	public static final String				FORM_MESSAGE	= "login-form-message";
	public static final String				LOGIN_BUTTON_ID	= "login-button-login";

	/**
	 * @param scene
	 */
	public LoginTab(final Scene scene) {
		Objects.requireNonNull(scene);

		final LoginModel model = new LoginModel();
		model.reset();
		this.formHandler = new FormHandler<LoginModel>().init(LoginModel.class);
		this.loginFormControl = new LoginEventControl();
		this.fCtx = new FormContext<LoginModel>(LOGIN_FORM_ID, formHandler, model, scene);
	}

	// ###########################################################################
	// ScenePart methods
	// ###########################################################################
	@Override
	public String getId() {
		return "login-tab";
	}

	@Override
	public Tab create() {
		// Form message text
		final TextFlow flow = new TextFlow();
		flow.setId(FORM_MESSAGE);
		flow.getChildren()
			.add(new Text(String.format(LoginModel.LOGIN_GREETING_MESSAGE_PATTERN, LoginModel.MAX_COUNTER)));
		fCtx.putNode(FORM_MESSAGE, flow);

		// Form
		final Node form = formHandler.generateFormGrid(fCtx);

		// Buttons
		final Button loginButton = new Button();
		loginButton.setId(LOGIN_BUTTON_ID);
		loginButton.setText("Login");
		loginButton.setUserData(fCtx);
		loginButton.setOnAction(loginFormControl::handleLogin);
		fCtx.putNode(LOGIN_BUTTON_ID, loginButton);

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
	public void init() {
		fCtx.formHandler.fillForm(fCtx);
	}
}
