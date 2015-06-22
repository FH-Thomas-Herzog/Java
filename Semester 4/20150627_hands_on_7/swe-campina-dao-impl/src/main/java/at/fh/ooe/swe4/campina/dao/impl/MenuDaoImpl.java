package at.fh.ooe.swe4.campina.dao.impl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import at.fh.ooe.swe4.campina.dao.api.AbstractRemoteDao;
import at.fh.ooe.swe4.campina.dao.api.MenuDao;
import at.fh.ooe.swe4.campina.dao.api.MenuEntryDao;
import at.fh.ooe.swe4.campina.persistence.api.ConnectionManager;
import at.fh.ooe.swe4.campina.persistence.api.EntityManager;
import at.fh.ooe.swe4.campina.persistence.api.entity.Menu;
import at.fh.ooe.swe4.campina.persistence.api.entity.MenuEntry;
import at.fh.ooe.swe4.campina.persistence.impl.EntityManagerImpl;

/**
 * This is the implementation of the {@link MenuDao} specification.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 20, 2015
 */
public class MenuDaoImpl extends AbstractRemoteDao implements MenuDao {

	private static final long	serialVersionUID	= 2017517222109172291L;

	final EntityManager<Menu>	menuEm				= new EntityManagerImpl<>(Menu.class);
	final MenuEntryDao			menuEntryDao;

	/**
	 * @param connectionManager
	 * @throws RemoteException
	 */
	public MenuDaoImpl(ConnectionManager connectionManager) throws RemoteException {
		super(connectionManager);

		this.menuEntryDao = new MenuEntryDaoImpl(connectionManager);
	}

	@Override
	public Menu save(Menu menu) throws RemoteException {
		if (menu == null) {
			throw new RemoteException("Cannot save or update null entity", new NullPointerException());
		}

		try (final Connection con = connectionManager.getConnection(Boolean.TRUE)) {
			menu = menuEm.saveOrUpdate(con, menu);
			con.commit();
			return menu;
		} catch (Throwable e) {
			throw new RemoteException("Could not save or update menu", e);
		}
	}

	@Override
	public void delete(Menu menu) throws RemoteException {
		if (menu == null) {
			throw new RemoteException("Cannot save or update null entity", new NullPointerException());
		}

		if ((menu == null) || (menu.getId() == null)) {
			throw new RemoteException("Cannot delete null entity or entity with null id", new NullPointerException());
		}

		try (Connection con = connectionManager.getConnection(Boolean.TRUE);) {
			menuEntryDao.deleteByMenuId(menu.getId());
			menuEm.delete(con, menu);
			con.commit();
		} catch (SQLException e) {
			throw new RemoteException("Could not delete menu", e);
		}
	}

	@Override
	public Menu byId(Integer id) throws RemoteException {
		if (id == null) {
			throw new RemoteException("Cannot fetch entity with null id", new NullPointerException());
		}

		try (Connection con = connectionManager.getConnection(Boolean.TRUE);) {
			final Menu menu = menuEm.byId(con, id);
			return menu;
		} catch (SQLException e) {
			throw new RemoteException("Could not get menu by id", e);
		}
	}

	@Override
	public List<Menu> getAll() throws RemoteException {
		try (Connection con = connectionManager.getConnection(Boolean.TRUE);) {
			final List<Menu> menus = menuEm.byType(con);
			Collections.sort(menus, new Comparator<Menu>() {
				@Override
				public int compare(Menu o1, Menu o2) {
					return o1.getLabel()
								.compareTo(o2.getLabel());
				}
			});
			return menus;
		} catch (SQLException e) {
			throw new RemoteException("Could not get all menus", e);
		}
	}

}
