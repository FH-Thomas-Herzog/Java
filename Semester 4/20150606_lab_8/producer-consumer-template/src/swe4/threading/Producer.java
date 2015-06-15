package swe4.threading;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Random;

import swe4.threading.api.BufferService;
import swe4.util.Util;

public class Producer {
	private BufferService	b;	// to store products to

	public Producer(String hostWithPort) {
		final String binding = "rmi://" + hostWithPort + "/bufferService";
		try {
			this.b = (BufferService) Naming.lookup(binding);
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(-1);
		}
	} // Producer

	public void produce() throws RemoteException {
		Random randGen = new Random();
		for (int i = 0; i < 26; i++) {
			char newChar = (char) ('A' + (i % 26));
			System.out.printf("Producer(%.3f): ++> %c%n", Util.currTime(),
								newChar);
			Util.sleep(randGen.nextInt(1000));
			System.out.printf("Producer(%.3f): <++ %c%n", Util.currTime(),
								newChar);
			b.put(newChar);
		}
	} // run

	public static void main(String args[]) {
		String hostPort = args[0];
		Producer c = new Producer(hostPort);
		System.out.println("Consuming...");
		try {
			c.produce();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
} // Producer
