package at.fh.ooe.swe4.campina.dao.api;

import java.rmi.RemoteException;
import java.util.List;

import at.fh.ooe.swe4.campina.persistence.api.entity.Menu;
import at.fh.ooe.swe4.campina.persistence.api.entity.MenuEntry;

/**
 * The DAO for the {@link MenuEntry} entity type.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 21, 2015
 */
public interface MenuEntryDao extends RemoteDao<MenuEntry> {

	/**
	 * Gets all {@link MenuEntry} for given {@link Menu} id.
	 * 
	 * @param id
	 *            the menu id
	 * @return the found menu entries
	 * @throws RemoteException
	 *             if the loading fails. Will contain a cause.
	 */
	public List<MenuEntry> getAllForMenu(int id) throws RemoteException;

}
