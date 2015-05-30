package swe4.threading;

public class Util {
  
  static long startTime = System.currentTimeMillis();
  
  public static void unsafeSleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException x) {}
  }

  public static Object currTime() {
    return (System.currentTimeMillis() - startTime) / 1000.0;
  }

}
