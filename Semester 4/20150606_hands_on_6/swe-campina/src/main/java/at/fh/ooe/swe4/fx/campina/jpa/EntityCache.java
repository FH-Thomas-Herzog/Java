package at.fh.ooe.swe4.fx.campina.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import at.fh.ooe.swe4.fx.campina.jpa.constants.Day;
import at.fh.ooe.swe4.fx.campina.view.admin.login.model.LoginModel;

public class EntityCache {

	public static Set<User>			userCache		= new HashSet<>(100);
	public static Set<Menu>			menuCache		= new HashSet<>(100);
	public static Set<MenuEntry>	menuEntryCache	= new HashSet<>(100);
	public static Set<Order>		orderCache		= new HashSet<>(100);

	static {
		final User u1 = new User(1, "Thomas", "Herzog", "t.herzog@bla.bla");
		final User u2 = new User(2, "Hugo", "Fichtner", "h.fichtner@bla.bla");
		final User u3 = new User(3, "Christian", "Beikov", "c.beickov@bla.bla");
		final User u4 = new User(4, "Rainer", "Rudolf", "r.rudolf@bla.bla");
		final User u5 = new User(5, "Bernd", "Maier", "b.maier@bla.bla");

		final Menu m1 = new Menu(1, Day.MONDAY, "Fisch Tag");
		final Menu m2 = new Menu(1, Day.WEDNESDAY, "Steak Tag");
		final Menu m3 = new Menu(1, Day.THURSDAY, "Nudel Tag");

		final MenuEntry me1 = new MenuEntry(1, 1, "Forelle", BigDecimal.ONE, m1);
		final MenuEntry me2 = new MenuEntry(2, 2, "Zander", BigDecimal.ONE, m1);
		m1.getEntries()
			.add(me1);
		m1.getEntries()
			.add(me2);

		final Order o1 = new Order(1, u1, me1, Calendar.getInstance(), Calendar.getInstance());
		me1.getOrders()
			.add(o1);
		u1.getOrders()
			.add(o1);
		final Order o2 = new Order(2, u2, me2, Calendar.getInstance(), Calendar.getInstance());
		me1.getOrders()
			.add(o1);
		u2.getOrders()
			.add(o1);

		userCache.add(u1);
		userCache.add(u2);
		userCache.add(u3);
		userCache.add(u4);
		userCache.add(u5);

		menuEntryCache.add(me1);
		menuEntryCache.add(me2);

		menuCache.add(m1);
		menuCache.add(m2);
		menuCache.add(m3);

		orderCache.add(o1);
		orderCache.add(o2);
	}

	private EntityCache() {
	}

	public static final User isValidLogin(final LoginModel model) {
		Objects.requireNonNull(model);

		final Iterator<User> it = userCache.iterator();
		boolean valid = Boolean.FALSE;
		User user = null;
		while ((it.hasNext()) && (!valid)) {
			user = it.next();
			valid = ((model.getUsername().equals(user.getUsername())) && (model.getPassword().equals(user.getPassword())));
		}

		return valid ? user : null;
	}

	public static final Menu byMenuId(final Serializable id) {
		Objects.requireNonNull(id);

		for (Menu menu : menuCache) {
			if (id.equals(menu.getId())) {
				return menu;
			}
		}
		throw new IllegalArgumentException("id does not correspond to an menu");
	}

	public static final void deleteForMenuId(final Serializable id) {
		Objects.requireNonNull(id);

		final Iterator<MenuEntry> it = menuEntryCache.iterator();
		while (it.hasNext()) {
			final MenuEntry entry = it.next();
			if (entry.getId()
						.equals(id)) {
				it.remove();
			}
		}
	}
}
