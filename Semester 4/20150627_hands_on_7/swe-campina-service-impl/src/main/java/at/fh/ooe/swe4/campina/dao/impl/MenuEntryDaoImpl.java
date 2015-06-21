package at.fh.ooe.swe4.campina.dao.impl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import at.fh.ooe.swe4.campina.dao.api.AbstractRemoteDao;
import at.fh.ooe.swe4.campina.dao.api.MenuEntryDao;
import at.fh.ooe.swe4.campina.persistence.api.ConnectionManager;
import at.fh.ooe.swe4.campina.persistence.api.EntityManager;
import at.fh.ooe.swe4.campina.persistence.api.entity.MenuEntry;
import at.fh.ooe.swe4.campina.persistence.impl.EntityManagerImpl;

/**
 * This is the implementation of the {@link MenuEntryDao} specification.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 20, 2015
 */
public class MenuEntryDaoImpl extends AbstractRemoteDao implements MenuEntryDao {

	private static final long				serialVersionUID	= 2017517222109172291L;

	private final EntityManager<MenuEntry>	menuEntryEm			= new EntityManagerImpl<>(MenuEntry.class);
	private static final String				SELECT_FOR_MENU		= "SELECT id, %s, version FROM %s WHERE menu_id=?";

	/**
	 * @param connectionManager
	 * @throws RemoteException
	 */
	public MenuEntryDaoImpl(ConnectionManager connectionManager) throws RemoteException {
		super(connectionManager);
	}

	@Override
	public MenuEntry save(MenuEntry menuEntry) throws RemoteException {
		if (menuEntry == null) {
			throw new RemoteException("Cannot save or update null entity", new NullPointerException());
		}

		try (final Connection con = connectionManager.getConnection(Boolean.TRUE)) {
			menuEntry = menuEntryEm.saveOrUpdate(con, menuEntry);
			con.commit();
			return menuEntry;
		} catch (Throwable e) {
			throw new RemoteException("Could not save or update menu", e);
		}
	}

	@Override
	public void delete(MenuEntry menuEntry) throws RemoteException {
		if (menuEntry == null) {
			throw new RemoteException("Cannot save or update null entity", new NullPointerException());
		}

		if ((menuEntry == null) || (menuEntry.getId() == null)) {
			throw new RemoteException("Cannot delete null entity or entity with null id", new NullPointerException());
		}

		try (Connection con = connectionManager.getConnection(Boolean.TRUE);) {
			menuEntryEm.delete(con, menuEntry);
			con.commit();
		} catch (SQLException e) {
			throw new RemoteException("Could not delete entity", e);
		}
	}

	@Override
	public MenuEntry byId(Integer id) throws RemoteException {
		if (id == null) {
			throw new RemoteException("Cannot fetch entity with null id", new NullPointerException());
		}

		try (Connection con = connectionManager.getConnection(Boolean.TRUE);) {
			final MenuEntry menuEntry = menuEntryEm.byId(con, id);
			return menuEntry;
		} catch (SQLException e) {
			throw new RemoteException("Could not get menu entry by id", e);
		}
	}

	@Override
	public List<MenuEntry> getAll() throws RemoteException {
		try (Connection con = connectionManager.getConnection(Boolean.TRUE);) {
			final List<MenuEntry> users = menuEntryEm.byType(con);
			Collections.sort(users, new Comparator<MenuEntry>() {
				@Override
				public int compare(MenuEntry o1, MenuEntry o2) {
					return o1.getOrdinal()
								.compareTo(o2.getOrdinal());
				}
			});
			return users;
		} catch (SQLException e) {
			throw new RemoteException("Could not get all menu entries", e);
		}
	}

	@Override
	public List<MenuEntry> getAllForMenu(final int id) throws RemoteException {
		try (Connection con = connectionManager.getConnection(Boolean.TRUE);) {
			final List<MenuEntry> menuEntries = menuEntryEm.byQuery(con,
																	String.format(SELECT_FOR_MENU, menuEntryEm.getColumnNames(null), menuEntryEm.getTableName()),
																	Integer.valueOf(id));
			Collections.sort(menuEntries);
			return menuEntries;
		} catch (SQLException e) {
			throw new RemoteException("Could not get all menu entries for menu", e);
		}
	}
}
