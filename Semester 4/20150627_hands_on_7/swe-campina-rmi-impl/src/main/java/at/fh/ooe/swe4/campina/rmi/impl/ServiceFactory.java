package at.fh.ooe.swe4.campina.rmi.impl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.SortedSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import at.fh.ooe.swe4.campina.persistence.api.ConnectionManager;
import at.fh.ooe.swe4.campina.persistence.impl.ConnectionManagerImpl;
import at.fh.ooe.swe4.campina.persistence.impl.ConnectionManagerImpl.DbMetadata;
import at.fh.ooe.swe4.campina.rmi.api.factory.RmiServiceFactory;

/**
 * This class creates the remote object for the client so that multiple instance
 * can be used on the client site. Therefore the produced beans are considered
 * to be stateless implemented beans, because the client is not supposed not
 * expect anything more but an stateless bean.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 17, 2015
 */
public class ServiceFactory extends UnicastRemoteObject implements RmiServiceFactory {

	private static final long									serialVersionUID	= 9162336931859659503L;

	private Timer												cleanupTimer;
	private final Map<Class<Remote>, SortedSet<ServiceWrapper>>	beanCache			= new HashMap<>(100, (float) 0.75);
	private final DbMetadata									databaseMeta;
	private final Object										lockObject			= new Object();

	private static final Logger									log					= Logger.getLogger(ServiceFactory.class);
	private static final String									IMPL_NAME_SPACE		= "at.fh.ooe.swe4.campina.dao.impl.";

	private static final int									CLIENT_COUNT		= 10;

	/**
	 * Helper class for wrapping the cached services for caching purposes.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 17, 2015
	 */
	private static final class ServiceWrapper implements Comparable<ServiceWrapper> {

		public int			clientCount;
		public final Remote	instance;

		/**
		 * @param instance
		 */
		public ServiceWrapper(Remote instance) {
			super();
			this.instance = instance;
			this.clientCount = 0;
		}

		/**
		 * Ensures that the lowes client count service is the first element of
		 * the sorted container
		 */
		@Override
		public int compareTo(ServiceWrapper o) {
			return Integer.valueOf(clientCount)
							.compareTo(o.clientCount);
		}
	}

	/**
	 * Cleanup time task which ensures that not more than 10 references are
	 * bound to one service. If the service has the client count breached then
	 * the reference to this service will be released.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 17, 2015
	 */
	private static final class CleanupTask extends TimerTask {

		private final ServiceFactory	factory;

		/**
		 * @param factory
		 */
		public CleanupTask(ServiceFactory factory) {
			super();
			this.factory = factory;
		}

		@Override
		public void run() {
			// ensures that clients have to wait for cleanup finished
			synchronized (factory.lockObject) {
				log.info("Starting cleanup");
				int count = 0;
				for (Entry<Class<Remote>, SortedSet<ServiceWrapper>> entry : factory.beanCache.entrySet()) {
					final Iterator<ServiceWrapper> it = entry.getValue()
																.iterator();
					while (it.hasNext()) {
						final ServiceWrapper service = it.next();
						if (service.clientCount >= CLIENT_COUNT) {
							it.remove();
							count++;
						}
					}
				}
				log.info("Finished cleanup (" + count + " removed)");
			}
		}
	}

	/**
	 * @throws RemoteException
	 *             if remote object could not be created
	 */
	public ServiceFactory(final DbMetadata databaseMeta) throws RemoteException {
		Objects.requireNonNull(databaseMeta);

		this.cleanupTimer = new Timer();
		this.cleanupTimer.schedule(new CleanupTask(this), 0, (int) (1 * 1000));
		this.databaseMeta = databaseMeta;

		// constructor tries to establish a connection and therefore validates
		// the provided metadata
		new ConnectionManagerImpl(databaseMeta);
	}

	@Override
	public <T extends Remote> T createService(Class<T> interfaze) throws RemoteException {
		synchronized (this.lockObject) {

			ServiceWrapper service;
			SortedSet<ServiceWrapper> cache = beanCache.get(interfaze);
			// -- No instance cached --
			if (cache == null) {
				log.info("Init cache: '" + interfaze.getSimpleName() + "'");
				cache = new TreeSet<ServiceWrapper>();
				beanCache.put((Class<Remote>) interfaze, cache);
			}
			// -- empty cache --
			if ((cache.isEmpty()) || (cache.first().clientCount >= CLIENT_COUNT)) {
				cache.add(new ServiceWrapper(newServiceInstance(interfaze)));
				log.info("Caching new instance: '" + interfaze.getSimpleName() + "'");
			}

			// -- get first bean with lowest client count --
			service = cache.first();

			service.clientCount++;
			log.info("Retrieved cached instance: '" + interfaze.getSimpleName() + "'");

			if (service.instance == null) {
				throw new RemoteException("Creation of service: '" + interfaze.getName() + "' failed");
			}

			return (T) service.instance;
		}
	}

	/**
	 * Creates a new service instance.
	 * 
	 * @param interfaze
	 *            the service interface class
	 * @return the service instance or null if the creation failed
	 */
	private <T extends Remote> T newServiceInstance(final Class<T> interfaze) {
		Objects.requireNonNull(interfaze);

		T instance = null;
		try {
			Class<T> clazz = (Class<T>) Class.forName(IMPL_NAME_SPACE + interfaze.getSimpleName() + "Impl");
			instance = clazz.getConstructor(ConnectionManager.class)
							.newInstance(new ConnectionManagerImpl(databaseMeta));
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Could create service instance", e);
		}

		return instance;
	}
}
