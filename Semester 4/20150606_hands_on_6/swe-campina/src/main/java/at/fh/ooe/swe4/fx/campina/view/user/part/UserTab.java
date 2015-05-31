package at.fh.ooe.swe4.fx.campina.view.user.part;

import java.util.Arrays;
import java.util.Objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import at.fh.ooe.swe4.fx.campina.component.builder.impl.FormHandler;
import at.fh.ooe.swe4.fx.campina.jpa.User;
import at.fh.ooe.swe4.fx.campina.view.api.ScenePart;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;
import at.fh.ooe.swe4.fx.campina.view.user.control.UserFormControl;
import at.fh.ooe.swe4.fx.campina.view.user.model.UserModel;

public class UserTab implements ScenePart<Tab> {

	private final Scene						scene;
	private final FormHandler<UserModel>	formBuidler;
	private final UserFormControl			userFormControl;

	private static final String				ID			= "user-admin";
	private static final Class<UserModel>	USER_MODEL	= UserModel.class;

	public UserTab(final Scene scene) {
		this.scene = scene;
		this.formBuidler = new FormHandler<UserModel>();
		this.formBuidler.init(USER_MODEL);
		this.userFormControl = new UserFormControl();
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public Tab create() {
		final GridPane pane = new GridPane();
		pane.setId(toId("tab-content"));

		// form
		final FormContext<UserModel> fCtx = new FormContext<UserModel>("user-form", formBuidler, new UserModel(), scene);
		final GridPane formGrid = formBuidler.generateFormGrid(fCtx);

		// left part of tab
		final GridPane leftTabContent = new GridPane();
		leftTabContent.setId(toId("form"));
		leftTabContent.add(formGrid, 0, 0);
		leftTabContent.add(createUserFormButtonGroup(fCtx), 0, 1);

		final GridPane rightTabContent = new GridPane();
		rightTabContent.setId(toId("control"));
		rightTabContent.add(createUserControls(fCtx), 0, 0);

		pane.add(leftTabContent, 0, 0);
		pane.add(rightTabContent, 1, 0);

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

	private GridPane createUserControls(final FormContext<UserModel> ctx) {
		Objects.requireNonNull(ctx);

		final GridPane gridPane = new GridPane();
		gridPane.setId(toId("user-control-grid-pane"));

		final ObservableList<User> users = FXCollections.observableArrayList(Arrays.asList(null,
																							new User("Thomas", "Herzog"),
																							new User("Hugo", "Maier"),
																							new User("Herbert", "Rutzinger")));

		final ChoiceBox<User> userList = new ChoiceBox<User>(users);
		userList.setUserData(ctx);
		userList.getSelectionModel()
				.selectedItemProperty()
				.addListener(userFormControl::handleUserSelection);
		userList.setConverter(new StringConverter<User>() {

			@Override
			public String toString(User object) {
				if (object != null) {
					return new StringBuilder(object.getLastName()).append(", ")
																	.append(object.getFirstName())
																	.toString();
				}
				return "Neuer Benutzer";
			}

			@Override
			public User fromString(String string) {
				throw new UnsupportedOperationException("Not supported conversion from string to object");
			}
		});
		userList.getSelectionModel()
				.select(0);
		gridPane.add(userList, 0, 0);

		return gridPane;
	}

	private String toId(final String id) {
		return id + "-" + ID;
	}
}
