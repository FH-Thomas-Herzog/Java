package at.fh.ooe.swe.test.api;

import org.apache.log4j.Logger;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * This is the base test which provides common resources for the test
 * implementation.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
public abstract class AbstractTest {

	public final Logger LOG;

	private static final String LOG_FORMAT = "%1$-12s";

	public AbstractTest() {
		super();
		LOG = Logger.getLogger(this.getClass());
	}

	/**
	 * This watcher watches the invocation of an Test class.
	 */
	@ClassRule
	public static final TestWatcher w = new TestWatcher() {
		private final Logger LOG = Logger.getLogger(AbstractTest.class);

		protected void starting(Description description) {
			LOG.info(String.format(LOG_FORMAT, "STARTED: " + description.getClassName()));
		};

		protected void succeeded(Description description) {
			LOG.info(String.format(LOG_FORMAT, "FINISHED: " + description.getClassName()));
		};
	};

	/**
	 * This watcher is used for logging the test execution.
	 */
	@Rule
	public TestWatcher watcher = new TestWatcher() {
		@Override
		protected void starting(Description description) {
			LOG.info(String.format(LOG_FORMAT, "started:") + description.getMethodName());
		}

		@Override
		protected void succeeded(Description description) {
			LOG.info(String.format(LOG_FORMAT, "succeeded:") + description.getMethodName());
		};

		@Override
		protected void failed(Throwable e, Description description) {
			LOG.error(String.format(LOG_FORMAT, "failed:") + description.getMethodName(), e);
		};

	};

}
