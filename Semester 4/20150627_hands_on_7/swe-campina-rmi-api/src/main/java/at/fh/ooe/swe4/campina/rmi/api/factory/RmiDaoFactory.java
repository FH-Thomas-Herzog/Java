package at.fh.ooe.swe4.campina.rmi.api.factory;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface specifies a RMI DAO factory which creates instances for the
 * given name.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 15, 2015
 */
public interface RmiDaoFactory extends Remote {

	/**
	 * Creates a DAO for the given name.
	 * 
	 * @param interfaze
	 *            the DAO name which must be exactly the Interface name of the
	 *            DAO.
	 * @return the DAO instance
	 * @throws IllegalArgumentException
	 *             if the name does not map to a DAO interface
	 */
	public <T extends Remote> T createDao(Class<T> interfaze) throws RemoteException;
}
