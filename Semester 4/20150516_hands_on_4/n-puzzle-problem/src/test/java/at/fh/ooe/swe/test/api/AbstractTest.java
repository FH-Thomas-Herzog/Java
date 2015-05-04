package at.fh.ooe.swe.test.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

import at.fh.ooe.swe4.junit.test.suite.watcher.LoggingTestClassWatcher;
import at.fh.ooe.swe4.junit.test.suite.watcher.LoggingTestInvocationWatcher;

/**
 * This is the base test which provides common resources for the test
 * implementation.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
public abstract class AbstractTest {

	protected final Logger log;
	/**
	 * This watcher watches the invocation of an Test class.
	 */
	@Rule
	public final TestWatcher methodInvocationLogger = new LoggingTestInvocationWatcher(Level.INFO);

	/**
	 * This watcher is used for logging the test execution.
	 */
	@ClassRule
	public static TestWatcher testClassInvocationLogger = new LoggingTestClassWatcher(Level.INFO);

	/**
	 * Default constructor which initializes the logger with the current test
	 * class.
	 */
	public AbstractTest() {
		super();
		log = Logger.getLogger(this.getClass());
	}

	/**
	 * Creates a container with the given size and sets integer values i in
	 * order from 1 to size.<br>
	 * <b>This container will contain no null element.<b>
	 * 
	 * @param size
	 *            the size of the container
	 * @return the created container
	 */
	protected List<Integer> createContainer(final int size) {
		final List<Integer> container = new ArrayList<Integer>(size);
		IntStream.range(1, (size + 1))
					.forEachOrdered(new IntConsumer() {
						@Override
						public void accept(int value) {
							container.add(value);
						}
					});

		return container;
	}
}
