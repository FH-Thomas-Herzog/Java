package at.fh.ooe.swe4.campina.jpa;

import java.util.Calendar;

import at.fh.ooe.swe4.campina.jpa.api.AbstractEntity;

/**
 * The order on the database.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class Order extends AbstractEntity<Integer> {

	private static final long	serialVersionUID	= -4218683353334647328L;

	private User				user;
	private MenuEntry			menuEntry;
	private Calendar			orderDate;
	private Calendar			collectDate;

	public Order(Integer id, User user, MenuEntry menuEntry, Calendar orderDate, Calendar collectDate) {
		super(id);
		this.user = user;
		this.menuEntry = menuEntry;
		this.orderDate = orderDate;
		this.collectDate = collectDate;
	}

	/**
	 * 
	 */
	public Order() {
	}

	/**
	 * @param id
	 */
	public Order(Integer id) {
		super(id);
	}

	@Override
	public Integer getId() {
		return _getId();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MenuEntry getMenuEntry() {
		return menuEntry;
	}

	public void setMenuEntry(MenuEntry menuEntry) {
		this.menuEntry = menuEntry;
	}

	public Calendar getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Calendar orderDate) {
		this.orderDate = orderDate;
	}

	public Calendar getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Calendar collectDate) {
		this.collectDate = collectDate;
	}
}
