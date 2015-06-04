package at.fh.ooe.swe4.fx.campina.jpa;

import java.math.BigDecimal;

public class MenuEntry extends AbstractEntity<Integer> implements Comparable<MenuEntry> {

	private static final long	serialVersionUID	= -1771596592654083392L;

	private Integer				ordinal;
	private String				label;
	private BigDecimal			price;
	private Menu				menu;

	public MenuEntry() {
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

	@Override
	public int compareTo(MenuEntry o) {
		return ordinal.compareTo(o.getOrdinal());
	}
}
