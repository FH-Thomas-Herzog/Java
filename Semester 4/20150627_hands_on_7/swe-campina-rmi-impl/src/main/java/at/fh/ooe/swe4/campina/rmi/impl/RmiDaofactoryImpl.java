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
import at.fh.ooe.swe4.campina.rmi.api.factory.RmiDaoFactory;

/**
 * This class creates the remote object for the client so that multiple instance
 * can be used on the client site. Therefore the produced beans are considered
 * to be stateless implemented beans, because the client is not supposed to
 * expect anything more but an stateless bean.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 17, 2015
 */
public class RmiDaofactoryImpl extends UnicastRemoteObject implements RmiDaoFactory {

	private static final long								serialVersionUID	= 9162336931859659503L;

	private Timer											cleanupTimer;
	private final Map<Class<Remote>, SortedSet<DaoWrapper>>	daoCache			= new HashMap<>(100, (float) 0.75);
	private final DbMetadata								databaseMeta;
	private final ConnectionManager							connectionManager;
	private final Object									lockObject			= new Object();

	private static final Logger								log					= Logger.getLogger(RmiDaofactoryImpl.class);
	private static final String								IMPL_NAME_SPACE		= "at.fh.ooe.swe4.campina.dao.impl.";

	private static final int								CLIENT_COUNT		= 10;

	/**
	 * Helper class for wrapping the cached DAO for caching purposes.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 17, 2015
	 */
	private static final class DaoWrapper implements Comparable<DaoWrapper> {

		public int			clientCount;
		public final Remote	instance;

		/**
		 * @param instance
		 */
		public DaoWrapper(Remote instance) {
			super();
			this.instance = instance;
			this.clientCount = 0;
		}

		/**
		 * Ensures that the lowest client count DAO is the first element of the
		 * sorted container
		 */
		@Override
		public int compareTo(DaoWrapper o) {
			return Integer.valueOf(clientCount)
							.compareTo(o.clientCount);
		}
	}

	/**
	 * Cleanup time task which ensures that not more than 10 references are
	 * bound to one DAO. If the service has the client count breached then the
	 * reference to this DAO will be released.
	 * 
	 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
	 * @date Jun 17, 2015
	 */
	private static final class CleanupTask extends TimerTask {

		private final RmiDaofactoryImpl	factory;

		/**
		 * @param factory
		 */
		public CleanupTask(RmiDaofactoryImpl factory) {
			super();
			this.factory = factory;
		}

		@Override
		public void run() {
			// ensures that clients have to wait for cleanup finished
			synchronized (factory.lockObject) {
				int count = 0;
				for (Entry<Class<Remote>, SortedSet<DaoWrapper>> entry : factory.daoCache.entrySet()) {
					final Iterator<DaoWrapper> it = entry.getValue()
															.iterator();
					while (it.hasNext()) {
						final DaoWrapper service = it.next();
						if (service.clientCount >= CLIENT_COUNT) {
							it.remove();
							count++;
						}
					}
				}
				log.info("Finished DAO cache cleanup (" + count + " removed)");
			}
		}
	}

	/**
	 * @throws RemoteException
	 *             if remote object could not be created
	 */
	public RmiDaofactoryImpl(final DbMetadata databaseMeta) throws RemoteException {
		Objects.requireNonNull(databaseMeta);

		this.cleanupTimer = new Timer();
		this.cleanupTimer.schedule(new CleanupTask(this), 0, (int) (10 * 1000));
		this.databaseMeta = databaseMeta;

		// constructor tries to establish a connection and therefore validates
		// the provided metadata
		this.connectionManager = new ConnectionManagerImpl(databaseMeta);
	}

	@Override
	public <T extends Remote> T createDao(Class<T> interfaze) throws RemoteException {
		synchronized (this.lockObject) {

			DaoWrapper dao;
			SortedSet<DaoWrapper> typedDaoCache = daoCache.get(interfaze);
			// -- No instance cached --
			if (typedDaoCache == null) {
				log.info("Init DAO cache: '" + interfaze.getSimpleName() + "'");
				typedDaoCache = new TreeSet<DaoWrapper>();
				daoCache.put((Class<Remote>) interfaze, typedDaoCache);
			}
			// -- empty cache --
			if ((typedDaoCache.isEmpty()) || (typedDaoCache.first().clientCount >= CLIENT_COUNT)) {
				typedDaoCache.add(new DaoWrapper(newDaoInstance(interfaze)));
				log.info("Caching new DAO: '" + interfaze.getSimpleName() + "'");
			}

			// -- get first bean with lowest client count --
			dao = typedDaoCache.first();

			dao.clientCount++;
			log.info("Retrieved cached DAO: '" + interfaze.getSimpleName() + "'");

			if (dao.instance == null) {
				throw new RemoteException("Creation of DAO: '" + interfaze.getName() + "' failed");
			}

			return (T) dao.instance;
		}
	}

	/**
	 * Creates a new DAO instance.
	 * 
	 * @param interfaze
	 *            the DAO interface class
	 * @return the DAO instance or null if the creation failed
	 */
	private <T extends Remote> T newDaoInstance(final Class<T> interfaze) {
		Objects.requireNonNull(interfaze);

		T instance = null;
		try {
			Class<T> clazz = (Class<T>) Class.forName(IMPL_NAME_SPACE + interfaze.getSimpleName() + "Impl");
			instance = clazz.getConstructor(ConnectionManager.class)
							.newInstance(this.connectionManager);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Could create service instance", e);
		}

		return instance;
	}
}
