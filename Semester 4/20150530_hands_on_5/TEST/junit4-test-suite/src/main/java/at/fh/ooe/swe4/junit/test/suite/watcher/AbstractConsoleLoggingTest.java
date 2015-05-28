package at.fh.ooe.swe4.junit.test.suite.watcher;

import org.apache.log4j.Level;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * Abstract test class which logs the class and test method invocations.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date May 17, 2015
 */
public abstract class AbstractConsoleLoggingTest {

	@ClassRule
	public static LoggingTestClassWatcher classWatcher = new LoggingTestClassWatcher(Level.INFO);
	@Rule
	public LoggingTestInvocationWatcher testWatcher = new LoggingTestInvocationWatcher(Level.INFO);

}
