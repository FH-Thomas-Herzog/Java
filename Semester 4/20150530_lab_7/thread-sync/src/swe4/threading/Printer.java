package swe4.threading;

import java.util.Random;

public class Printer extends Thread {
	private static Object	syncObj	= new Object();

	private char			ch;
	private int				rows;
	private int				cols;

	public Printer(char ch, int rows, int cols) {
		this.ch = ch;
		this.rows = rows;
		this.cols = cols;
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	public void print1() {

		Random r = new Random();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(ch);
				sleep(r.nextInt(10));
			}
			System.out.println();
		}
	}

	public void print2() {

		Random r = new Random();

		for (int i = 0; i < rows; i++) {
			synchronized (syncObj) {
				for (int j = 0; j < cols; j++) {
					System.out.print(ch);
					sleep(r.nextInt(10));
				}
				System.out.println();
			} // end of sync block

			// sleep(r.nextInt(10));
		} // for
	}

	public void print3() {

		Random r = new Random();

		synchronized (syncObj) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					System.out.print(ch);
					sleep(r.nextInt(10));
				}
				System.out.println();
			} // end of sync block

			// sleep(r.nextInt(10));
		} // for
	}

	public void run() {
		// print1();
		//print2();
		print3();
	}

	public static void main(String[] args) {
		System.out.println("Start Threads");
		Printer p1 = new Printer('x', 10, 60);
		Printer p2 = new Printer('o', 10, 60);
		p1.start();
		p2.start();
		try {
			p1.join();
			p2.join();
		} catch (InterruptedException e) {
		}
		System.out.println("Finish");
	}
}