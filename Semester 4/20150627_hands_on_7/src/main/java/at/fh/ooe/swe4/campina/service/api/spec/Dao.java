package at.fh.ooe.swe4.campina.service.api.spec;

import java.io.Serializable;
import java.rmi.RemoteException;

import at.fh.ooe.swe4.campina.jpa.api.AbstractEntity;

public interface Dao<I extends Serializable, T extends AbstractEntity<I>> extends Serializable {

	public T save(T entity) throws RemoteException;

	public T delete(T entity) throws RemoteException;
}
