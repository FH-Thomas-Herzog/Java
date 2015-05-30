package swe4.threading;

import java.util.Random;
import swe4.util.Util;

public class Consumer extends Thread {
	private Buffer	b;	// get get products from

	public Consumer(String name, Buffer b) {
		this.b = b;
	} // Consumer

	public void run() {
		Random randGen = new Random();
		for (int i = 0; i < 26; i++) {
			char ch = b.get();
			System.out.printf("Consumer(%.3f): --> %c%n", Util.currTime(), ch);
			Util.sleep(randGen.nextInt(1500));
			System.out.printf("Consumer(%.3f): <-- %c%n", Util.currTime(), ch);
		}
	} // run
} // Consumer

