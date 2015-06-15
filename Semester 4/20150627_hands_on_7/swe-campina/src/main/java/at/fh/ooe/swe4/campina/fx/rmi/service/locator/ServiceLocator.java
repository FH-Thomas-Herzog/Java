package at.fh.ooe.swe4.campina.fx.rmi.service.locator;

import java.rmi.Naming;
import java.rmi.Remote;
import java.util.Objects;

public class ServiceLocator {

	private static final String	url	= "rmi://localhost:50555/";

	public ServiceLocator() {
		// TODO Auto-generated constructor stub
	}

	public static <T extends Remote> T getService(final Class<T> interfaze) {
		Objects.requireNonNull(interfaze);

		final String binding = url + interfaze.getSimpleName();
		try {
			return (T) Naming.lookup(binding);
		} catch (Exception e) {
			throw new IllegalArgumentException("This service is not available via rmi: " + binding, e);
		}
	}
}
