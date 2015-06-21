package at.fh.ooe.swe4.campina.test.dao.api;

import java.rmi.RemoteException;
import java.util.Objects;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class RemoteDetailMatcher extends BaseMatcher<Throwable> {

	private final Class<? extends Throwable>	clazz;

	/**
	 * @param clazz
	 */
	public RemoteDetailMatcher(Class<? extends Throwable> clazz) {
		super();
		Objects.requireNonNull(clazz);

		this.clazz = clazz;
	}

	@Override
	public boolean matches(Object item) {
		return ((item != null)
				&& ((((RemoteException) item).detail) != null))
				&& (clazz.isInstance(((RemoteException) item).detail));
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Detail of RemoteException is not equal to '" + clazz.getName() + "'");

	}
}
