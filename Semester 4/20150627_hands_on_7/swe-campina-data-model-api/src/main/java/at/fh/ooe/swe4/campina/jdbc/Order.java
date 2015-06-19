package at.fh.ooe.swe4.campina.jdbc;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Table;

import at.fh.ooe.swe4.campina.jdbc.api.AbstractEntity;

/**
 * The order on the database.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
@Table(name = "ORDER", schema = "CAMPINA")
public class Order extends AbstractEntity {

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

	@Column(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "MENU_ENTRY_ID")
	public MenuEntry getMenuEntry() {
		return menuEntry;
	}

	public void setMenuEntry(MenuEntry menuEntry) {
		this.menuEntry = menuEntry;
	}

	@Column(name = "ORDER_DATE")
	public Calendar getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Calendar orderDate) {
		this.orderDate = orderDate;
	}

	@Column(name = "COLLECT")
	public Calendar getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Calendar collectDate) {
		this.collectDate = collectDate;
	}
}
