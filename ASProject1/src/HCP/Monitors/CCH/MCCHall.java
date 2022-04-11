package HCP.Monitors.CCH;

import HCP.Enums.CallCenterCall;
import HCP.Utils.UnboundedQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementations of the shared region for the Call Center
 */
public class MCCHall implements ICCHallPatient, ICCHallCallCenter{
    private final ReentrantLock monitor = new ReentrantLock();

    // Tells whether CC is operating in auto or manual mode
    private boolean isAuto = true;
    // Tells whether an order from CCP was already given
    private boolean shouldWait = false;
    // Condition where CC will wait for an order from CCP
    private final Condition waitCC = monitor.newCondition();

    // Queue with calls to the call center
    private final UnboundedQueue<CallCenterCall> callQueue = new UnboundedQueue<>();
    private final Condition waitCall = monitor.newCondition();

    @Override
    public void waitManualOrder() {
        monitor.lock();

        try {
            while (!isAuto && shouldWait) waitCC.await();

            if (!isAuto) shouldWait = true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        monitor.unlock();
    }

    @Override
    public CallCenterCall waitCall() {
        CallCenterCall nextCall = CallCenterCall.EXIT;

        monitor.lock();

        try {

            while (callQueue.isEmpty()) waitCall.await();
            nextCall = callQueue.dequeue();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        monitor.unlock();

        return nextCall;
    }

    @Override
    public void informLeftEVHall() {
        monitor.lock();
        callQueue.enqueue(CallCenterCall.EV_PATIENT_LEFT);
        waitCall.signal();
        monitor.unlock();
    }

    // Test code
    public void informExit() {
        monitor.lock();
        callQueue.enqueue(CallCenterCall.EXIT);
        waitCall.signal();
        monitor.unlock();
    }
}
