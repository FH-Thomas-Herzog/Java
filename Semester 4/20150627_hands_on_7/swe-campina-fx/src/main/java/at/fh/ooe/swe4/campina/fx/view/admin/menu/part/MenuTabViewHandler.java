package at.fh.ooe.swe4.campina.fx.view.admin.menu.part;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;
import javafx.util.StringConverter;
import at.fh.ooe.swe4.campina.fx.view.admin.menu.control.MenuEntryEventControl;
import at.fh.ooe.swe4.campina.fx.view.admin.menu.control.MenuEventControl;
import at.fh.ooe.swe4.campina.fx.view.admin.menu.model.MenuEntryModel;
import at.fh.ooe.swe4.campina.fx.view.admin.menu.model.MenuModel;
import at.fh.ooe.swe4.campina.fx.view.admin.menu.model.MenuEntryModel.MenuEntryModelConverter;
import at.fh.ooe.swe4.campina.fx.view.admin.menu.model.MenuModel.MenuModelConverter;
import at.fh.ooe.swe4.campina.fx.view.api.FormContext;
import at.fh.ooe.swe4.campina.fx.view.api.ViewHandler;
import at.fh.ooe.swe4.campina.fx.view.form.FormHandler;

/**
 * The view handler for the menu and menu entry view.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class MenuTabViewHandler implements ViewHandler<Tab> {

	/**
	 * The form handler for the menu entry form
	 */
	private final FormHandler<MenuEntryModel>	menuEntryFormHandler;
	/**
	 * The menu entry controller
	 */
	private final MenuEntryEventControl			menuEntryControl;
	/**
	 * The form context for the menu entry form
	 */
	final FormContext<MenuEntryModel>			menuEntryCtx;

	/**
	 * The form handler for the menu form
	 */
	private final FormHandler<MenuModel>		menuFormHandler;
	/**
	 * The menu controller
	 */
	private final MenuEventControl				menuControl;
	/**
	 * The form context for the menu entry form
	 */
	final FormContext<MenuModel>				menuCtx;

	// ###########################################################################
	// Ids of nodes and form backed model class
	// ###########################################################################
	public static final String					MENU_ENTRY_FORM_MESSAGE		= "tab-menu-entry-form-message";
	public static final String					MENU_FORM_MESSAGE			= "tab-menu-form-message";
	public static final String					EDIT_OPTION_ID				= "tab-menu-edit-option";
	public static final String					MENU_SELECTION_KEY			= "tab-menu-menu-selection-data";
	public static final String					MENU_ENTRY_SELECTION_KEY	= "tab-menu-menu-entry-selection-data";
	public static final String					MENU_SAVE_BUTTON_ID			= "tab-menu-menu-form-save-button";
	public static final String					MENU_DELETE_BUTTON_ID		= "tab-menu-menu-form-delete-button";
	public static final String					MENU_ENTRY_SAVE_BUTTON_ID	= "tab-menu-menu-entry-form-save-button";
	public static final String					MENU_ENTRY_DELETE_BUTTON_ID	= "tab-menu-menu-entry-form-delete-button";
	public static final String					MENU_NEW_BUTTON_ID			= "tab-menu-menu-form-new-button";
	public static final String					MENU_ENTRY_NEW_BUTTON_ID	= "tab-menu-menu-entry-form-new-button";

	/**
	 * Enumeration which specifies the edit type. Provides an label too.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 5, 2015
	 */
	public static enum EditMode {
		MENU("Menus"),
		MENU_ENTRY("Menu Einträge");

		public final String	label;

		/**
		 * @param label
		 */
		private EditMode(final String label) {
			this.label = label;
		}
	}

	/**
	 * The converter for the {@link EditMode} type.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 5, 2015
	 */
	public static class EditModeConverter extends StringConverter<EditMode> {

		@Override
		public String toString(EditMode object) {
			return object.label;
		}

		@Override
		public EditMode fromString(String string) {
			throw new UnsupportedOperationException("From string not supportedby this converter");
		}

	}

	/**
	 * The change listener for the {@link Toggle} type.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 5, 2015
	 */
	public static class ChangeModeListener implements ChangeListener<Toggle> {

		private final GridPane				parent;
		private final GridPane				menuGroup;
		private final GridPane				menuEntryGroup;
		private final MenuEventControl		menuControl;
		private final MenuEntryEventControl	menuEntryControl;

		/**
		 * @param parent
		 * @param menuGroup
		 * @param menuEntryGroup
		 * @param menuControl
		 * @param menuEntryControl
		 */
		public ChangeModeListener(GridPane parent, GridPane menuGroup, GridPane menuEntryGroup, MenuEventControl menuControl,
				MenuEntryEventControl menuEntryControl) {
			super();
			Objects.requireNonNull(parent);
			Objects.requireNonNull(menuGroup);
			Objects.requireNonNull(menuEntryGroup);
			Objects.requireNonNull(menuControl);
			Objects.requireNonNull(menuEntryControl);

			this.parent = parent;
			this.menuGroup = menuGroup;
			this.menuEntryGroup = menuEntryGroup;
			this.menuControl = menuControl;
			this.menuEntryControl = menuEntryControl;
		}

		@Override
		public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
			if (observable.getValue() == null) {
				return;
			}

			parent.getChildren()
					.remove(menuEntryGroup);
			parent.getChildren()
					.remove(menuGroup);

			switch ((EditMode) observable.getValue()
											.getUserData()) {
			case MENU:
				parent.add(menuGroup, 0, 1);
				final FormContext<MenuModel> menuCtx = (FormContext<MenuModel>) menuGroup.getUserData();
				menuCtx.model.reset();
				menuControl.handleMenuReload(menuCtx);
				((ChoiceBox<MenuModel>) menuCtx.getNode(MENU_SELECTION_KEY)).getSelectionModel()
																			.select(menuCtx.model);
				menuCtx.getNode(MENU_DELETE_BUTTON_ID)
						.setVisible(Boolean.FALSE);
				menuCtx.formHandler.fillForm(menuCtx);
				break;
			case MENU_ENTRY:
				parent.add(menuEntryGroup, 0, 1);
				final FormContext<MenuEntryModel> menuEntryCtx = (FormContext<MenuEntryModel>) menuEntryGroup.getUserData();
				menuEntryCtx.model.reset();
				menuEntryControl.handleMenuEntryReload(menuEntryCtx);
				((ChoiceBox<MenuEntryModel>) menuEntryCtx.getNode(MENU_ENTRY_SELECTION_KEY)).getSelectionModel()
																							.select(menuEntryCtx.model);
				menuEntryCtx.getNode(MENU_ENTRY_DELETE_BUTTON_ID)
							.setVisible(Boolean.FALSE);
				menuEntryControl.handleMenuLoad(menuEntryCtx);
				menuEntryCtx.formHandler.fillForm(menuEntryCtx);
				break;
			default:
				throw new IllegalArgumentException("'EditMode#" + ((EditMode) observable.getValue()
																						.getUserData()).name() + "' node suppported");
			}
		}
	}

	/**
	 * The change listener for the {@link MenuModel} type.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 5, 2015
	 */
	public static class MenuChangeListener implements ChangeListener<MenuModel> {

		private final FormContext<MenuModel>	ctx;
		private final MenuEventControl			control;

		/**
		 * @param ctx
		 * @param control
		 */
		public MenuChangeListener(FormContext<MenuModel> ctx, MenuEventControl control) {
			super();
			Objects.requireNonNull(ctx);
			Objects.requireNonNull(control);

			this.ctx = ctx;
			this.control = control;
		}

		@Override
		public void changed(ObservableValue<? extends MenuModel> observable, MenuModel oldValue, MenuModel newValue) {
			if (observable.getValue() == null) {
				return;
			}

			ctx.model = observable.getValue();
			ctx.model.prepare(observable.getValue()
										.getEntity());
			if (ctx.model.getId() != null) {
				ctx.getNode(MENU_DELETE_BUTTON_ID)
					.setVisible(Boolean.TRUE);
			} else {
				ctx.getNode(MENU_DELETE_BUTTON_ID)
					.setVisible(Boolean.FALSE);
			}
			ctx.formHandler.fillForm(ctx);
		}
	}

	/**
	 * The change listener for the {@link MenuEntryModel} type
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 5, 2015
	 */
	public static class MenuEntryChangeListener implements ChangeListener<MenuEntryModel> {

		private final FormContext<MenuEntryModel>	ctx;
		private final MenuEntryEventControl			control;

		/**
		 * @param ctx
		 * @param control
		 */
		public MenuEntryChangeListener(FormContext<MenuEntryModel> ctx, MenuEntryEventControl control) {
			super();
			Objects.requireNonNull(ctx);
			Objects.requireNonNull(control);

			this.ctx = ctx;
			this.control = control;
		}

		@Override
		public void changed(ObservableValue<? extends MenuEntryModel> observable, MenuEntryModel oldValue, MenuEntryModel newValue) {
			if (observable.getValue() == null) {
				return;
			}

			ctx.model = observable.getValue();
			ctx.model.prepare(observable.getValue()
										.getEntity());
			control.handleMenuLoad(ctx);
			if (ctx.model.getId() == null) {
				ctx.getNode(MENU_ENTRY_DELETE_BUTTON_ID)
					.setVisible(Boolean.FALSE);
			} else {
				ctx.getNode(MENU_ENTRY_DELETE_BUTTON_ID)
					.setVisible(Boolean.TRUE);
			}
			ctx.formHandler.fillForm(ctx);
		}
	}

	/**
	 * @param scene
	 */
	public MenuTabViewHandler(final Scene scene) {
		Objects.requireNonNull(scene);

		this.menuEntryControl = new MenuEntryEventControl();
		this.menuEntryFormHandler = new FormHandler<MenuEntryModel>();
		this.menuEntryFormHandler.init();
		this.menuEntryCtx = new FormContext<MenuEntryModel>("menu-entry-form", menuEntryFormHandler, new MenuEntryModel(), scene);

		this.menuControl = new MenuEventControl();
		this.menuFormHandler = new FormHandler<MenuModel>();
		this.menuFormHandler.init();
		this.menuCtx = new FormContext<MenuModel>("menu-form", menuFormHandler, new MenuModel(), scene);
	}

	@Override
	public String getId() {
		return "tab-menu";
	}

	@Override
	public Tab createNode() {
		// edit mode toggle
		final ToggleGroup group = new ToggleGroup();
		final List<RadioButton> buttons = createEditModeToggle(group);
		final GridPane editOptionsGrid = new GridPane();
		editOptionsGrid.setId(getId() + "-edit-options-grid");
		editOptionsGrid.setHgap(10);
		editOptionsGrid.setVgap(10);
		for (int i = 0; i < buttons.size(); i++) {
			editOptionsGrid.add(buttons.get(i), i, 0);
		}
		group.getToggles()
				.get(0)
				.setSelected(Boolean.TRUE);

		// Form message text
		final TextFlow menuEntryFlow = new TextFlow();
		menuEntryFlow.setId(MENU_ENTRY_FORM_MESSAGE);
		menuEntryFlow.setStyle("-fx-font-size: 20pt");
		menuEntryCtx.putNode(MENU_ENTRY_FORM_MESSAGE, menuEntryFlow);

		final TextFlow menuFlow = new TextFlow();
		menuEntryFlow.setId(MENU_ENTRY_FORM_MESSAGE);
		menuEntryFlow.setStyle("-fx-font-size: 20pt");
		menuCtx.putNode(MENU_FORM_MESSAGE, menuFlow);

		// menu form grid
		final GridPane menuGrid = new GridPane();
		menuGrid.setId(getId() + "-menu-grid");
		menuGrid.add(menuFlow, 0, 0);
		menuGrid.add(createMenuChoiceBox(menuCtx), 0, 1);
		menuGrid.add(menuCtx.formHandler.generateFormGrid(menuCtx), 0, 2);
		menuGrid.add(createMenuButtonGroup(menuCtx), 0, 3);
		menuGrid.setUserData(menuCtx);
		menuGrid.setHgap(10);
		menuGrid.setVgap(10);

		final GridPane menuEntryGrid = new GridPane();
		menuEntryGrid.setId(getId() + "-menu-entry-grid");
		menuEntryGrid.add(menuEntryFlow, 0, 0);
		menuEntryGrid.add(createMenuEntryChoiceBox(menuEntryCtx), 0, 1);
		menuEntryGrid.add(menuEntryCtx.formHandler.generateFormGrid(menuEntryCtx), 0, 2);
		menuEntryGrid.add(createMenuEntryButtonGroup(menuEntryCtx), 0, 3);
		menuEntryGrid.setUserData(menuEntryCtx);
		menuEntryGrid.setHgap(10);
		menuEntryGrid.setVgap(10);

		// content grid
		final GridPane gridPane = new GridPane();
		gridPane.setId(getId() + "-content");
		gridPane.add(editOptionsGrid, 0, 0);
		gridPane.add(menuGrid, 0, 1);
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		// TODO: Set change listener on edit mode choice;
		group.selectedToggleProperty()
				.addListener(new ChangeModeListener(gridPane, menuGrid, menuEntryGrid, menuControl, menuEntryControl));

		final Tab menuTab = new Tab();
		menuTab.setId("tab-menu");
		menuTab.setText("Menus");
		menuTab.setClosable(Boolean.FALSE);

		menuTab.setContent(gridPane);

		return menuTab;
	}

	/**
	 * Creates the edit toogle for the edit mode.
	 * 
	 * @param group
	 *            the toogle group which holds the toggles.
	 * @return the list of toggles
	 */
	private List<RadioButton> createEditModeToggle(final ToggleGroup group) {
		Objects.requireNonNull(group);

		final List<RadioButton> buttons = new ArrayList<>();

		for (EditMode editMode : EditMode.values()) {
			final RadioButton button = new RadioButton();
			button.setId(getId() + "-edit-mode-" + editMode.name()
															.toLowerCase());
			button.setText(editMode.label);
			button.setToggleGroup(group);
			button.setUserData(editMode);
			buttons.add(button);
		}

		return buttons;
	}

	/**
	 * Creates a menu choice box.
	 * 
	 * @param ctx
	 *            the form context
	 * @return the choice box
	 */
	private ChoiceBox<MenuModel> createMenuChoiceBox(final FormContext<MenuModel> ctx) {
		Objects.requireNonNull(ctx);

		final ObservableList<MenuModel> menus = FXCollections.observableArrayList(Arrays.asList(new MenuModel()));

		final ChoiceBox<MenuModel> menuChoice = new ChoiceBox<>(menus);
		menuChoice.setUserData(ctx);
		menuChoice.getSelectionModel()
					.selectedItemProperty()
					.addListener(new MenuChangeListener(ctx, menuControl));
		menuChoice.setConverter(new MenuModelConverter());
		menuChoice.setPrefWidth(400);

		ctx.putObserable(MENU_SELECTION_KEY, menus);
		ctx.putNode(MENU_SELECTION_KEY, menuChoice);

		return menuChoice;
	}

	/**
	 * Creates a menu entry choice box
	 * 
	 * @param ctxthe
	 *            form context
	 * @return the choice box
	 */
	private ChoiceBox<MenuEntryModel> createMenuEntryChoiceBox(final FormContext<MenuEntryModel> ctx) {
		Objects.requireNonNull(ctx);

		final ObservableList<MenuEntryModel> entries = FXCollections.observableArrayList(Arrays.asList(new MenuEntryModel()));

		final ChoiceBox<MenuEntryModel> menuEntryChoice = new ChoiceBox<>(entries);
		menuEntryChoice.setUserData(ctx);
		menuEntryChoice.getSelectionModel()
						.selectedItemProperty()
						.addListener(new MenuEntryChangeListener(ctx, menuEntryControl));
		menuEntryChoice.setConverter(new MenuEntryModelConverter());
		menuEntryChoice.setPrefWidth(400);

		ctx.putObserable(MENU_ENTRY_SELECTION_KEY, entries);
		ctx.putNode(MENU_ENTRY_SELECTION_KEY, menuEntryChoice);

		return menuEntryChoice;
	}

	/**
	 * Creates the menu button group
	 * 
	 * @param ctx
	 *            the form context
	 * @return the creates button group
	 */
	private GridPane createMenuButtonGroup(final FormContext<MenuModel> ctx) {
		Objects.requireNonNull(ctx);

		final GridPane gridPane = new GridPane();
		gridPane.setId(getId() + "-button-grid");
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		final Button newButton = new Button();
		newButton.setText("Neues Menu");
		newButton.setId(MENU_NEW_BUTTON_ID);
		newButton.setUserData(ctx);
		newButton.setOnAction(menuControl::newAction);

		final Button saveButton = new Button();
		saveButton.setText("Speichern");
		saveButton.setId(MENU_SAVE_BUTTON_ID);
		saveButton.setUserData(ctx);
		saveButton.setOnAction(menuControl::saveMenu);

		final Button deleteButton = new Button();
		deleteButton.setId(MENU_DELETE_BUTTON_ID);
		deleteButton.setText("Löschen");
		deleteButton.setUserData(ctx);
		deleteButton.setOnAction(menuControl::deleteMenu);

		gridPane.add(newButton, 0, 0);
		gridPane.add(saveButton, 1, 0);
		gridPane.add(deleteButton, 2, 0);

		// register in context
		ctx.putNode(MENU_NEW_BUTTON_ID, newButton);
		ctx.putNode(MENU_SAVE_BUTTON_ID, saveButton);
		ctx.putNode(MENU_DELETE_BUTTON_ID, deleteButton);

		return gridPane;
	}

	/**
	 * Creates the menu entry button group
	 * 
	 * @param ctx
	 *            the from context
	 * @return the created button group
	 */
	private GridPane createMenuEntryButtonGroup(final FormContext<MenuEntryModel> ctx) {
		Objects.requireNonNull(ctx);

		final GridPane gridPane = new GridPane();
		gridPane.setId(getId() + "-button-grid");
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		final Button newButton = new Button();
		newButton.setText("Neuer Menu Eintrag");
		newButton.setId(MENU_ENTRY_NEW_BUTTON_ID);
		newButton.setUserData(ctx);
		newButton.setOnAction(menuEntryControl::newAction);

		final Button saveButton = new Button();
		saveButton.setText("Speichern");
		saveButton.setId(MENU_ENTRY_SAVE_BUTTON_ID);
		saveButton.setUserData(ctx);
		saveButton.setOnAction(menuEntryControl::saveMenuEntry);

		final Button deleteButton = new Button();
		deleteButton.setId(MENU_ENTRY_DELETE_BUTTON_ID);
		deleteButton.setText("Löschen");
		deleteButton.setUserData(ctx);
		deleteButton.setOnAction(menuEntryControl::deleteMenuEntry);

		gridPane.add(newButton, 0, 0);
		gridPane.add(saveButton, 1, 0);
		gridPane.add(deleteButton, 2, 0);

		// register in context
		ctx.putNode(MENU_ENTRY_NEW_BUTTON_ID, newButton);
		ctx.putNode(MENU_ENTRY_SAVE_BUTTON_ID, saveButton);
		ctx.putNode(MENU_ENTRY_DELETE_BUTTON_ID, deleteButton);

		return gridPane;
	}

	@Override
	public void initHandler() {
		menuControl.handleMenuReload(menuCtx);
		menuCtx.formHandler.fillForm(menuCtx);
		((ChoiceBox<MenuModel>) menuCtx.getNode(MENU_SELECTION_KEY)).getSelectionModel()
																	.select(menuCtx.model);
		menuCtx.getNode(MENU_DELETE_BUTTON_ID)
				.setVisible(Boolean.FALSE);
	}

}
