package at.fh.ooe.swe4.campina.dao.api;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

import at.fh.ooe.swe4.campina.persistence.api.ConnectionManager;

/**
 * This class is the base class for entity DAOs which are accessible via RMI and
 * therefore remote DAOs.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 19, 2015
 */
public class AbstractRemoteDao extends UnicastRemoteObject {

	private static final long			serialVersionUID	= -6317106515178653903L;

	protected final ConnectionManager	connectionManager;

	/**
	 * @param connectionManager
	 * @throws RemoteException
	 * @throws NullPointerException
	 *             if the connection manager is null
	 */
	public AbstractRemoteDao(ConnectionManager connectionManager) throws RemoteException {
		super();
		Objects.requireNonNull(connectionManager);

		this.connectionManager = connectionManager;
	}

}
