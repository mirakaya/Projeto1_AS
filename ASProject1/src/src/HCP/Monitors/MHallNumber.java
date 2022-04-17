package HCP.Monitors;

import java.util.concurrent.locks.ReentrantLock;

public class MHallNumber {
    private final ReentrantLock monitor = new ReentrantLock();
    private int next = 1;

    public int getNextHallHumber() {
        monitor.lock();
        int result = next++;
        monitor.unlock();

        return result;
    }
}
