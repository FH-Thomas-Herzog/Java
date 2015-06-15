package at.fh.ooe.swe4.lab8.rmi.impl;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

import at.fh.ooe.swe4.lab8.rmi.api.HelloRmi;

public class Client {

	private static final int	ITERATION_COUNT	= 1000;

	public Client() {
		// TODO Auto-generated constructor stub
	}

	private static void doTimings(HelloRmi service) throws RemoteException {
		long nanoStart = System.nanoTime();
		for (int i = 0; i < ITERATION_COUNT; i++) {
			service.getServerDate();
		}
		long nanoEnd = System.nanoTime();
		double timePerCall = ((double) nanoEnd - nanoStart) / ITERATION_COUNT / 1E9;

		System.out.printf("Time/Call: %.6f\n", timePerCall);
		System.out.printf("Call/Second: %d\n", (int) (1 / timePerCall));
	}

	public static void main(String args[]) {
		final String binding = "rmi://" + args[0] + "/helloService";

		try {
			System.out.println("Doing lookup for HelloRmi...");
			HelloRmi service = (HelloRmi) Naming.lookup(binding);
			System.out.printf("typeof(service): %s\n", service.getClass()
																.getName());
			System.out.printf("Client date: %s\n", LocalDateTime.now());
			System.out.printf("Server date: %s\n", service.getServerDate());

			System.out.println("--------------- Timings --------------- ");
			for (int i = 0; i < ITERATION_COUNT; i++) {
				System.out.printf("Next-id: %d\n", service.nextId());
				doTimings(service);
			}
			System.out.println("--------------- Timings --------------- ");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
