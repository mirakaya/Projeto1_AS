package queues;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class OrderedMonitor {
    private final ReentrantLock lock;
    private final Condition[] awaitConditions;

    private final boolean[] awaitFlags;

    private final int size;
    private int count = 0;
    private int awaitIdx = 0;
    private int toAwakeIdx = 0;

    public OrderedMonitor(int size) {
        this.size = size;
        lock = new ReentrantLock();
        awaitConditions = new Condition[size];
        awaitFlags = new boolean[size];

        for (int i = 0; i < awaitConditions.length; i++) {
            awaitConditions[i] = lock.newCondition();
        }
    }

    public void await() {
        try {
            lock.lock();

            if (count == size) return;

            final int awaitId = awaitIdx;
            count++;
            awaitIdx = (awaitIdx + 1) % size;
            awaitFlags[awaitId] = true;

            while(awaitFlags[awaitId])
                awaitConditions[awaitId].await();

            System.out.println("Awoke " + awaitId);

        } catch (InterruptedException ignored) {}
        finally {
            lock.unlock();
        }
    }

    public void awake() {
        try {
            lock.lock();

            if (count == 0) return;

            final int toAwakeId = toAwakeIdx;
            count--;
            toAwakeIdx = (toAwakeIdx + 1) % size;
            awaitFlags[toAwakeId] = false;

            awaitConditions[toAwakeId].signal();
        } finally {
            lock.unlock();
        }
    }
}
