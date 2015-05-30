package swe4.threading.producer_consumer;

import java.util.Formatter;
import java.util.LinkedList;
import java.util.Queue;

import swe4.threading.Util;

public class Buffer<T> {

  private Queue<T> queue;
  private int max;
  private String name;

  public Buffer(String name, int max) {
    this.queue = new LinkedList<T>();
    this.max = max;
    this.name = name;
  }

  private synchronized void print(T item, String dir, String postfix) {
    Formatter f = new Formatter();
    f.format("(%.3f) %s: %c %s [", Util.currTime(), name, item, dir);
    for (T element : queue) {
      f.format("%s", element);
    }
    f.format("] %s", postfix);
    System.out.println(f);
  }

  public synchronized boolean empty() {
    // TODO
    return false;
  }

  public synchronized boolean full() {
    // TODO
    return false;
  }

  public synchronized void put(T item) throws InterruptedException {
    // TODO
  }

  public synchronized T get() throws InterruptedException {
    // TODO
    return null;
  }

}
