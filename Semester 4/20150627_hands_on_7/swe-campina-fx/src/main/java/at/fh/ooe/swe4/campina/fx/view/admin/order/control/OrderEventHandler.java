package at.fh.ooe.swe4.campina.fx.view.admin.order.control;

import java.util.List;
import java.util.Objects;
import java.lang.IllegalStateException;

import javafx.collections.ObservableList;
import at.fh.ooe.swe4.campina.dao.api.OrderDao;
import at.fh.ooe.swe4.campina.fx.rmi.service.locator.DaoLocator;
import at.fh.ooe.swe4.campina.fx.view.admin.order.model.OrderModel;
import at.fh.ooe.swe4.campina.persistence.api.entity.Order;

public class OrderEventHandler {

	public List<Order>	orders	= null;

	public OrderEventHandler() {
		// TODO Auto-generated constructor stub
	}

	public void reloadOrders(ObservableList<OrderModel> list) {
		Objects.requireNonNull(list);

		list.clear();
		try {
			final List<Order> orders = DaoLocator.getDao(OrderDao.class)
													.getAll();
			for (Order order : orders) {
				final OrderModel model = new OrderModel();
				model.prepare(order);
				list.add(model);
			}
		} catch (Exception e) {
			throw new IllegalStateException("Could not load orders", e);
		}
	}

	private void prepareTestData() {

	}
}
