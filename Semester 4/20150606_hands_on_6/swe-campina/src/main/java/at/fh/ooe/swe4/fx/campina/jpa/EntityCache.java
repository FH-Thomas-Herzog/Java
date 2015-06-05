package at.fh.ooe.swe4.fx.campina.jpa;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import at.fh.ooe.swe4.fx.campina.jpa.constants.Day;
import at.fh.ooe.swe4.fx.campina.view.admin.login.model.LoginModel;

public class EntityCache {

	public static Set<User>	userCache	= new HashSet<>(100);
	public static Set<Menu>	menuCache	= new HashSet<>(100);

	static {
		userCache.add(new User(1, "Thomas", "Herzog", "t.herzog@bla.bla"));
		userCache.add(new User(2, "Hugo", "Fichtner", "h.fichtner@bla.bla"));
		userCache.add(new User(3, "Christian", "Beikov", "c.beickov@bla.bla"));
		userCache.add(new User(4, "Rainer", "Rudolf", "r.rudolf@bla.bla"));
		userCache.add(new User(5, "Bernd", "Maier", "b.maier@bla.bla"));

		final Menu m1 = new Menu(1, Day.MONDAY, "Fisch Tag");
		final Menu m2 = new Menu(1, Day.WEDNESDAY, "Steak Tag");
		final Menu m3 = new Menu(1, Day.THURSDAY, "Nudel Tag");

		m1.getEntries()
			.add(new MenuEntry(1, 1, "Forelle", BigDecimal.ONE, m1));
		m1.getEntries()
			.add(new MenuEntry(2, 2, "Zander", BigDecimal.ONE, m1));

		menuCache.add(m1);
		menuCache.add(m2);
		menuCache.add(m3);
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
}
