package at.fh.ooe.swe4.junit.test.suite.watcher;

import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * This class is the test method invocation logger watcher.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
public class LoggingTestClassWatcher extends TestWatcher {

	private Logger log;
	private static final String LOG_FORMAT = "%1$-12s";

	public LoggingTestClassWatcher() {
	}

	@Override
	public Statement apply(Statement base, Description description) {
		log = Logger.getLogger(description.getTestClass());
		return super.apply(base, description);
	}

	@Override
	protected void starting(Description description) {
		log.info(String.format(LOG_FORMAT, "started:") + description.getMethodName());
	}

	@Override
	protected void succeeded(Description description) {
		log.info(String.format(LOG_FORMAT, "succeeded:") + description.getMethodName());
	};

	@Override
	protected void failed(Throwable e, Description description) {
		log.error(String.format(LOG_FORMAT, "failed:") + description.getMethodName(), e);
	};
}
