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

import at.fh.ooe.swe4.campina.dao.api.MenuDao;
import at.fh.ooe.swe4.campina.dao.impl.MenuDaoImpl;
import at.fh.ooe.swe4.campina.persistence.api.entity.Menu;
import at.fh.ooe.swe4.campina.persistence.api.entity.constants.Day;
import at.fh.ooe.swe4.campina.test.dao.api.AbstractDaoTest;
import at.fh.ooe.swe4.campina.test.dao.api.RemoteDetailMatcher;

/**
 * This test class represents the test class for the {@link MenuDao}
 * implementation.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 20, 2015
 */
public class MenuDaoTest extends AbstractDaoTest<Menu> {

	private final MenuDao	dao;

	public MenuDaoTest() throws RemoteException {
		super(Menu.class);
		this.dao = new MenuDaoImpl(conManager);
	}

	@Before
	public void beforeTest() {
		setupDB();
	}

	// -- Then --
	@Test
	public void saveNull() throws RemoteException {
		// -- Given --
		final Menu menu = null;
		expectedException.expect(new RemoteDetailMatcher(NullPointerException.class));

		// -- When --
		dao.save(menu);
	}

	@Test
	public void save() throws RemoteException {
		// -- Given --
		Menu menu = new Menu();
		menu.setDay(Day.MONDAY);
		menu.setLabel("menu-1");

		// -- When --
		dao.save(menu);

		// -- Then --
		try (final Connection con = conManager.getConnection(Boolean.TRUE);) {
			final Menu menuDB = em.byId(con, menu.getId());
			assertEquals(menu, menuDB);
		} catch (SQLException e) {
			fail("Could not obtain connection");
		}
	}

	@Test
	public void getAllEmpty() throws RemoteException {
		// -- Given | When --
		final List<Menu> fetchedMenus = dao.getAll();

		// -- Then --
		assertTrue(fetchedMenus.isEmpty());
	}

	@Test
	public void getAll() throws RemoteException {
		// -- Given --
		List<Menu> menus = new ArrayList<>(5);
		for (int i = 0; i < 5; i++) {
			Menu menu = new Menu();
			menu.setDay(Day.MONDAY);
			menu.setLabel("menu-1-" + i);
			menus.add(menu);
		}
		menus = saveEntities(menus);

		// -- When --
		final List<Menu> fetchedMenus = dao.getAll();

		// -- Then --
		assertEquals(menus.size(), fetchedMenus.size());
		assertEquals(menus, fetchedMenus);
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
		Menu menu = new Menu();
		menu.setDay(Day.MONDAY);
		menu.setLabel("menu-1");
		menu = saveEntity(menu);

		// -- When --
		final Menu menuDB = dao.byId(menu.getId());

		// -- Then --
		assertEquals(menu, menuDB);
	}
}
