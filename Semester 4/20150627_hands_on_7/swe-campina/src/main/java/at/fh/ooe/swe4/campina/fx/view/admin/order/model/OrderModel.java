package at.fh.ooe.swe4.campina.fx.view.admin.order.model;

import org.apache.commons.lang.time.DateFormatUtils;

import at.fh.ooe.swe4.campina.fx.view.api.AbstractViewModel;
import at.fh.ooe.swe4.campina.jpa.Order;
import at.fh.ooe.swe4.campina.jpa.User;

/**
 * The view model backing the {@link Order} entity.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class OrderModel extends AbstractViewModel<Integer, Order> {

	/**
	 * 
	 */
	public OrderModel() {
		super();
	}

	/**
	 * @param entity
	 */
	public OrderModel(Order entity) {
		super(entity);
	}

	@Override
	public void reset() {
		prepare(new Order());
	}

	// Resolved by TableColumn
	public String getOrderDate() {
		return DateFormatUtils.ISO_DATETIME_FORMAT.format(getEntity().getOrderDate());
	}

	public String getUsername() {
		final User user = getEntity().getUser();
		return new StringBuilder(user.getLastName()).append(", ")
													.append(user.getFirstName())
													.toString();
	}

	public String getMenuLabel() {
		return getEntity().getMenuEntry()
							.getMenu()
							.getLabel();
	}

	public String getMenuEntryLabel() {
		return getEntity().getMenuEntry()
							.getLabel();
	}
}
