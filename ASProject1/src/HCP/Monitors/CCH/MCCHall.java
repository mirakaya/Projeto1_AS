package HCP.Monitors.CCH;

import HCP.Enums.CallCenterCall;
import HCP.Enums.PatientAge;
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
    private final UnboundedQueue<Call> callQueue = new UnboundedQueue<>();
    private final Condition waitCall = monitor.newCondition();

    @Override
    public void waitManualOrder() throws InterruptedException {
        monitor.lockInterruptibly();

        while (!isAuto && shouldWait) waitCC.await();

        if (!isAuto) shouldWait = true;

        monitor.unlock();
    }

    @Override
    public Call waitCall() throws InterruptedException {
        Call nextCall;
        monitor.lockInterruptibly();

        while (callQueue.isEmpty()) waitCall.await();
        nextCall = callQueue.dequeue();

        monitor.unlock();

        return nextCall;
    }

    @Override
    public void informLeftEVHall() throws InterruptedException {
        monitor.lockInterruptibly();
        callQueue.enqueue(new Call(CallCenterCall.EV_PATIENT_LEFT));
        waitCall.signal();
        monitor.unlock();
    }

    @Override
    public void informLeftWTR(PatientAge age) throws InterruptedException {
        monitor.lockInterruptibly();
        callQueue.enqueue(new Call(CallCenterCall.WTR_PATIENT_LEFT, age));
        waitCall.signal();
        monitor.unlock();
    }

    @Override
    public void informLeftMDW(PatientAge age) throws InterruptedException {
        monitor.lockInterruptibly();
        callQueue.enqueue(new Call(CallCenterCall.MDW_PATIENT_LEFT, age));
        waitCall.signal();
        monitor.unlock();
    }

    @Override
    public void informLeftMDR(PatientAge age) throws InterruptedException {
        monitor.lockInterruptibly();
        callQueue.enqueue(new Call(CallCenterCall.MDR_PATIENT_LEFT, age));
        waitCall.signal();
        monitor.unlock();
    }

    // Test code
    public void informExit() {
        monitor.lock();
        callQueue.enqueue(Call.EXIT_CALL);
        waitCall.signal();
        monitor.unlock();
    }
}
