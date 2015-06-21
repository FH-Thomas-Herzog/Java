package at.fh.ooe.swe4.campina.fx.rmi.service.locator;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Objects;

import at.fh.ooe.swe4.campina.rmi.api.factory.RmiDaoFactory;

public class DaoLocator {

	private static final String				url	= "rmi://localhost:50555/";
	private static final RmiDaoFactory	factory;

	static {
		final String binding = url + RmiDaoFactory.class.getSimpleName();
		try {
			factory = (RmiDaoFactory) Naming.lookup(binding);
		} catch (Exception e) {
			throw new IllegalArgumentException("This service factory is not available via rmi: " + binding, e);
		}
	}

	public DaoLocator() {
		// TODO Auto-generated constructor stub
	}

	public static <T extends Remote> T getDao(final Class<T> interfaze) {
		Objects.requireNonNull(interfaze);
		try {
			return factory.createDao(interfaze);
		} catch (RemoteException e) {
			throw new IllegalStateException("Could not retrieve dao");
		}
	}
}
