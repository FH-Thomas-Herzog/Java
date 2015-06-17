package at.fh.ooe.swe4.campina.service.api.spec;

import java.io.Serializable;
import java.rmi.RemoteException;

import at.fh.ooe.swe4.campina.jpa.api.AbstractEntity;

/**
 * This interface marks an interface as an service which needs to provide at
 * least these basic database operations.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 17, 2015
 * @param <I>
 *            the type of the id
 * @param <T>
 *            the type of the entity
 */
public interface Service<I extends Serializable, T extends AbstractEntity<I>> extends Serializable {

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
}
