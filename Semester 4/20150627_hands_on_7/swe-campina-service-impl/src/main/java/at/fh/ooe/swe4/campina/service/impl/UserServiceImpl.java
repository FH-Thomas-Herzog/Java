package at.fh.ooe.swe4.campina.service.impl;

import java.rmi.RemoteException;

import at.fh.ooe.swe4.campina.jdbc.User;
import at.fh.ooe.swe4.campina.jdbc.api.ConnectionManager;
import at.fh.ooe.swe4.campina.service.api.AbstractService;
import at.fh.ooe.swe4.campina.service.api.UserService;

public class UserServiceImpl extends AbstractService implements UserService {

	private static final long	serialVersionUID	= 8350405718897091714L;

	public UserServiceImpl(ConnectionManager connectionManager) throws RemoteException {
		super(connectionManager);
	}

	@Override
	public User save(User entity) throws RemoteException {
		return null;
	}

	@Override
	public void delete(User entity) throws RemoteException {

	}

}
