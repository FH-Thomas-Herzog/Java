package at.fh.ooe.swe4.campina.jpa;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import at.fh.ooe.swe4.campina.jpa.api.AbstractEntity;

/**
 * The menu entry
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class MenuEntry extends AbstractEntity<Integer> implements Comparable<MenuEntry> {

	private static final long	serialVersionUID	= -1771596592654083392L;

	private Integer				ordinal;
	private String				label;
	private BigDecimal			price;
	private Menu				menu;
	private Set<Order>			orders				= new HashSet<>();

	/**
	 * 
	 */
	public MenuEntry() {
		super();
	}

	/**
	 * @param id
	 */
	public MenuEntry(Integer id) {
		super(id);
	}

	/**
	 * @param id
	 * @param ordinal
	 * @param label
	 * @param price
	 * @param menu
	 */
	public MenuEntry(Integer id, Integer ordinal, String label, BigDecimal price, Menu menu) {
		this(id);
		this.ordinal = ordinal;
		this.label = label;
		this.price = price;
		this.menu = menu;
	}

	@Override
	public Integer getId() {
		return _getId();
	}

	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@Override
	public int compareTo(MenuEntry o) {
		if ((getId() == null) && (o.getId() == null)) {
			return 0;
		} else if (getId() == null) {
			return -1;
		} else if (o.getId() == null) {
			return 1;
		} else {
			return getId().compareTo(o.getId());
			// return day.compareTo(o.getDay());
		}
	}
}
