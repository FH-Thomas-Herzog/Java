package at.fh.ooe.swe4.fx.campina.view.admin.user.part;

import java.util.Arrays;
import java.util.Objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;
import javafx.util.StringConverter;
import at.fh.ooe.swe4.fx.campina.view.admin.user.control.UserEventControl;
import at.fh.ooe.swe4.fx.campina.view.admin.user.model.UserModel;
import at.fh.ooe.swe4.fx.campina.view.api.FormContext;
import at.fh.ooe.swe4.fx.campina.view.api.ViewHandler;
import at.fh.ooe.swe4.fx.campina.view.form.FormHandler;

/**
 * This class builds the user tab.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 3, 2015
 */
public class UserTabviewHandler implements ViewHandler<Tab> {

	/**
	 * The form builder for the user form
	 */
	private final FormHandler<UserModel>	formHandler;
	/**
	 * The user form controller
	 */
	private final UserEventControl			userControl;
	/**
	 * The form context for the user form
	 */
	final FormContext<UserModel>			ctx;

	// ###########################################################################
	// Ids of nodes and form backed model class
	// ###########################################################################
	public static final String				USER_SELECTION_KEY	= "user-selection";
	public static final String				SAVE_BUTTON_ID		= "user-save-button";
	public static final String				NEW_BUTTON_ID		= "user-new-button";
	public static final String				DELETE_BUTTON_ID	= "user-delete-button";
	public static final String				BLOCK_BUTTON_ID		= "user-block-button";
	public static final String				FORM_MESSAGE		= "user-form-message";

	/**
	 * Inits this user tab manager for the given scene
	 * 
	 * @param scene
	 *            the backing scene
	 */
	public UserTabviewHandler(final Scene scene) {
		Objects.requireNonNull(scene);

		this.formHandler = new FormHandler<UserModel>();
		this.formHandler.init();
		this.userControl = new UserEventControl();
		this.ctx = new FormContext<UserModel>("tab-user", formHandler, new UserModel(), scene);
	}

	// ###########################################################################
	// ScenePart methods
	// ###########################################################################
	@Override
	public String getId() {
		return "user-admin";
	}

	@Override
	public void initHandler() {
		userControl.handleUserReload(ctx);
		((ChoiceBox<UserModel>) ctx.getNode(USER_SELECTION_KEY)).getSelectionModel()
																.select(0);
	}

	@Override
	public Tab createNode() {
		final GridPane pane = new GridPane();
		pane.setId(getId() + "-content");

		// form
		final GridPane formGrid = formHandler.generateFormGrid(ctx);

		// left part of tab
		final GridPane mainGrid = new GridPane();
		mainGrid.setId("user-form");
		mainGrid.add(createFormMessage(ctx), 0, 0);
		mainGrid.add(createUserChoice(ctx), 0, 1);
		mainGrid.add(formGrid, 0, 2);
		mainGrid.add(createButtonGroup(ctx), 0, 3);
		mainGrid.setPrefHeight(500);
		mainGrid.setHgap(10);
		mainGrid.setVgap(10);
		pane.add(mainGrid, 0, 0);

		final Tab tab = new Tab(getId());
		tab.setText("Benutzerverwaltung");
		tab.setClosable(false);
		tab.setContent(pane);
		return tab;
	}

	// ###########################################################################
	// Node creation methods
	// ###########################################################################
	/**
	 * Creates the form message box where the form messages are placed
	 * 
	 * @param ctx
	 *            the form context
	 * @return the {@link TextFlow} isntance for the messages
	 */
	private TextFlow createFormMessage(final FormContext<UserModel> ctx) {
		final TextFlow flow = new TextFlow();
		flow.setId(getId() + "-user-form-message");
		flow.setStyle("-fx-font-size: 20pt");
		ctx.putNode(FORM_MESSAGE, flow);
		return flow;
	}

	/**
	 * Creates the button group for the user tab actions.
	 * 
	 * @param ctx
	 *            TODO
	 * 
	 * @return a {@link GridPane} instance holding the button
	 */
	private GridPane createButtonGroup(FormContext<UserModel> ctx) {
		final GridPane gridPane = new GridPane();
		gridPane.setId(getId() + "-button-grid");
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		// TODO: Register events here
		final Button newButton = new Button();
		newButton.setText("Zurücksetzen");
		newButton.setId(NEW_BUTTON_ID);
		newButton.setUserData(ctx);
		newButton.setOnAction(userControl::handleNewAction);

		final Button saveButton = new Button();
		saveButton.setId(SAVE_BUTTON_ID);
		saveButton.setText("Speichern");
		saveButton.setUserData(ctx);
		saveButton.setOnAction(userControl::handleSaveAction);

		final Button deleteButton = new Button();
		deleteButton.setId(DELETE_BUTTON_ID);
		deleteButton.setText("Löschen");
		deleteButton.setUserData(ctx);
		deleteButton.setOnAction(userControl::handleDeleteAction);
		deleteButton.setVisible(Boolean.FALSE);

		final Button blockButton = new Button();
		blockButton.setId(BLOCK_BUTTON_ID);
		blockButton.setText("Sperren");
		blockButton.setUserData(ctx);
		blockButton.setOnAction(userControl::handleBlockAction);
		blockButton.setVisible(Boolean.FALSE);

		gridPane.add(newButton, 0, 0);
		gridPane.add(saveButton, 1, 0);
		gridPane.add(deleteButton, 2, 0);
		gridPane.add(blockButton, 3, 0);

		// register in context
		ctx.putNode(NEW_BUTTON_ID, newButton);
		ctx.putNode(SAVE_BUTTON_ID, saveButton);
		ctx.putNode(DELETE_BUTTON_ID, deleteButton);
		ctx.putNode(BLOCK_BUTTON_ID, blockButton);

		return gridPane;
	}

	/**
	 * Creates the user choice node for the user selection.
	 * 
	 * @param ctxthe
	 *            form context
	 * @return the {@link ChoiceBox} instance
	 */
	private ChoiceBox<UserModel> createUserChoice(final FormContext<UserModel> ctx) {
		Objects.requireNonNull(ctx);

		final ObservableList<UserModel> users = FXCollections.observableArrayList(Arrays.asList(new UserModel()));

		final ChoiceBox<UserModel> userChoice = new ChoiceBox<>(users);
		userChoice.setUserData(ctx);
		userChoice.getSelectionModel()
					.selectedItemProperty()
					.addListener(new ChangeListener<UserModel>() {
						@Override
						public void changed(ObservableValue<? extends UserModel> observable, UserModel oldValue, UserModel newValue) {
							if (observable.getValue() == null) {
								return;
							}

							userControl.handleUserSelection(ctx, observable.getValue());
						}
					});
		userChoice.setConverter(new StringConverter<UserModel>() {

			@Override
			public String toString(UserModel object) {
				return object.getSelectionName();
			}

			@Override
			public UserModel fromString(String string) {
				throw new UnsupportedOperationException("Not supported conversion from string to object");
			}
		});
		userChoice.setPrefWidth(400);
		ctx.putObserable(USER_SELECTION_KEY, users);
		ctx.putNode(USER_SELECTION_KEY, userChoice);
		return userChoice;
	}
}
