package at.fh.ooe.swe4.campina.service.impl.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.log4j.Logger;

import at.fh.ooe.swe4.campina.service.api.spec.rmi.RmiServer;

/**
 * This is the rmi server implementation.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 15, 2015
 */
public class RmiServerImpl implements RmiServer {

	private static final long	serialVersionUID		= 164219497553939223L;

	private Registry			serviceRegistry			= null;
	private int					port;
	private Set<String>			registeredServiceNames	= new HashSet<>();
	private static final Logger	log						= Logger.getLogger(RmiServerImpl.class);

	/**
	 * @param port
	 *            TODO
	 */
	public RmiServerImpl(int port) {
		if ((port <= 1024) || (port >= 65535)) {
			throw new IllegalArgumentException("port is invalid: " + port);
		}

		this.port = port;
	}

	@Override
	public void start() throws RemoteException {
		if (serviceRegistry != null) {
			throw new IllegalStateException("The rmi server is already started");
		}
		log.info("Starting service regsitry on: 'rmi://localhost:" + port + "'");
		serviceRegistry = LocateRegistry.createRegistry(port);
		log.info("Service regsitry registered");
	}

	@Override
	public void stop() throws RemoteException {
		if (serviceRegistry == null) {
			throw new IllegalStateException("The rmi server is not started");
		}
		try {
			for (String name : registeredServiceNames) {
				serviceRegistry.unbind(name);
				log.info("Service '" + name + "' unbound");
			}
		} catch (NotBoundException e) {
			throw new IllegalStateException("Should not happen :(");
		}
		serviceRegistry = null;
		port = -1;
		registeredServiceNames.clear();
	}

	@Override
	public <T extends Remote> void bindService(T service, Class<T> interfaceClazz) throws RemoteException {
		Objects.requireNonNull(service);
		Objects.requireNonNull(interfaceClazz);

		final String name = interfaceClazz.getSimpleName();
		if (registeredServiceNames.contains(name)) {
			log.info("Service '" + name + "' will get bound");
		}
		try {
			Remote rmiService = UnicastRemoteObject.exportObject(service, port);
			serviceRegistry.bind(name, rmiService);
			registeredServiceNames.add(name);
		} catch (AlreadyBoundException e) {
			throw new IllegalStateException("Should not happen :(");
		}

	}

	@Override
	public <T extends Remote> void unbindService(final Class<T> interfaceClazz) throws RemoteException {
		Objects.requireNonNull(interfaceClazz);

		if (registeredServiceNames.contains(interfaceClazz.getSimpleName())) {
			log.info("Unbind Service: " + interfaceClazz.getName() + "");
			try {
				serviceRegistry.unbind(interfaceClazz.getSimpleName());
			} catch (NotBoundException e) {
				throw new IllegalStateException("Should not happen :(");
			}
		}
	}
	// ###########################################################
	// Private section
	// ###########################################################

}
