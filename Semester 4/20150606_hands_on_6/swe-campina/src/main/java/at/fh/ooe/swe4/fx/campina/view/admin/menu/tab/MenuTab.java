package at.fh.ooe.swe4.fx.campina.view.admin.menu.tab;

import java.util.Objects;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import at.fh.ooe.swe4.fx.campina.component.builder.impl.FormHandler;
import at.fh.ooe.swe4.fx.campina.view.admin.login.model.LoginModel;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.control.MenuEntryFormControl;
import at.fh.ooe.swe4.fx.campina.view.admin.menu.model.MenuEntryModel;
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
	final FormContext<MenuEntryModel>			fCtx;

	// ###########################################################################
	// Ids of nodes and form backed model class
	// ###########################################################################
	public static final String					FORM_MESSAGE	= "tab-menu-form-message";

	public MenuTab(final Scene scene) {
		Objects.requireNonNull(scene);

		this.menuEntryFormControl = new MenuEntryFormControl();
		this.menuEntryFormHandler = new FormHandler<MenuEntryModel>();
		this.menuEntryFormHandler.init();
		final MenuEntryModel model = new MenuEntryModel();
		model.reset();
		this.fCtx = new FormContext<MenuEntryModel>("menu-entry-form", menuEntryFormHandler, model, scene);
	}

	@Override
	public String getId() {
		return "tab-menu";
	}

	@Override
	public Tab create() {

		// Form message text
		final TextFlow flow = new TextFlow();
		flow.setId(FORM_MESSAGE);

		// form
		final Node form = fCtx.formHandler.generateFormGrid(fCtx);

		// grid
		final GridPane gridPane = new GridPane();
		gridPane.setId(getId() + "-content");
		gridPane.add(flow, 0, 0);
		gridPane.add(form, 0, 1);

		final Tab menuTab = new Tab();
		menuTab.setId("tab-menu");
		menuTab.setText("Menus");
		menuTab.setClosable(Boolean.FALSE);

		menuTab.setContent(gridPane);

		return menuTab;
	}

	@Override
	public void init() {
		fCtx.formHandler.fillForm(fCtx);
	}

}
