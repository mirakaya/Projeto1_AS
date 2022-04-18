package HCP.Monitors.Simulation;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MSimulationController implements ISCEntities, ISCSimulation {
    private final ReentrantLock monitor = new ReentrantLock();

    private boolean isPaused = false;
    private final Condition resumed = monitor.newCondition();

    @Override
    public void waitIfPaused() throws InterruptedException {
        monitor.lockInterruptibly();
        while (isPaused) {
            resumed.await();
        }
        monitor.unlock();
    }

    @Override
    public void pause() {
        monitor.lock();
        isPaused = true;
        monitor.unlock();
    }

    @Override
    public void resume() {
        monitor.lock();
        isPaused = false;
        resumed.signalAll();
        monitor.unlock();
    }
}
