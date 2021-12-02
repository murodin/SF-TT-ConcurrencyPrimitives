package simple;

class MyJob implements Runnable {
  Object rendezvous = new Object();

  /*volatile -- this does NOT really help with the
  * read/modify/write problem*/long counter = 0;
  @Override
  public void run() {
    for (long i = 0; i < 100_000_000; i++) {
      synchronized(this.rendezvous) {
        counter++;
      }
    }
  } // Thread runs off end? Then it dies
}

// Threading problems:
// 1) timing
// 2) "transactional correctness"
public class FirstThread {
  public static void main(String[] args) throws Throwable {
    MyJob mj = new MyJob();
    System.out.println(mj.counter);
    Thread t1 = new Thread(mj);
    Thread t2 = new Thread(mj);
    t1.start();
//    t1.join(); // This would cause very crude "mutual exclusion"
    // because one (entire) process completes before the other
    // is permitted to start
    t2.start();
//    Thread.sleep(1_000);
    t1.join();
    t2.join();
    System.out.println(mj.counter);
  }
}
