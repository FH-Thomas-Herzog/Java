package at.fh.ooe.swe4.fx.campina.jpa;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import at.fh.ooe.swe4.fx.campina.view.login.model.LoginModel;

public class EntityCache {

	public static Set<User>	userCache	= new HashSet<>(100);

	private EntityCache() {
		userCache.add(new User(1, "Thomas", "Herzog", "t.herzog@bla.bla"));
		userCache.add(new User(2, "Hugo", "Fichtner", "h.fichtner@bla.bla"));
		userCache.add(new User(3, "Christian", "Beikov", "c.beickov@bla.bla"));
		userCache.add(new User(4, "Rainer", "Rudolf", "r.rudolf@bla.bla"));
		userCache.add(new User(5, "Bernd", "Maier", "b.maier@bla.bla"));
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
