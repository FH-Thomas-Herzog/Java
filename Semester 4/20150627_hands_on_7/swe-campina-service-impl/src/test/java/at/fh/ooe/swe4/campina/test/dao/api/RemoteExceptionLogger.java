package at.fh.ooe.swe4.campina.test.dao.api;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * This class logs the detail of the thrown remoteException which the client
 * would also do.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 21, 2015
 */
public class RemoteExceptionLogger extends TestWatcher {

	private static final Logger	log	= Logger.getLogger(RemoteExceptionLogger.class);

	public RemoteExceptionLogger() {
	}

	@Override
	protected void failed(Throwable e, Description description) {
		super.failed(e, description);
		if (e != null) {
			if (e instanceof RemoteException) {
				final RemoteException re = (RemoteException) e;
				if (re.detail != null) {
					log.error("RemoteException with detail: ", e);
				}
			}
		}
	}
}
