package hello.badqueue;

public class MyBadQueue<E> {
  private E[] data = (E[])(new Object[10]);
  private int count;

  public void put(E e) throws InterruptedException {
    synchronized (this) {
      while (count == data.length)
        this.wait(); // <<== releases, but then reacquires before contining, the "key"

      data[count] = e;
      count++;
      this.notifyAll();
    }
  }

  public E take() throws InterruptedException {
    synchronized(this) {
      while (count == 0)
        this.wait(); // <== thread "sleeps" with "its head on the object"

      E rv = data[0];
      System.arraycopy(data, 1, data, 0, --count);
      this.notifyAll(); // <== shakes the object...
      return rv;
    }
  }

}
