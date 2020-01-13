package runnables;

class Stopper implements Runnable {
  private /*volatile*/ boolean stop = false;

  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName()
        + " starting...");

    while (!stop)
//      System.out.print(".");
      ;

    System.out.println(Thread.currentThread().getName()
        + " stopping...");
  }

  public boolean getStop() {
    return stop;
  }

  public void stop() {
    stop = true;
  }
}

public class Second {
  public static void main(String[] args) throws Throwable {
    Stopper s = new Stopper();
    new Thread(s).start();
    System.out.println("Stopper started...");
    Thread.sleep(1000);
    System.out.println("Main woke up...");
    s.stop();
    System.out.println("Stop called on Stopper, stop value is " + s.getStop());
  }
}
