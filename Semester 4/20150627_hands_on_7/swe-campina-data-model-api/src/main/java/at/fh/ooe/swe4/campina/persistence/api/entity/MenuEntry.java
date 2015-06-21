package at.fh.ooe.swe4.campina.persistence.api.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Table;

import at.fh.ooe.swe4.campina.persistence.api.AbstractEntity;

/**
 * The menu entry
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
@Table(name = "MENU_ENTRY", schema = "CAMPINA")
public class MenuEntry extends AbstractEntity implements Comparable<MenuEntry> {

	private static final long	serialVersionUID	= -1771596592654083392L;

	private Integer				ordinal;
	private String				label;
	private BigDecimal			price;
	private Menu				menu;

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

	@Column(name = "ORDINAL")
	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	@Column(name = "LABEL")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "PRICE")
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "MENU_ID")
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
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
