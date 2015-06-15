package at.fh.ooe.swe4.campina.service.api.spec.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is the specification for the rmi server.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 15, 2015
 */
public interface RmiServer extends Serializable {

	/**
	 * Registers an service. The rmi server must have been started before.
	 * 
	 * @param service
	 *            the service instance to register
	 * @param interfaceClazz
	 *            the class of the current isntance which will be used for name
	 *            resolving.
	 * @throws RemoteException
	 *             if the service could not be registered
	 * @throws NullPointerException
	 *             if the service or the interfcae are null
	 * @throws IllegalStateException
	 *             if the rmi hasn't been started before
	 */
	public <T extends Remote> void bindService(T service, Class<T> interfaceClazz) throws RemoteException;

	/**
	 * Unbinds a registered service if the service is bind to the backed rmi
	 * server.
	 * 
	 * @param interfaceClazz
	 *            the registered service interface class
	 * @throws RemoteException
	 *             if the rmi service is not started
	 * @throws NullPointerException
	 *             if the interface class is null
	 */
	public <T extends Remote> void unbindService(Class<T> interfaceClazz) throws RemoteException;

	/**
	 * SWtarts the rmi server and hosts the services on the given url.
	 * 
	 * @throws RemoteException
	 *             if the rmi server could not be started
	 * @throws NullPointerException
	 *             if the url is null
	 */
	public void start() throws RemoteException;

	/**
	 * Stops the rmi server and ends all connections, therefore client yould
	 * experience {@link RemoteException}.
	 * 
	 * @throws RemoteException
	 *             if the rmi server could not be started
	 */
	public void stop() throws RemoteException;

}
