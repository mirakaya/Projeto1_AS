package HCP.Utils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of a queue for threads. Multiple threads enter it and waits to be
 * awakened. When awaken they will exit by the order they entered.
 * Instances of this class are to be used inside a region protected with the monitor
 * passed as an argument.
 */
public class OrderedMonitor {
    private final Condition[] awaitConditions;
    private final Condition awaitExited;

    private final boolean[] awaitFlags;
    private boolean exited = true;

    private final int size;
    private int count = 0;
    private int awaitIdx = 0;
    private int toAwakeIdx = 0;

    public OrderedMonitor(int size, ReentrantLock lock) {
        this.size = size;
        awaitConditions = new Condition[size];
        awaitExited = lock.newCondition();
        awaitFlags = new boolean[size];

        for (int i = 0; i < awaitConditions.length; i++) {
            awaitConditions[i] = lock.newCondition();
        }
    }

    public int getSize() {
        return size;
    }

    public void await() {
        try {
            if (count == size) return;

            final int awaitId = awaitIdx;
            count++;
            awaitIdx = (awaitIdx + 1) % size;
            awaitFlags[awaitId] = true;

            while(awaitFlags[awaitId])
                awaitConditions[awaitId].await();

            exited = true;
            awaitExited.signal();
        } catch (InterruptedException ignored) {}
    }

    public void awake() {
        try {
            if (count == 0) return;

            while(!exited)
                awaitExited.await();

            final int toAwakeId = toAwakeIdx;
            count--;
            toAwakeIdx = (toAwakeIdx + 1) % size;
            awaitFlags[toAwakeId] = false;
            exited = false;

            awaitConditions[toAwakeId].signal();
        } catch (InterruptedException ignored) {}
    }
}
