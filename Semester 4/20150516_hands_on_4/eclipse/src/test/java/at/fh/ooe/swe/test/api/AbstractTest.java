package at.fh.ooe.swe.test.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;
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

	protected final Logger LOG;
	/**
	 * This watcher watches the invocation of an Test class.
	 */
	@Rule
	public final TestWatcher methodInvocationLogger = new LoggingTestInvocationWatcher();

	/**
	 * This watcher is used for logging the test execution.
	 */
	@Rule
	public TestWatcher testClassInvocationLogger = new LoggingTestClassWatcher();

	protected static final int SIZE = 4;
	protected static final int CONTAINER_SIZE = (int) Math.pow(SIZE, 2);

	public AbstractTest() {
		super();
		LOG = Logger.getLogger(this.getClass());
	}

	/**
	 * Creates a container with the given size and set it with integer values
	 * from 1 -> size.<br>
	 * This container will contain an null element.
	 * 
	 * @param size
	 *            the size of the container
	 * @return the created container
	 */
	protected List<Integer> createContainer(final int size) {
		final List<Integer> container = new ArrayList<Integer>(size);
		IntStream.range(1, (size + 1)).forEach(new IntConsumer() {
			@Override
			public void accept(int value) {
				container.add(value);
			}
		});

		return container;
	}
}
