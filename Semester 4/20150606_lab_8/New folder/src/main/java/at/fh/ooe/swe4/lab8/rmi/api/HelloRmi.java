package at.fh.ooe.swe4.lab8.rmi.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface HelloRmi extends Remote {

	LocalDateTime getServerDate() throws RemoteException;

	int nextId() throws RemoteException;
}
