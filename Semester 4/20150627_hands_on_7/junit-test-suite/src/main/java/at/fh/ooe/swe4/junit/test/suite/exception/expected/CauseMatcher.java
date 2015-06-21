package at.fh.ooe.swe4.junit.test.suite.exception.expected;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class CauseMatcher extends BaseMatcher<Throwable> {

	private final Class<? extends Throwable>	type;
	private final String						expectedMessage;

	public CauseMatcher(Class<? extends Throwable> type, String expectedMessage) {
		this.type = type;
		this.expectedMessage = expectedMessage;
	}

	@Override
	public boolean matches(Object item) {
		System.out.println(item);
		return type.isAssignableFrom(item.getClass());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("expects type ")
					.appendValue(type);
	}
}
