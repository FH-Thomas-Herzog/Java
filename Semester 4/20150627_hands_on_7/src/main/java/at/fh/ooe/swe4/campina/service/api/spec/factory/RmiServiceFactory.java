package at.fh.ooe.swe4.campina.service.api.spec.factory;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface specifies a rmi service factory which creates instances for
 * the given name.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 15, 2015
 */
public interface RmiServiceFactory extends Remote {

	/**
	 * Creates a service for the given name.
	 * 
	 * @param interfaze
	 *            the service name which must be exactly the Interface name of
	 *            the service.
	 * @return the service instance
	 * @throws IllegalArgumentException
	 *             if the name does not map to a service interface
	 */
	public <T extends Remote> T createService(Class<T> interfaze) throws RemoteException;
}
