package at.fh.ooe.swe4.campina.test.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import at.fh.ooe.swe4.campina.dao.api.MenuEntryDao;
import at.fh.ooe.swe4.campina.dao.impl.MenuEntryDaoImpl;
import at.fh.ooe.swe4.campina.persistence.api.entity.Menu;
import at.fh.ooe.swe4.campina.persistence.api.entity.MenuEntry;
import at.fh.ooe.swe4.campina.persistence.api.entity.constants.Day;
import at.fh.ooe.swe4.campina.test.dao.api.AbstractDaoTest;
import at.fh.ooe.swe4.campina.test.dao.api.RemoteDetailMatcher;

/**
 * This test class represents the test class for the {@link MenuEntryDao}
 * implementation.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 20, 2015
 */
public class MenuEntryDaoTest extends AbstractDaoTest<MenuEntry> {

	private final MenuEntryDao	dao;
	private Menu				menu;

	public MenuEntryDaoTest() throws RemoteException {
		super(MenuEntry.class);
		this.dao = new MenuEntryDaoImpl(conManager);
	}

	@Before
	public void beforeTest() {
		setupDB();
		this.menu = new Menu();
		menu.setDay(Day.MONDAY);
		menu.setLabel("menu-1");
		menu = saveEntity(menu);
	}

	@Test
	public void saveNull() throws RemoteException {
		// -- Given --
		final MenuEntry menuEntry = null;
		expectedException.expect(new RemoteDetailMatcher(NullPointerException.class));

		// -- When | Then --
		dao.save(menuEntry);
	}

	@Test
	public void save() throws RemoteException {
		// -- Given --
		MenuEntry menuEntry = new MenuEntry();
		menuEntry.setLabel("menu-1");
		menuEntry.setMenu(menu);
		menuEntry.setOrdinal(0);
		menuEntry.setPrice(BigDecimal.ONE);
		menuEntry = saveEntity(menuEntry);

		// -- When --
		dao.save(menuEntry);

		// -- Then --
		try (final Connection con = conManager.getConnection(Boolean.TRUE);) {
			final MenuEntry menuEntryDB = em.byId(con, menuEntry.getId());
			assertEquals(menuEntry, menuEntryDB);
		} catch (SQLException e) {
			fail("Could not obtain connection");
		}
	}

	@Test
	public void getAllEmpty() throws RemoteException {
		// -- Given | When --
		final List<MenuEntry> fetchedMenus = dao.getAll();

		// -- Then --
		assertTrue(fetchedMenus.isEmpty());
	}

	@Test
	public void getAll() throws RemoteException {
		// -- Given --
		List<MenuEntry> menuEntries = new ArrayList<>(5);
		for (int i = 0; i < 5; i++) {
			MenuEntry menuEntry = new MenuEntry();
			menuEntry.setLabel("menu-entry-1-" + i);
			menuEntry.setMenu(menu);
			menuEntry.setOrdinal(i);
			menuEntry.setPrice(BigDecimal.ONE);
			menuEntries.add(menuEntry);
		}
		menuEntries = saveEntities(menuEntries);

		// -- When --
		final List<MenuEntry> fetchedMenuEntries = dao.getAll();

		// -- Then --
		assertEquals(menuEntries.size(), fetchedMenuEntries.size());
		assertEquals(menuEntries, fetchedMenuEntries);
	}

	@Test
	public void getAllForMenuEmpty() throws RemoteException {
		// -- Given | When --
		final List<MenuEntry> fetchedMenus = dao.getAllForMenu(menu.getId());

		// -- Then --
		assertTrue(fetchedMenus.isEmpty());
	}

	@Test
	public void getAllForMenu() throws RemoteException {
		// -- Given --
		List<MenuEntry> menuEntries = new ArrayList<>(5);
		for (int i = 0; i < 5; i++) {
			MenuEntry menuEntry = new MenuEntry();
			menuEntry.setLabel("menu-entry-1-" + i);
			menuEntry.setMenu(menu);
			menuEntry.setOrdinal(i);
			menuEntry.setPrice(BigDecimal.ONE);
			menuEntries.add(menuEntry);
		}
		menuEntries = saveEntities(menuEntries);

		// -- When --
		final List<MenuEntry> fetchedMenuEntries = dao.getAllForMenu(menu.getId());

		// -- Then --
		assertEquals(menuEntries.size(), fetchedMenuEntries.size());
		assertEquals(menuEntries, fetchedMenuEntries);
	}

	@Test
	public void byIdNull() throws RemoteException {
		// -- Given --
		final Integer id = null;
		expectedException.expect(new RemoteDetailMatcher(NullPointerException.class));

		// -- When --
		dao.byId(id);
	}

	@Test
	public void byId() throws RemoteException {
		// -- Given --
		MenuEntry menuEntry = new MenuEntry();
		menuEntry.setLabel("menu-entry-1");
		menuEntry.setMenu(menu);
		menuEntry.setOrdinal(0);
		menuEntry.setPrice(BigDecimal.ONE);
		menuEntry = saveEntity(menuEntry);

		// -- When --
		final MenuEntry menuEntryDB = dao.byId(menuEntry.getId());

		// -- Then --
		assertEquals(menu.getId(), menuEntryDB.getId());
	}

	@Test
	public void delete() throws RemoteException {
		// -- Given --
		MenuEntry menuEntry = new MenuEntry();
		menuEntry.setLabel("menu-entry-1");
		menuEntry.setMenu(menu);
		menuEntry.setOrdinal(0);
		menuEntry.setPrice(BigDecimal.ONE);
		menuEntry = saveEntity(menuEntry);

		// -- When --
		dao.delete(menuEntry);

		// -- Then --
		try {
			dao.byId(menuEntry.getId());
			fail("Entity could be found but shouldn't");
		} catch (RemoteException e) {
			// TODO: handle exception
		}
	}

	@Test
	public void deleteByMenuIdNone() throws RemoteException {
		// -- Given | When --
		dao.deleteByMenuId(menu.getId());

		// -- Then --
		final List<MenuEntry> result = dao.getAll();
		assertTrue(result.isEmpty());
	}

	@Test
	public void deleteByMenuId() throws RemoteException {
		// -- Given --
		// -- Given --
		List<MenuEntry> menuEntries = new ArrayList<>(5);
		for (int i = 0; i < 5; i++) {
			MenuEntry menuEntry = new MenuEntry();
			menuEntry.setLabel("menu-entry-1-" + i);
			menuEntry.setMenu(menu);
			menuEntry.setOrdinal(i);
			menuEntry.setPrice(BigDecimal.ONE);
			menuEntries.add(menuEntry);
		}
		menuEntries = saveEntities(menuEntries);

		// -- When --
		dao.deleteByMenuId(menu.getId());

		// -- Then --
		final List<MenuEntry> result = dao.getAll();
		assertTrue(result.isEmpty());
	}
}
