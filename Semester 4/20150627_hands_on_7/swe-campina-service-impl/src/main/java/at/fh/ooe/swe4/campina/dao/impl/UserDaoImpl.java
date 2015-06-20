package at.fh.ooe.swe4.campina.dao.impl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import at.fh.ooe.swe4.campina.dao.api.AbstractRemoteDao;
import at.fh.ooe.swe4.campina.dao.api.UserDao;
import at.fh.ooe.swe4.campina.dao.exception.EmailAlreadyUsedException;
import at.fh.ooe.swe4.campina.dao.exception.UsernameAlreadyUsedException;
import at.fh.ooe.swe4.campina.persistence.api.ConnectionManager;
import at.fh.ooe.swe4.campina.persistence.api.EntityManager;
import at.fh.ooe.swe4.campina.persistence.api.User;
import at.fh.ooe.swe4.campina.persistence.impl.EntityManagerImpl;

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
		if (user == null) {
			throw new RemoteException("Cannot save or update null entity", new NullPointerException());
		}

		Integer id = (user.getId() == null) ? -1 : user.getId();
		try (Connection con = connectionManager.getConnection(Boolean.TRUE);) {
			PreparedStatement pstmt;
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
	public void delete(User user) throws RemoteException {
		if (user == null) {
			throw new RemoteException("Cannot delete null entity", new NullPointerException());
		}

		try (Connection con = connectionManager.getConnection(Boolean.TRUE);) {
			userEm.delete(con, user);
			con.commit();
		} catch (SQLException e) {
			throw new RemoteException("Could not delete entity", e);
		}
	}

	@Override
	public User byId(final Integer id) throws RemoteException {
		if (id == null) {
			throw new RemoteException("Cannot find entity with null id", new NullPointerException());
		}

		try (Connection con = connectionManager.getConnection(Boolean.TRUE);) {
			final User user = userEm.byId(con, id);
			return user;
		} catch (SQLException e) {
			throw new RemoteException("Could not get user by id", e);
		}
	}

	@Override
	public List<User> getAll() throws RemoteException {
		try (Connection con = connectionManager.getConnection(Boolean.TRUE);) {
			final List<User> users = userEm.byType(con);
			Collections.sort(users, new Comparator<User>() {
				@Override
				public int compare(User o1, User o2) {
					return o1.getLastName()
								.compareTo(o2.getLastName());
				}
			});
			return users;
		} catch (SQLException e) {
			throw new RemoteException("Could not get all users", e);
		}
	}
}
