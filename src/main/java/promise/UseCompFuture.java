package promise;

import java.util.concurrent.CompletableFuture;

public class UseCompFuture {
  public static CompletableFuture<String> getFileFaked(String fn) {
    System.out.println("Background request launched...");
    CompletableFuture<String> cfs = new CompletableFuture<>();
    new Thread(() -> {
      System.out.println(Thread.currentThread().getName() +
          " Starting background file (fake) read!!!");
      delay();
      cfs.complete("Contents of file " + fn + " are secretive and I won't say!");
    }).start();
    System.out.println("Background request processing, getFileFaked return now!");
    return cfs;
  }
  public static void delay() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
    CompletableFuture<Void> cfv = CompletableFuture
        .supplyAsync(() -> "data.txt")
//        .thenApplyAsync(fn -> {
//          System.out.println(Thread.currentThread().getName()
//              + " processing file: " + fn);
//          delay();
//          System.out.println("returning contents...");
//          return "Contents of file " + fn + " is ... very short!";
//        })
        .thenComposeAsync(f -> getFileFaked(f))
        .thenAcceptAsync(d -> System.out.println(Thread.currentThread().getName()
            + "Final result: " + d));
    System.out.println("Work submitted...");
    cfv.join();
    System.out.println("main exiting...");
  }
}
