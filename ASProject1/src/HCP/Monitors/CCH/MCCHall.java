package HCP.Monitors.CCH;

import HCP.Enums.CallCenterCall;
import HCP.Enums.PatientAge;
import HCP.Monitors.Simulation.MSimulationController;
import HCP.Utils.UnboundedQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementations of the shared region for the Call Center
 */
public class MCCHall implements ICCHallPatient, ICCHallCallCenter, ICCHallSimulation{
    private final ReentrantLock monitor = new ReentrantLock();
    private final MSimulationController controller;

    // Tells whether CC is operating in auto or manual mode
    private boolean isAuto = true;
    // Tells whether an order from CCP was already given
    private int movementCount = 1;
    // Condition where CC will wait for an order from CCP
    private final Condition waitCC = monitor.newCondition();

    // Queue with calls to the call center
    private final UnboundedQueue<Call> callQueue = new UnboundedQueue<>();
    private final Condition waitCall = monitor.newCondition();

    public MCCHall(MSimulationController controller) {
        this.controller = controller;
    }

    @Override
    public void waitManualOrder() throws InterruptedException {
        controller.waitIfPaused();
        monitor.lockInterruptibly();

        while (!isAuto && movementCount > 1) waitCC.await();

        if (!isAuto) movementCount--;

        monitor.unlock();
    }

    @Override
    public Call waitCall() throws InterruptedException {
        Call nextCall;

        controller.waitIfPaused();
        monitor.lockInterruptibly();

        while (callQueue.isEmpty()) waitCall.await();
        nextCall = callQueue.dequeue();

        monitor.unlock();

        return nextCall;
    }

    @Override
    public void informLeftEVHall() throws InterruptedException {
        controller.waitIfPaused();
        monitor.lockInterruptibly();
        callQueue.enqueue(new Call(CallCenterCall.EV_PATIENT_LEFT));
        waitCall.signal();
        monitor.unlock();
    }

    @Override
    public void informLeftWTR(PatientAge age) throws InterruptedException {
        controller.waitIfPaused();
        monitor.lockInterruptibly();
        callQueue.enqueue(new Call(CallCenterCall.WTR_PATIENT_LEFT, age));
        waitCall.signal();
        monitor.unlock();
    }

    @Override
    public void informLeftMDW(PatientAge age) throws InterruptedException {
        controller.waitIfPaused();
        monitor.lockInterruptibly();
        callQueue.enqueue(new Call(CallCenterCall.MDW_PATIENT_LEFT, age));
        waitCall.signal();
        monitor.unlock();
    }

    @Override
    public void informLeftMDR(PatientAge age) throws InterruptedException {
        controller.waitIfPaused();
        monitor.lockInterruptibly();
        callQueue.enqueue(new Call(CallCenterCall.MDR_PATIENT_LEFT, age));
        waitCall.signal();
        monitor.unlock();
    }

    @Override
    public void informExit() {
        monitor.lock();
        callQueue.enqueue(Call.EXIT_CALL);
        waitCall.signal();
        monitor.unlock();
    }

    @Override
    public void setAuto(boolean isAuto) {
        monitor.lock();
        this.isAuto = isAuto;
        monitor.unlock();
    }

    @Override
    public void manualMove() {
        monitor.lock();
        movementCount++;
        waitCall.signal();
        monitor.unlock();
    }
}
