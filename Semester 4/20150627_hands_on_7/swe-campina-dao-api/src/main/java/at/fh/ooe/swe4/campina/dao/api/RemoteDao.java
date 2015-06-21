package at.fh.ooe.swe4.campina.dao.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import at.fh.ooe.swe4.campina.persistence.api.AbstractEntity;

/**
 * This interface marks an interface as an service which needs to provide at
 * least these basic database operations.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 17, 2015
 * @param <T>
 *            the type of the entity
 */
public interface RemoteDao<T extends AbstractEntity> extends Remote {

	/**
	 * Saves or updates the entity
	 * 
	 * @param entity
	 *            the entity to be saved or updated
	 * @return the saved or updated entity
	 * @throws RemoteException
	 *             if the remote invocation fails
	 */
	public T save(T entity) throws RemoteException;

	/**
	 * Deletes the entity
	 * 
	 * @param entity
	 *            the entity to be saved or deleted
	 * @throws RemoteException
	 *             if the remote invocation fails
	 */
	public void delete(T entity) throws RemoteException;

	/**
	 * Gets the entity by its id
	 * 
	 * @param id
	 *            the entity id
	 * @return the found entity
	 * @throws RemoteException
	 *             if the load fails
	 */
	public T byId(Integer id) throws RemoteException;

	/**
	 * Gets all entities.
	 * 
	 * @return the list of entities
	 * @throws RemoteException
	 *             if the load fails
	 */
	public List<T> getAll() throws RemoteException;

}
