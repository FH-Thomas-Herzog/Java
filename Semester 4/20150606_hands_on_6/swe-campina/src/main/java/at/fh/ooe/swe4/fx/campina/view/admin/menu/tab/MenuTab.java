package at.fh.ooe.swe4.fx.campina.view.admin.menu.tab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.StringConverter;
import at.fh.ooe.swe4.fx.campina.component.builder.impl.FormHandler;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.control.MenuEntryFormControl;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.control.MenuFormControl;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.model.MenuEntryModel;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.model.MenuEntryModel.MenuEntryModelConverter;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.model.MenuModel;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.model.MenuModel.MenuModelConverter;
import at.fh.ooe.swe4.fx.campina.view.api.ScenePart;
import at.fh.ooe.swe4.fx.campina.view.context.FormContext;

public class MenuTab implements ScenePart<Tab> {

	/**
	 * The form handler for the menu entry form
	 */
	private final FormHandler<MenuEntryModel>	menuEntryFormHandler;
	/**
	 * The menu entry controller
	 */
	private final MenuEntryFormControl			menuEntryFormControl;
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
	private final MenuFormControl				menuFormControl;
	/**
	 * The form context for the menu entry form
	 */
	final FormContext<MenuModel>				menuCtx;

	private ChangeListener<EditMode>			editModeChangeListener;

	// ###########################################################################
	// Ids of nodes and form backed model class
	// ###########################################################################
	public static final String					MENU_ENTRY_FORM_MESSAGE		= "tab-menu-entry-form-message";
	public static final String					MENU_FORM_MESSAGE			= "tab-menu-form-message";
	public static final String					EDIT_OPTION_ID				= "tab-menu-edit-option";
	public static final String					MENU_SELECTION_KEY			= "tab-menu-menu-selection-data";
	public static final String					MENU_ENTRY_SELECTION_KEY	= "tab-menu-menu-entry-selection-data";

	public static enum EditMode {
		MENU("Menus"),
		MENU_ENTRY("Menu Einträge");

		public final String	label;

		private EditMode(final String label) {
			this.label = label;
		}
	}

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

	public static class ChangeModeListener implements ChangeListener<Toggle> {

		private final GridPane				parent;
		private final GridPane				menuGroup;
		private final GridPane				menuEntryGroup;
		private final MenuFormControl		menuControl;
		private final MenuEntryFormControl	menuEntryControl;

		public ChangeModeListener(GridPane parent, GridPane menuGroup, GridPane menuEntryGroup, MenuFormControl menuControl,
				MenuEntryFormControl menuEntryControl) {
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
			if ((newValue == null) || ((oldValue != null) && (oldValue.equals(newValue)))) {
				return;
			}

			parent.getChildren()
					.remove(menuEntryGroup);
			parent.getChildren()
					.remove(menuGroup);

			switch ((EditMode) newValue.getUserData()) {
			case MENU:
				parent.add(menuGroup, 0, 1);
				final FormContext<MenuModel> menuCtx = (FormContext<MenuModel>) menuGroup.getUserData();
				menuCtx.model.reset();
				menuControl.handleMenuLoad(menuCtx);
				((ChoiceBox<MenuModel>) menuCtx.getNode(MENU_SELECTION_KEY)).getSelectionModel()
																			.select(menuCtx.model);
				menuCtx.formHandler.fillForm(menuCtx);
				break;
			case MENU_ENTRY:
				parent.add(menuEntryGroup, 0, 1);
				final FormContext<MenuEntryModel> menuEntryCtx = (FormContext<MenuEntryModel>) menuEntryGroup.getUserData();
				menuEntryCtx.model.reset();
				menuEntryControl.handleMenuEntryLoad(menuEntryCtx);
				((ChoiceBox<MenuEntryModel>) menuEntryCtx.getNode(MENU_ENTRY_SELECTION_KEY)).getSelectionModel()
																							.select(menuEntryCtx.model);
				menuEntryControl.handleMenuLoad(menuEntryCtx);
				menuEntryCtx.formHandler.fillForm(menuEntryCtx);
				break;
			default:
				throw new IllegalArgumentException("'EditMode#" + ((EditMode) newValue.getUserData()).name() + "' node suppported");
			}
		}
	}

	public static class MenuChangeListener implements ChangeListener<MenuModel> {

		private final FormContext<MenuModel>	ctx;
		private final MenuFormControl			control;

		public MenuChangeListener(FormContext<MenuModel> ctx, MenuFormControl control) {
			super();
			Objects.requireNonNull(ctx);
			Objects.requireNonNull(control);

			this.ctx = ctx;
			this.control = control;
		}

		@Override
		public void changed(ObservableValue<? extends MenuModel> observable, MenuModel oldValue, MenuModel newValue) {
			if ((newValue == null) || ((oldValue != null) && (oldValue.equals(newValue)))) {
				return;
			}

			ctx.model = newValue;
			ctx.model.prepare(newValue.getEntity());
			if (ctx.model.getId() != null) {
				ctx.formHandler.fillForm(ctx);
			} else {
				ctx.model = newValue;

				// Could Reload menu here

				ctx.formHandler.fillForm(ctx);
			}
		}
	}

	public static class MenuEntryChangeListener implements ChangeListener<MenuEntryModel> {

		private final FormContext<MenuEntryModel>	ctx;
		private final MenuEntryFormControl			control;

		public MenuEntryChangeListener(FormContext<MenuEntryModel> ctx, MenuEntryFormControl control) {
			super();
			Objects.requireNonNull(ctx);
			Objects.requireNonNull(control);

			this.ctx = ctx;
			this.control = control;
		}

		@Override
		public void changed(ObservableValue<? extends MenuEntryModel> observable, MenuEntryModel oldValue, MenuEntryModel newValue) {
			if ((newValue == null) || ((oldValue != null) && (oldValue.equals(newValue)))) {
				return;
			}

			ctx.model = newValue;
			ctx.model.prepare(newValue.getEntity());
			if (ctx.model.getId() == null) {
				control.handleMenuLoad(ctx);
				ctx.formHandler.fillForm(ctx);
			} else {
				ctx.model = newValue;
				control.handleMenuLoad(ctx);

				// Could Reload menu here

				ctx.formHandler.fillForm(ctx);
			}
		}
	}

	public MenuTab(final Scene scene) {
		Objects.requireNonNull(scene);

		this.menuEntryFormControl = new MenuEntryFormControl();
		this.menuEntryFormHandler = new FormHandler<MenuEntryModel>();
		this.menuEntryFormHandler.init();
		this.menuEntryCtx = new FormContext<MenuEntryModel>("menu-entry-form", menuEntryFormHandler, new MenuEntryModel(), scene);

		this.menuFormControl = new MenuFormControl();
		this.menuFormHandler = new FormHandler<MenuModel>();
		this.menuFormHandler.init();
		this.menuCtx = new FormContext<MenuModel>("menu-form", menuFormHandler, new MenuModel(), scene);
	}

	@Override
	public String getId() {
		return "tab-menu";
	}

	@Override
	public Tab create() {
		// edit mode toggle
		final ToggleGroup group = new ToggleGroup();
		final List<RadioButton> buttons = createEditModeToggle(group);
		final GridPane editOptionsGrid = new GridPane();
		editOptionsGrid.setId(getId() + "-edit-options-grid");
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
		final TextFlow menuFlow = new TextFlow();
		menuEntryFlow.setId(MENU_ENTRY_FORM_MESSAGE);
		menuEntryFlow.setStyle("-fx-font-size: 20pt");

		// menu form grid
		final GridPane menuGrid = new GridPane();
		menuGrid.setId(getId() + "-menu-grid");
		menuGrid.add(menuFlow, 0, 0);
		menuGrid.add(createMenuChoiceBox(menuCtx), 0, 1);
		menuGrid.add(menuCtx.formHandler.generateFormGrid(menuCtx), 0, 2);
		menuGrid.add(new Text("Menu Buttons here"), 0, 3);
		menuGrid.setUserData(menuCtx);

		final GridPane menuEntryGrid = new GridPane();
		menuEntryGrid.setId(getId() + "-menu-entry-grid");
		menuEntryGrid.add(menuEntryFlow, 0, 0);
		menuEntryGrid.add(createMenuEntryChoiceBox(menuEntryCtx), 0, 1);
		menuEntryGrid.add(menuEntryCtx.formHandler.generateFormGrid(menuEntryCtx), 0, 2);
		menuEntryGrid.add(new Text("Menu Entry Buttons here"), 0, 3);
		menuEntryGrid.setUserData(menuEntryCtx);

		// content grid
		final GridPane gridPane = new GridPane();
		gridPane.setId(getId() + "-content");
		gridPane.add(editOptionsGrid, 0, 0);
		gridPane.add(menuGrid, 0, 1);

		// TODO: Set change listener on edit mode choice;
		group.selectedToggleProperty()
				.addListener(new ChangeModeListener(gridPane, menuGrid, menuEntryGrid, menuFormControl, menuEntryFormControl));

		final Tab menuTab = new Tab();
		menuTab.setId("tab-menu");
		menuTab.setText("Menus");
		menuTab.setClosable(Boolean.FALSE);

		menuTab.setContent(gridPane);

		return menuTab;
	}

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

	private ChoiceBox<MenuModel> createMenuChoiceBox(final FormContext<MenuModel> ctx) {
		Objects.requireNonNull(ctx);

		final ObservableList<MenuModel> menus = FXCollections.observableArrayList(Arrays.asList(new MenuModel()));

		final ChoiceBox<MenuModel> menuChoice = new ChoiceBox<>(menus);
		menuChoice.setUserData(ctx);
		menuChoice.getSelectionModel()
					.selectedItemProperty()
					.addListener(new MenuChangeListener(ctx, menuFormControl));
		menuChoice.setConverter(new MenuModelConverter());
		menuChoice.setPrefWidth(400);

		ctx.putObserable(MENU_SELECTION_KEY, menus);
		ctx.putNode(MENU_SELECTION_KEY, menuChoice);

		return menuChoice;
	}

	private ChoiceBox<MenuEntryModel> createMenuEntryChoiceBox(final FormContext<MenuEntryModel> ctx) {
		Objects.requireNonNull(ctx);

		final ObservableList<MenuEntryModel> entries = FXCollections.observableArrayList(Arrays.asList(new MenuEntryModel()));

		final ChoiceBox<MenuEntryModel> menuEntryChoice = new ChoiceBox<>(entries);
		menuEntryChoice.setUserData(ctx);
		menuEntryChoice.getSelectionModel()
						.selectedItemProperty()
						.addListener(new MenuEntryChangeListener(ctx, menuEntryFormControl));
		menuEntryChoice.setConverter(new MenuEntryModelConverter());
		menuEntryChoice.setPrefWidth(400);

		ctx.putObserable(MENU_ENTRY_SELECTION_KEY, entries);
		ctx.putNode(MENU_ENTRY_SELECTION_KEY, menuEntryChoice);

		return menuEntryChoice;
	}

	@Override
	public void init() {
		menuCtx.formHandler.fillForm(menuCtx);
		menuFormControl.handleMenuLoad(menuCtx);
		((ChoiceBox<MenuModel>) menuCtx.getNode(MENU_SELECTION_KEY)).getSelectionModel()
																	.select(menuCtx.model);
	}

}
