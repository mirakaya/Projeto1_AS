package queues;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        /*
          This Monitors intended use is as follows.
          If a bunch of thread await at this monitor
          if they are awoken one at time in a paused fashion
          they should come out in a ordered fashion.
         */
        final int size = 30;
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
            TimeUnit.SECONDS.sleep(1);
        }

        for (int i = 0; i < size; i++) {
            awaiters[i].join();
        }
    }
}
