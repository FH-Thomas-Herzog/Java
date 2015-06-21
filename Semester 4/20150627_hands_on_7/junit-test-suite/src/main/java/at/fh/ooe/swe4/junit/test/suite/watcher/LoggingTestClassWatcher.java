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
public class LoggingTestClassWatcher extends AbstractLoggerWatcher {

	private String className;

	public LoggingTestClassWatcher(final Level level) {
		super(level);
	}

	@Override
	public Statement apply(Statement base, Description description) {
		log = Logger.getLogger(description.getTestClass());
		return super.apply(base, description);
	}

	@Override
	protected void starting(Description description) {
		className = description.getTestClass()
								.getName();
		log.log(level, StringUtils.repeat("#", SEPARATOR_REPEATIONS));
		log.log(level, new StringBuilder(String.format(LOG_FORMAT, "started:")).append(className)
																				.toString());
		log.log(level, StringUtils.repeat("#", SEPARATOR_REPEATIONS));
	}

	@Override
	protected void succeeded(Description description) {
		log.log(level, StringUtils.repeat("-", SEPARATOR_REPEATIONS));
		log.log(level, StringUtils.repeat("#", SEPARATOR_REPEATIONS));
		log.log(level, new StringBuilder(String.format(LOG_FORMAT, "succeeded:")).append(className)
																					.toString());
		log.log(level, StringUtils.repeat("#", SEPARATOR_REPEATIONS));
	};

	@Override
	protected void failed(Throwable e, Description description) {
		log.log(level, StringUtils.repeat("-", SEPARATOR_REPEATIONS));
		log.log(level, StringUtils.repeat("#", SEPARATOR_REPEATIONS));
		log.log(level, new StringBuilder(String.format(LOG_FORMAT, "failed:")).append(className)
																				.toString(), e);
		log.log(level, StringUtils.repeat("#", SEPARATOR_REPEATIONS));
	};
}
