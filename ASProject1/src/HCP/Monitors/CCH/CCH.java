package HCP.Monitors.CCH;

import HCP.Monitors.BoundedQueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of the Control Center Hall
 */
public class CCH implements ICCHPatient, ICCHControlCenter {
    private final ReentrantLock monitor;

    private final int nPatients;
    private int nPatientsTreated;

    private final BoundedQueue<CCHCall> callQueue;
    private final Condition waitCall;

    public CCH(int nPatients) {
        this.monitor = new ReentrantLock();

        this.nPatients = nPatients;
        this.nPatientsTreated = 0;

        this.callQueue = new BoundedQueue<>(nPatients * 6);
        this.waitCall = this.monitor.newCondition();
    }

    @Override
    public CCHCall nextCall() {
        CCHCall next = CCHCall.CLOSE_CC;

        try {
            this.monitor.lock();
            while(this.callQueue.isEmpty()) {
                this.waitCall.await();
            }

            next = this.callQueue.dequeue();
        } catch (InterruptedException ignored) {}
        finally {
            this.monitor.unlock();
        }

        return next;
    }

    @Override
    public void informChildLeftEH() {
        this.monitor.lock();
        this.callQueue.enqueue(CCHCall.CHILD_PATIENT_LEFT);
        this.monitor.unlock();
    }

    @Override
    public void informAdultLeftEH() {
        this.monitor.lock();
        this.callQueue.enqueue(CCHCall.ADULT_PATIENT_LEFT);
        this.monitor.unlock();
    }

    @Override
    public void informTreated() {
        this.monitor.lock();

        this.nPatientsTreated++;

        if (this.nPatientsTreated == this.nPatients) {
            this.callQueue.enqueue(CCHCall.CLOSE_CC);
        }

        this.monitor.unlock();
    }
}
