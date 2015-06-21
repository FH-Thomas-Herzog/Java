package at.fh.ooe.swe4.campina.rmi.api.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is the specification for the RMI server.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 15, 2015
 */
public interface RmiServer extends Serializable {

	/**
	 * Registers an bean. The RMI server must have been started before.
	 * 
	 * @param bean
	 *            the bean instance to register
	 * @param interfaceClazz
	 *            the class of the current instance which will be used for name
	 *            resolving.
	 * @throws RemoteException
	 *             if the bean could not be registered
	 * @throws NullPointerException
	 *             if the bean or the interface are null
	 * @throws IllegalStateException
	 *             if the RMI hasn't been started before
	 */
	public <T extends Remote> void bindBean(T bean, Class<T> interfaceClazz) throws RemoteException;

	/**
	 * Unbinds a registered bean if the service is bind to the backed RMI
	 * server.
	 * 
	 * @param interfaceClazz
	 *            the registered bean interface class
	 * @throws RemoteException
	 *             if the RMI server is not started
	 * @throws NullPointerException
	 *             if the interface class is null
	 */
	public <T extends Remote> void unbindBean(Class<T> interfaceClazz) throws RemoteException;

	/**
	 * SWtarts the RMI server.
	 * 
	 * @throws RemoteException
	 *             if the RMI server could not be started
	 * @throws NullPointerException
	 *             if the URL is null
	 */
	public void start() throws RemoteException;

	/**
	 * Stops the RMI server and ends all connections, therefore client could
	 * experience {@link RemoteException}.
	 * 
	 * @throws RemoteException
	 *             if the rmi server could not be started
	 */
	public void stop() throws RemoteException;
}
