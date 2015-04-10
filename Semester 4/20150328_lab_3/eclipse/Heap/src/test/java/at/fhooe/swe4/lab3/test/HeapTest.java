package at.fhooe.swe4.lab3.test;

import java.util.Random;

import at.fhooe.swe4.lab3.heap.Heap;

public class HeapTest {
	public static void main(String[] args) {

		Heap<Integer> h = new Heap<Integer>();
		System.out.println(h);
		h.enqueue(1);
		System.out.println(h);
		// System.out.println(h.dequeue());
		h.enqueue(2);
		System.out.println(h);

		Random r = new Random();
		for (int i = 0; i < 30; i++) {
			h.enqueue(r.nextInt(100));
		}
		System.out.println(h);

		while (!h.isEmpty()) {
			System.out.println(h.dequeue());
			// System.out.println(h);
		}
		System.out.println(h);

	}
}
