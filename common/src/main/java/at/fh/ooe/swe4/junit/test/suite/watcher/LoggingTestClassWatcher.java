package at.fh.ooe.swe4.junit.test.suite.watcher;

import org.apache.commons.lang.StringUtils;
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

	private int classNameLength = 15;
	private String className;
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
		className = description.getTestClass().getName();
		classNameLength += className.length();
		log.info(StringUtils.repeat("-", classNameLength));
		log.info(String.format(LOG_FORMAT, "started:") + className);
		log.info(StringUtils.repeat("-", classNameLength));
	}

	@Override
	protected void succeeded(Description description) {
		log.info(StringUtils.repeat("-", classNameLength));
		log.info(String.format(LOG_FORMAT, "succeeded:") + className);
		log.info(StringUtils.repeat("-", classNameLength));
	};

	@Override
	protected void failed(Throwable e, Description description) {
		log.info(StringUtils.repeat("-", classNameLength));
		log.error(String.format(LOG_FORMAT, "failed:") + className, e);
		log.info(StringUtils.repeat("-", classNameLength));
	};
}
