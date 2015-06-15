package swe4.threading;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Random;

import swe4.threading.api.BufferService;
import swe4.util.Util;

public class Consumer {
	private BufferService	b;	// get get products from

	public Consumer(String hostWIthPort) {
		final String binding = "rmi://" + hostWIthPort + "/bufferService";
		try {
			this.b = (BufferService) Naming.lookup(binding);
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(-1);
		}
	} // Consumer

	public void consume() throws RemoteException {
		Random randGen = new Random();
		for (int i = 0; i < 26; i++) {
			char ch = b.get();
			System.out.printf("Consumer(%.3f): --> %c%n", Util.currTime(), ch);
			Util.sleep(randGen.nextInt(1500));
			System.out.printf("Consumer(%.3f): <-- %c%n", Util.currTime(), ch);
		}
	} // run

	public static void main(String args[]) {
		String hostPort = args[0];
		Consumer c = new Consumer(hostPort);
		System.out.println("Consuming...");
		try {
			c.consume();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
} // Consumer

