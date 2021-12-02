package simple;

public class Visibility {
  static volatile boolean stop = false;

  public static void main(String[] args) throws Throwable {
    Runnable stopper = () -> {
      System.out.println(Thread.currentThread().getName() + " worker starting");
      while (!stop) {

      }
      System.out.println(Thread.currentThread().getName() + " worker ending");
    };
    Thread t1 = new Thread(stopper);
//    t1.setDaemon(true); // makes a thread unimportant, so JVM will die
    // when no "non-deamon" threads remain
    t1.start();
    System.out.println(Thread.currentThread().getName() + " worker launched");
    Thread.sleep(1_000);
    stop = true;
    System.out.println(Thread.currentThread().getName() + " stop is " + stop);
    System.out.println("main exiting");
//    stop = false; // RACE CONDITION!!! DON'T LET THIS HAPPEN :)
  }
}
