package at.fh.ooe.swe4.campina.fx.view.admin.order.part;

import java.util.Arrays;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import at.fh.ooe.swe4.campina.fx.view.admin.menu.part.MenuTabViewHandler.EditMode;
import at.fh.ooe.swe4.campina.fx.view.admin.order.control.OrderEventHandler;
import at.fh.ooe.swe4.campina.fx.view.admin.order.model.OrderModel;
import at.fh.ooe.swe4.campina.fx.view.api.ViewHandler;

/**
 * The view handler for the menu and menu entry view.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class OrderTabViewHandler implements ViewHandler<Tab> {

	private final Scene							scene;
	private final OrderEventHandler				orderControl;
	private final ObservableList<OrderModel>	orderList;

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
	 * @param scene
	 */
	public OrderTabViewHandler(final Scene scene) {
		Objects.requireNonNull(scene);

		this.scene = scene;
		this.orderList = FXCollections.observableArrayList();
		orderControl = new OrderEventHandler();
	}

	@Override
	public String getId() {
		return "tab-menu";
	}

	@Override
	public Tab createNode() {
		final TableColumn<OrderModel, String> orderDate = new TableColumn<>("Bestelldatum");
		orderDate.setMinWidth(200);
		orderDate.setCellValueFactory(new PropertyValueFactory<OrderModel, String>("orderDate"));
		final TableColumn<OrderModel, String> username = new TableColumn<>("Benutzer");
		username.setMinWidth(130);
		username.setCellValueFactory(new PropertyValueFactory<OrderModel, String>("username"));
		final TableColumn<OrderModel, String> menuCard = new TableColumn<>("Speisekarte");
		menuCard.setMinWidth(130);
		menuCard.setCellValueFactory(new PropertyValueFactory<OrderModel, String>("menuLabel"));
		final TableColumn<OrderModel, String> menu = new TableColumn<>("Menu");
		menu.setMinWidth(130);
		menu.setCellValueFactory(new PropertyValueFactory<OrderModel, String>("menuEntryLabel"));

		final TableView<OrderModel> tableView = new TableView<>();
		tableView.getColumns()
					.addAll(Arrays.asList(orderDate, username, menuCard, menu));
		tableView.setEditable(Boolean.FALSE);
		tableView.getSelectionModel()
					.setCellSelectionEnabled(Boolean.FALSE);
		orderControl.reloadOrders(orderList);
		tableView.setItems(orderList);

		final Tab orderTab = new Tab();
		orderTab.setText("Bestellungen");
		orderTab.setClosable(Boolean.FALSE);
		orderTab.setId(getId() + "-order-tab");
		orderTab.setContent(tableView);

		return orderTab;
	}

	@Override
	public void initHandler() {
	}

}
