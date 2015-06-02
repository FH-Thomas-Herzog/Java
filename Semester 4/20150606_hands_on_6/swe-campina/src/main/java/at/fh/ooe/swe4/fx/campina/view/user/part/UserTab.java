package at.fh.ooe.swe4.fx.campina.view.user.part;

import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.Objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import at.fh.ooe.swe4.fx.campina.component.builder.impl.FormHandler;
import at.fh.ooe.swe4.fx.campina.view.api.ScenePart;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;
import at.fh.ooe.swe4.fx.campina.view.user.control.UserFormControl;
import at.fh.ooe.swe4.fx.campina.view.user.model.UserModel;

public class UserTab implements ScenePart<Tab> {

	private final Scene						scene;
	private final FormHandler<UserModel>	formBuidler;
	private final UserFormControl			userFormControl;
	final FormContext<UserModel>			fCtx;
	private static final String				ID					= "user-admin";
	private static final Class<UserModel>	USER_MODEL			= UserModel.class;
	public static final String				USER_SELECTION_KEY	= "user-selection";

	public UserTab(final Scene scene) {
		this.scene = scene;
		this.formBuidler = new FormHandler<UserModel>();
		this.formBuidler.init(USER_MODEL);
		this.userFormControl = new UserFormControl();
		this.fCtx = new FormContext<UserModel>("user-form", formBuidler, new UserModel(), scene);
	}

	@Override
	public String getId() {
		return ID;
	}

	public void init() {
		userFormControl.handleUserLoad(fCtx.getObserable(USER_SELECTION_KEY));
		((ChoiceBox<UserModel>) fCtx.getNode(USER_SELECTION_KEY)).getSelectionModel()
																	.select(0);
	}

	@Override
	public Tab create() {
		final GridPane pane = new GridPane();
		pane.setId(toId("tab-content"));

		// form
		final GridPane formGrid = formBuidler.generateFormGrid(fCtx);

		// left part of tab
		final GridPane mainGrid = new GridPane();
		mainGrid.setId(toId("form"));
		mainGrid.add(createUserChoice(fCtx), 0, 0);
		mainGrid.add(formGrid, 0, 1);
		mainGrid.add(createUserFormButtonGroup(fCtx), 0, 2);

		pane.add(mainGrid, 0, 0);

		final Tab tab = new Tab("tab-" + ID);
		tab.setClosable(false);
		tab.setContent(pane);
		return tab;
	}

	// ########################################################
	// Private Section
	// ########################################################
	/**
	 * Creates the button group for the user tab held form.
	 * 
	 * @param ctx
	 *            TODO
	 * 
	 * @return a {@link GridPane} instance holding the button
	 */
	private GridPane createUserFormButtonGroup(FormContext<UserModel> ctx) {
		final GridPane gridPane = new GridPane();
		gridPane.setId(toId("button-grid-pane"));

		// TODO: Register events here
		final Button newButton = new Button();
		newButton.setText("Zurücksetzen");
		newButton.setId(toId("new"));
		newButton.setUserData(ctx);
		newButton.setOnAction(userFormControl::handleNewAction);

		final Button saveButton = new Button();
		saveButton.setId(toId("save"));
		saveButton.setText("Speichern");
		saveButton.setUserData(ctx);
		saveButton.setOnAction(userFormControl::handleSaveAction);

		final Button deleteButton = new Button();
		deleteButton.setId(toId("delete"));
		deleteButton.setText("Löschen");
		deleteButton.setUserData(ctx);
		deleteButton.setOnAction(userFormControl::handleDeleteAction);

		final Button blockButton = new Button();
		blockButton.setId(toId("block"));
		blockButton.setText("Sperren");
		blockButton.setUserData(ctx);
		blockButton.setOnAction(userFormControl::handleBlockAction);

		gridPane.add(newButton, 0, 0);
		gridPane.add(saveButton, 1, 0);
		gridPane.add(deleteButton, 2, 0);
		gridPane.add(blockButton, 3, 0);

		return gridPane;
	}

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

	private String toId(final String id) {
		return id + "-" + ID;
	}
}
