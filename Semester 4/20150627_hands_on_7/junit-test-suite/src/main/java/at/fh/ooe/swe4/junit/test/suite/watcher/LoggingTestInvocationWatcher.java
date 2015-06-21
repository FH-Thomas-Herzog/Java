package at.fh.ooe.swe4.junit.test.suite.watcher;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import at.fh.ooe.swe4.junit.test.suite.watcher.api.AbstractLoggerWatcher;

/**
 * This class is the test method invocation logger watcher.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Apr 26, 2015
 */
public class LoggingTestInvocationWatcher extends AbstractLoggerWatcher {

	public LoggingTestInvocationWatcher(final Level level) {
		super(level);
	}

	@Override
	public Statement apply(Statement base, Description description) {
		log = Logger.getLogger(description.getTestClass());
		return super.apply(base, description);
	}

	@Override
	protected void starting(Description description) {
		log.log(level, StringUtils.repeat("-", SEPARATOR_REPEATIONS));
		log.log(level, String.format(LOG_FORMAT, "started:") + description.getMethodName());
	}

	@Override
	protected void succeeded(Description description) {
		log.log(level, String.format(LOG_FORMAT, "succeeded:") + description.getMethodName());
	};

	@Override
	protected void failed(Throwable e, Description description) {
		log.log(level, String.format(LOG_FORMAT, "failed:") + description.getMethodName(), e);
	};

}
