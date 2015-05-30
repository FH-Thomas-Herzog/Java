package swe4.threading;

import java.util.LinkedList;
import java.util.Queue;
import swe4.util.Util;

// Buffer offers the basic functionality of a java.util.concurrent.BlockingQueue.
// get -> take, put -> put
public class Buffer {
	private Queue<Character>	queue;	// element collection
	private int					max;	// maximum size of queue
										// Buffer will block when additional
										// elements are inserted.

	public Buffer(int max) {
		this.queue = new LinkedList<>();
		this.max = max;
	} // Buffer

	private void print(char ch, String dir, String postfix) {
		System.out.printf("Buffer  (%.3f): %c %s [", Util.currTime(), ch, dir);
		int i = 0;
		for (char c : queue)
			System.out.print((i++ > 0 ? ", " : "") + c);
		System.out.println("]" + postfix);
	}

	public synchronized boolean empty() {
		return queue.isEmpty();
	} // empty

	public synchronized boolean full() {
		return queue.size() >= max;
	} // full

	public synchronized void put(char ch) {
		while (full()) {
			print(ch, "==>", ": rejected because of full buffer!");
			// Buffer locks producer, locks this block
			Util.wait(this);
		} // while

		print(ch, "==>", "");
		queue.offer(ch);
		// notify the consumer
		notifyAll();
	} // put

	public synchronized char get() {
		while (empty()) {
			System.out.println("Consumer: Buffer empty.");
			// Wait until producer has produced
			Util.wait(this);
		} // while
		char ch = queue.poll();
		print(ch, "<==", "");

		// notify the next consumer
		notifyAll();
		return ch;
	} // get
} // Buffer

