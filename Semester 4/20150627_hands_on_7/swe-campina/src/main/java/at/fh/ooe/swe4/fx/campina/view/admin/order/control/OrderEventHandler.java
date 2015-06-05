package at.fh.ooe.swe4.fx.campina.view.admin.order.control;

import java.util.Objects;

import javafx.collections.ObservableList;
import at.fh.ooe.swe4.fx.campina.jpa.EntityCache;
import at.fh.ooe.swe4.fx.campina.jpa.Order;
import at.fh.ooe.swe4.fx.campina.view.admin.order.model.OrderModel;

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
