package swe4.util;

public class Util {
  static long startTime = System.currentTimeMillis();

  public static double currTime() {
    return (System.currentTimeMillis() - startTime) / 1000.0;
  }
  
  public static void joinAll (Thread ... threads) {
     for (Thread t : threads) {
        try {
           t.join ();
        } catch (InterruptedException x) {
           x.printStackTrace ();
        }
     }
  }

  public static void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } 
    catch (InterruptedException ex) {
    }
  }

  public static void wait(Object obj) {
    try {
      obj.wait();
    }
    catch (InterruptedException e) {
    }
  }
}
