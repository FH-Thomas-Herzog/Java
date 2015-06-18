package at.fh.ooe.swe4.campina.service.api;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public class AbstractService extends UnicastRemoteObject {

	private static final long			serialVersionUID	= -6317106515178653903L;

	protected final ConnectionManager	connectionManager;

	public AbstractService(ConnectionManager connectionManager) throws RemoteException {
		super();
		Objects.requireNonNull(connectionManager);

		this.connectionManager = connectionManager;
	}

}
