package queues;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        /*
          This Monitors intended use is as follows.
          If a bunch of thread await at this monitor
          if they are awoken one at time in a paused fashion
          they should come out in a ordered fashion.
         */
        final int size = 1000;
        OrderedMonitor monitor = new OrderedMonitor(size);
        Thread[] awaiters = new Thread[size];

        for (int i = 0; i < size; i++) {
            awaiters[i] = new Thread(monitor::await);
        }

        for (int i = 0; i < size; i++) {
            awaiters[i].start();
        }

        for (int i = 0; i < size; i++) {
            monitor.awake();
        }

        for (int i = 0; i < size; i++) {
            awaiters[i].join();
        }
    }
}
