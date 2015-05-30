package swe4.threading.printer;

import java.util.Random;

import swe4.threading.Util;

public class Printer extends Thread {

  private char ch;
  private int rows;
  private int cols;

  public Printer(char ch, int rows, int cols) {
    this.ch = ch;
    this.rows = rows;
    this.cols = cols;
  }

  public void print1() {
    Random r = new Random();

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        System.out.print(ch);
        Util.unsafeSleep(r.nextInt(10));
      }
      System.out.println();
    }
  }

  public void run() {
    print1();
  }

  public static void main(String[] args) {
    System.out.println("starting threads ...");
    Printer p1 = new Printer('x', 10, 60);
    Printer p2 = new Printer('o', 10, 60);
    p1.start();
    p2.start();
    try {
      p1.join();
      p2.join();
    } catch (InterruptedException e) {
    }
    System.out.println("done");
  }
}
