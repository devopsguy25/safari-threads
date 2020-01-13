package runnables;

class MyJob implements Runnable {
  int i = 0;
  @Override
  public void run() {
    for (; i < 10_000; i++) {
      System.out.println(Thread.currentThread().getName() +
          " i is " + i);
    }
  }
}
public class First {
  public static void main(String[] args) {
    Runnable r = new MyJob();
    Thread t1 = new Thread(r);
    t1.start();
    Thread t2 = new Thread(r);
    t2.start();
    System.out.println(Thread.currentThread().getName() + " exiting");
  }
}
