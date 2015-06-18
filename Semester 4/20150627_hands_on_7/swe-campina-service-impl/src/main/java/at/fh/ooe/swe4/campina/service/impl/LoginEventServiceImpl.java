package at.fh.ooe.swe4.campina.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.util.Objects;

import at.fh.ooe.swe4.campina.jdbc.LoginEvent;
import at.fh.ooe.swe4.campina.jdbc.api.ConnectionManager;
import at.fh.ooe.swe4.campina.service.api.LoginEventService;

public class LoginEventServiceImpl extends UnicastRemoteObject implements LoginEventService {

	private final ConnectionManager	connectionManager;

	public LoginEventServiceImpl(ConnectionManager connectionManager) throws RemoteException {
		super();
		Objects.requireNonNull(connectionManager);

		this.connectionManager = connectionManager;
	}

	private static final long	serialVersionUID	= 8411763221630572942L;

	@Override
	public LoginEvent save(LoginEvent entity) {
		// System.out.println("hello save login event");
		return null;
	}

	@Override
	public void delete(LoginEvent entity) {
		// System.out.println("hello delete login event");
	}
}
