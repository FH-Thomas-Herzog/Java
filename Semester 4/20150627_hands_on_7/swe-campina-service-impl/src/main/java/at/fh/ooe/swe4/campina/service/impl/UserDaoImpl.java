package at.fh.ooe.swe4.campina.service.impl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import at.fh.ooe.swe4.campina.persistence.api.ConnectionManager;
import at.fh.ooe.swe4.campina.persistence.api.EntityManager;
import at.fh.ooe.swe4.campina.persistence.api.User;
import at.fh.ooe.swe4.campina.persistence.impl.EntityManagerImpl;
import at.fh.ooe.swe4.campina.service.api.AbstractRemoteDao;
import at.fh.ooe.swe4.campina.service.api.UserDao;
import at.fh.ooe.swe4.campina.service.exception.EmailAlreadyUsedException;
import at.fh.ooe.swe4.campina.service.exception.UsernameAlreadyUsedException;

public class UserDaoImpl extends AbstractRemoteDao implements UserDao {

	private static final long			serialVersionUID	= 8350405718897091714L;

	private final EntityManager<User>	userEm				= new EntityManagerImpl<>(User.class);

	private static final String			USER_ID_BY_EMAIL	= "SELECT id FROM campina.user WHERE email=? AND id<>?";
	private static final String			USER_ID_BY_USERNAME	= "SELECT id FROM campina.user WHERE username=? AND id<>?";

	/**
	 * @param connectionManager
	 * @throws RemoteException
	 */
	public UserDaoImpl(ConnectionManager connectionManager) throws RemoteException {
		super(connectionManager);
	}

	@Override
	public User save(User user) throws RemoteException {
		Objects.requireNonNull(user);

		Integer id = (user.getId() == null) ? -1 : user.getId();
		Connection con = connectionManager.getConnection(Boolean.TRUE);
		PreparedStatement pstmt;
		try {
			// Email already used
			pstmt = con.prepareStatement(USER_ID_BY_EMAIL);
			pstmt.setString(1, user.getEmail());
			pstmt.setInt(2, id);
			if (pstmt.executeQuery()
						.next()) {
				throw new EmailAlreadyUsedException();
			}
			// Username already used
			pstmt = con.prepareStatement(USER_ID_BY_USERNAME);
			pstmt.setString(1, user.getUsername());
			pstmt.setInt(2, id);
			if (pstmt.executeQuery()
						.next()) {
				throw new UsernameAlreadyUsedException();
			}

			user = userEm.saveOrUpdate(con, user);
			con.commit();
			return user;
		} catch (SQLException e) {
			throw new RemoteException("Could not save user", e);
		}
	}

	@Override
	public void delete(User entity) throws RemoteException {
		Objects.requireNonNull(entity);

		Connection con = connectionManager.getConnection(Boolean.TRUE);
		try {
			userEm.delete(con, entity);
			con.commit();
		} catch (SQLException e) {
			throw new RemoteException("Could not delete entity", e);
		}
	}

}
