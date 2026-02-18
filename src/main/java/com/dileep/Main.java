package com.dileep;

public class Main {
  public static void main(String[] args) {

    Thread printFoo = new Thread(() -> {
      System.out.println("foo");
    });

    Thread printBar = new Thread(() -> {
      System.out.println("bar");
    });

    boolean canFoo = true;
    boolean canBar = false;

    while (canFoo) {
      printFoo.start();
      canBar = true;
      if (canBar) {

      }
    }
  }
}
