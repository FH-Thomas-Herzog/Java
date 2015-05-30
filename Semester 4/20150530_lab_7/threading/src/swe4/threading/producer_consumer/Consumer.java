package swe4.threading.producer_consumer;

import java.util.Random;

import swe4.threading.Util;

public class Consumer extends Thread {
  
  private Buffer<Character> buffer;
  private int maxTime;
  
  public Consumer(String name, Buffer<Character> buffer, int maxTime) {
    super(name); // provide name for task/thread
    this.buffer = buffer;
    this.maxTime = maxTime;
  }

  private void print(char ch, String prefix) {
    System.out.printf("(%.3f) %s: %s %c%n", Util.currTime(), getName(), prefix, ch);
  }
  
  @Override
  public void run() {
    // TODO
  }

}
