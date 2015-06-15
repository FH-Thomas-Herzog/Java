package at.fh.ooe.swe4.lab8.rmi.impl;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.Objects;

import at.fh.ooe.swe4.lab8.rmi.api.HelloRmi;

public class HelloRmiImpl implements HelloRmi {

	private int	counter	= 0;

	private static int getPort(final String hostPort) {
		Objects.requireNonNull(hostPort);

		int idx = hostPort.lastIndexOf(":");
		if (idx == -1) {
			return 1099;
		} else {
			return Integer.parseInt(hostPort.substring(idx + 1));
		}
	}

	public HelloRmiImpl() {
	}

	@Override
	public LocalDateTime getServerDate() throws RemoteException {
		return LocalDateTime.now();
	}

	@Override
	public synchronized int nextId() throws RemoteException {
		int value = counter;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		counter = value + 1;
		System.out.printf("next-id=%d | thread=%d\n", value, Thread.currentThread()
																	.getId());
		return counter;
	}

	public static void main(String[] args) {

		// Version 1.: start wmi registry via command line
		// so that files are in classpath of rmi registry process

		// Version 2.: Start rmi registry by yourself
		try {
			final int port = getPort(args[0]);
			final String binding = "rmi://" + args[0] + "/helloService";
			LocateRegistry.createRegistry(port);

			HelloRmi helloService = new HelloRmiImpl();
			Remote helloRemoteService = UnicastRemoteObject.exportObject(helloService, 0);
			Naming.rebind(binding, helloRemoteService);

			System.out.println("Service available under: " + binding);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
