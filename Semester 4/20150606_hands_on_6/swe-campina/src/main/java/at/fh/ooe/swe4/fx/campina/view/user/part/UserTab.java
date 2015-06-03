package at.fh.ooe.swe4.fx.campina.view.user.part;

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
import at.fh.ooe.swe4.fx.campina.component.builder.impl.FormHandler;
import at.fh.ooe.swe4.fx.campina.view.api.ScenePart;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;
import at.fh.ooe.swe4.fx.campina.view.user.control.UserFormControl;
import at.fh.ooe.swe4.fx.campina.view.user.model.UserModel;

/**
 * This class builds the user tab.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 3, 2015
 */
public class UserTab implements ScenePart<Tab> {

	/**
	 * The form builder for the user form
	 */
	private final FormHandler<UserModel>	formBuidler;
	/**
	 * The user form controller
	 */
	private final UserFormControl			userFormControl;
	/**
	 * The form context for the user form
	 */
	final FormContext<UserModel>			fCtx;

	// ###########################################################################
	// Ids of nodes and form backed model class
	// ###########################################################################
	private static final Class<UserModel>	USER_MODEL			= UserModel.class;
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
	public UserTab(final Scene scene) {
		Objects.requireNonNull(scene);

		final UserModel model = new UserModel();
		model.reset();
		this.formBuidler = new FormHandler<UserModel>();
		this.formBuidler.init(USER_MODEL);
		this.userFormControl = new UserFormControl();
		this.fCtx = new FormContext<UserModel>("user-form", formBuidler, model, scene);
	}

	// ###########################################################################
	// ScenePart methods
	// ###########################################################################
	@Override
	public String getId() {
		return "user-admin";
	}

	@Override
	public void init() {
		userFormControl.handleUserLoad(fCtx.getObserable(USER_SELECTION_KEY));
		((ChoiceBox<UserModel>) fCtx.getNode(USER_SELECTION_KEY)).getSelectionModel()
																	.select(0);
	}

	@Override
	public Tab create() {
		final GridPane pane = new GridPane();
		pane.setId("user-tab-content");

		// form
		final GridPane formGrid = formBuidler.generateFormGrid(fCtx);

		// left part of tab
		final GridPane mainGrid = new GridPane();
		mainGrid.setId("user-form");
		mainGrid.add(createFormMessageBox(fCtx), 0, 0);
		mainGrid.add(createUserChoice(fCtx), 0, 1);
		mainGrid.add(formGrid, 0, 2);
		mainGrid.add(createUserFormButtonGroup(fCtx), 0, 3);
		mainGrid.setPrefHeight(500);
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
	private TextFlow createFormMessageBox(final FormContext<UserModel> ctx) {
		final TextFlow flow = new TextFlow();
		flow.setId(FORM_MESSAGE);
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
	private GridPane createUserFormButtonGroup(FormContext<UserModel> ctx) {
		final GridPane gridPane = new GridPane();
		gridPane.setId("user-button-grid-pane");

		// TODO: Register events here
		final Button newButton = new Button();
		newButton.setText("Zurücksetzen");
		newButton.setId(NEW_BUTTON_ID);
		newButton.setUserData(ctx);
		newButton.setOnAction(userFormControl::handleNewAction);

		final Button saveButton = new Button();
		saveButton.setId(SAVE_BUTTON_ID);
		saveButton.setText("Speichern");
		saveButton.setUserData(ctx);
		saveButton.setOnAction(userFormControl::handleSaveAction);

		final Button deleteButton = new Button();
		deleteButton.setId(DELETE_BUTTON_ID);
		deleteButton.setText("Löschen");
		deleteButton.setUserData(ctx);
		deleteButton.setOnAction(userFormControl::handleDeleteAction);
		deleteButton.setVisible(Boolean.FALSE);

		final Button blockButton = new Button();
		blockButton.setId(BLOCK_BUTTON_ID);
		blockButton.setText("Sperren");
		blockButton.setUserData(ctx);
		blockButton.setOnAction(userFormControl::handleBlockAction);
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
							if ((newValue != null) && (!ctx.model.equals(newValue))) {
								userFormControl.handleUserSelection(ctx,
																	newValue);
							}
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
		userChoice.getSelectionModel()
					.select(0);
		userChoice.setPrefWidth(400);
		ctx.putObserable(USER_SELECTION_KEY, users);
		ctx.putNode(USER_SELECTION_KEY, userChoice);
		return userChoice;
	}
}
