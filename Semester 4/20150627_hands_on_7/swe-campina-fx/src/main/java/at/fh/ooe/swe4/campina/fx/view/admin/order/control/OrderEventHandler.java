package at.fh.ooe.swe4.campina.fx.view.admin.order.control;

import java.util.Objects;

import javafx.collections.ObservableList;
import at.fh.ooe.swe4.campina.fx.view.admin.order.model.OrderModel;
import at.fh.ooe.swe4.campina.jdbc.Order;

public class OrderEventHandler {

	public OrderEventHandler() {
		// TODO Auto-generated constructor stub
	}

	public void reloadOrders(ObservableList<OrderModel> list) {
		Objects.requireNonNull(list);

		list.clear();
		for (Order order : EntityCache.orderCache) {
			list.add(new OrderModel(order));
		}
	}
}
