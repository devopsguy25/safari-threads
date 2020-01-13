package hello.badqueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyBetterQueue<E> {
  private ReentrantLock lock = new ReentrantLock();
  private Condition notFull = lock.newCondition();
  private Condition notEmpty = lock.newCondition();

  private E[] data = (E[])(new Object[10]);
  private int count;

  public void put(E e) throws InterruptedException {
    lock.lock();
    try {
      while (count == data.length)
        notFull.await(); // <<== releases, but then reacquires before contining, the "key"

      data[count] = e;
      count++;
      notEmpty.signal();
    } finally {
      lock.unlock();
    }
  }

  public E take() throws InterruptedException {
    lock.lock();
    try {
      while (count == 0)
        notEmpty.await(); // <== thread "sleeps" with "its head on the object"

      E rv = data[0];
      System.arraycopy(data, 1, data, 0, --count);
      notFull.signal(); // <== shakes the object...
      return rv;
    } finally {
      lock.unlock();
    }
  }

}
