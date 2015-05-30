package swe4.threading;

public class Buffer {
  public Buffer(int max) {
  } // Buffer

  private synchronized void print(char ch, String dir, String postfix) {
//    System.out.printf("Buffer  (%.3f): %c %s [", Util.currTime(), ch, dir);
//    int i=0;
//    for (char c : queue)
//      System.out.print((i++ > 0 ? ", " : "") + c);
//    System.out.println("]" + postfix);
  }

  public synchronized boolean empty() {
    return false;
  } // empty

  public synchronized boolean full() {
    return false;
  } // full

  public synchronized void put(char ch) {
  } // put

  public synchronized char get() {
    return '\0';
  } // get
} // Buffer
