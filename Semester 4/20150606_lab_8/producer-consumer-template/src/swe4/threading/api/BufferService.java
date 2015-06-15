package swe4.threading.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BufferService extends Remote {

	void put(char ch) throws RemoteException;

	char get() throws RemoteException;
}
