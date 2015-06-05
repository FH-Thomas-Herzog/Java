package at.fh.ooe.swe4.fx.campina.jpa;

import java.util.SortedSet;
import java.util.TreeSet;

import at.fh.ooe.swe4.fx.campina.jpa.api.AbstractEntity;
import at.fh.ooe.swe4.fx.campina.jpa.constants.Day;

/**
 * The menu.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
public class Menu extends AbstractEntity<Integer> implements Comparable<Menu> {

	private static final long		serialVersionUID	= 7569033478037865818L;

	private Day						day;
	private String					label;
	private SortedSet<MenuEntry>	entires				= new TreeSet<>();

	/**
	 * 
	 */
	public Menu() {
		super();
	}

	/**
	 * 
	 * @param id
	 */
	public Menu(Integer id) {
		super(id);
	}

	/**
	 * @param id
	 * @param day
	 * @param label
	 */
	public Menu(Integer id, Day day, String label) {
		super(id);
		this.day = day;
		this.label = label;
	}

	@Override
	public Integer getId() {
		return _getId();
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public SortedSet<MenuEntry> getEntries() {
		return entires;
	}

	public void setEntires(SortedSet<MenuEntry> entires) {
		this.entires = entires;
	}

	@Override
	public int compareTo(Menu o) {
		return day.compareTo(o.getDay());
	}
}
