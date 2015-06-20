package at.fh.ooe.swe4.campina.dao.api;

import java.rmi.RemoteException;
import java.util.List;

import at.fh.ooe.swe4.campina.persistence.api.MenuEntry;
import at.fh.ooe.swe4.campina.rmi.api.service.RemoteDao;

public interface MenuEntryDao extends RemoteDao<MenuEntry> {

	public List<MenuEntry> getAllForMenu(int id) throws RemoteException;

}
