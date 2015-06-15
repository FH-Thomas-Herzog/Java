package swe4.threading;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import swe4.threading.api.BufferService;
import swe4.util.Util;

// Buffer offers the basic functionality of a java.util.concurrent.BlockingQueue.
// get -> take, put -> put
public class BufferServiceImpl implements BufferService {
	private Queue<Character>	queue;	// element collection
	private int					max;	// maximum size of queue
										// Buffer will block when additional
										// elements are inserted.

	public BufferServiceImpl(int max) {
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

	private static int getPort(final String hostPort) {
		Objects.requireNonNull(hostPort);

		int idx = hostPort.lastIndexOf(":");
		if (idx == -1) {
			return 1099;
		} else {
			return Integer.parseInt(hostPort.substring(idx + 1));
		}
	}

	public static void main(String[] args) {

		// Version 1.: start wmi registry via command line
		// so that files are in classpath of rmi registry process

		// Version 2.: Start rmi registry by yourself
		try {
			BufferService bufferService = new BufferServiceImpl(Integer.parseInt(args[1]));
			Remote helloRemoteService = UnicastRemoteObject.exportObject(bufferService, 0);

			final String binding = "rmi://" + args[0] + "/bufferService";
			LocateRegistry.createRegistry(getPort(args[0]));
			Naming.rebind(binding, helloRemoteService);

			System.out.println("Service available under: " + binding);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
} // Buffer

