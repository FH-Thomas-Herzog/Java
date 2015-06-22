package at.fh.ooe.swe4.campina.test.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import at.fh.ooe.swe4.campina.dao.api.UserDao;
import at.fh.ooe.swe4.campina.dao.exception.EmailAlreadyUsedException;
import at.fh.ooe.swe4.campina.dao.exception.UsernameAlreadyUsedException;
import at.fh.ooe.swe4.campina.dao.impl.UserDaoImpl;
import at.fh.ooe.swe4.campina.persistence.api.entity.User;
import at.fh.ooe.swe4.campina.test.dao.api.AbstractDaoTest;
import at.fh.ooe.swe4.campina.test.dao.api.RemoteDetailMatcher;

/**
 * This test class represents the test class for the {@link UserDao}
 * implementation.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 20, 2015
 */
public class UserDaoTest extends AbstractDaoTest<User> {

	private final UserDao	dao;

	public UserDaoTest() throws RemoteException {
		super(User.class);
		this.dao = new UserDaoImpl(conManager);
	}

	@Before
	public void beforeTest() {
		setupDB();
	}

	@Test
	public void saveNull() throws RemoteException {
		// -- Given --
		final User user = null;
		expectedException.expect(new RemoteDetailMatcher(NullPointerException.class));

		// -- When | Then --
		dao.save(user);
	}

	@Test
	public void save() throws RemoteException {
		// -- Given --
		User user = new User();
		user.setFirstName("Thomas");
		user.setLastName("Herzog");
		user.setUsername("cchet");
		user.setEmail("t.t@t.at");
		user.setPassword("xxxxxxx");
		user.setAdminFlag(Boolean.TRUE);
		user.setBlockedFlag(Boolean.FALSE);

		// -- When --
		user = dao.save(user);

		// -- Then --
		try (final Connection con = conManager.getConnection(Boolean.TRUE);) {
			final User userDB = em.byId(con, user.getId());
			assertEquals(user, userDB);
		} catch (SQLException e) {
			fail("Could not obtain connection");
		}
	}

	@Test
	public void saveDuplicateEmail() throws RemoteException {
		// -- Given --
		User user = new User();
		user.setFirstName("Thomas");
		user.setLastName("Herzog");
		user.setUsername("cchet");
		user.setEmail("t.t@t.at");
		user.setPassword("xxxxxxx");
		user.setAdminFlag(Boolean.TRUE);
		user.setBlockedFlag(Boolean.FALSE);
		user = saveEntity(user);
		user.setId(null);
		expectedException.expect(new RemoteDetailMatcher(EmailAlreadyUsedException.class));

		// -- When --
		user = dao.save(user);
	}

	@Test
	public void saveDuplicateUsername() throws RemoteException {
		// -- Given --
		User user = new User();
		user.setFirstName("Thomas");
		user.setLastName("Herzog");
		user.setUsername("cchet");
		user.setEmail("t.t@t.at");
		user.setPassword("xxxxxxx");
		user.setAdminFlag(Boolean.TRUE);
		user.setBlockedFlag(Boolean.FALSE);
		user = saveEntity(user);
		user.setId(null);
		user.setEmail(user.getEmail() + ".com");
		expectedException.expect(new RemoteDetailMatcher(UsernameAlreadyUsedException.class));

		// -- When | Then --
		user = dao.save(user);
	}

	@Test
	public void getAllEmpty() throws RemoteException {
		// -- Given | When --
		final List<User> fetchedUsers = dao.getAll();

		// -- Then --
		assertTrue(fetchedUsers.isEmpty());
	}

	@Test
	public void getAll() throws RemoteException {
		// -- Given --
		List<User> users = new ArrayList<>(5);
		for (int i = 0; i < 5; i++) {
			User user = new User();
			user.setFirstName("Thomas-" + i);
			user.setLastName("Herzog-" + i);
			user.setUsername("cchet-" + i);
			user.setEmail("t-" + i + ".t@t.at");
			user.setPassword("xxxxxxx");
			user.setAdminFlag(Boolean.TRUE);
			user.setBlockedFlag(Boolean.FALSE);
			users.add(user);
		}
		users = saveEntities(users);

		// -- When --
		final List<User> fetchedUsers = dao.getAll();

		// -- Then --
		assertEquals(users.size(), fetchedUsers.size());
		assertEquals(users, fetchedUsers);
	}

	@Test
	public void byIdNull() throws RemoteException {
		// -- Given --
		final Integer id = null;
		expectedException.expect(new RemoteDetailMatcher(NullPointerException.class));

		// -- When | Then --
		dao.byId(id);
	}

	@Test
	public void byId() throws RemoteException {
		// -- Given --
		User user = new User();
		user.setFirstName("Thomas");
		user.setLastName("Herzog");
		user.setUsername("cchet");
		user.setEmail("t.t@t.at");
		user.setPassword("xxxxxxx");
		user.setAdminFlag(Boolean.TRUE);
		user.setBlockedFlag(Boolean.FALSE);
		user = saveEntity(user);

		// -- When --
		final User userDB = dao.byId(user.getId());

		// -- Then --
		assertEquals(user, userDB);
	}

	@Test
	public void deleteSingle() throws RemoteException {
		// -- Given --
		User user = new User();
		user.setFirstName("Thomas");
		user.setLastName("Herzog");
		user.setUsername("cchet");
		user.setEmail("t.t@t.at");
		user.setPassword("xxxxxxx");
		user.setAdminFlag(Boolean.TRUE);
		user.setBlockedFlag(Boolean.FALSE);
		user = saveEntity(user);

		// -- When --
		dao.delete(user);

		// -- Then --
		try {
			dao.byId(user.getId());
			fail("Entity could be found but shouldn't");
		} catch (RemoteException e) {
			// TODO: handle exception
		}
	}
}
