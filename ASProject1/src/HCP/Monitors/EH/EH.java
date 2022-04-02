package HCP.Monitors.EH;

import HCP.Monitors.OrderedMonitor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class EH implements IEHControlCenter, IEHPatient {
    private final ReentrantLock monitor = new ReentrantLock();

    private final Condition waitChildSeat = monitor.newCondition();
    private boolean childSeatFull = false;

    private final Condition waitAdultSeat = monitor.newCondition();
    private boolean adultSeatFull = false;

    private final OrderedMonitor patientQueue;

    public EH(int nPatients) {
        this.patientQueue = new OrderedMonitor(nPatients);
    }

    @Override
    public void informFreeChildSeat() {
        this.monitor.lock();
        this.childSeatFull = false;
        this.waitChildSeat.signal();
        this.monitor.unlock();
    }

    @Override
    public void informFreeAdultSeat() {
        this.monitor.lock();
        this.adultSeatFull = false;
        this.waitAdultSeat.signal();
        this.monitor.unlock();
    }

    @Override
    public void enterChild() {
        try {
            this.monitor.lock();
            while (childSeatFull)
                this.waitChildSeat.await();

            this.childSeatFull = true;
            // Need to give a number to the patient
        } catch (InterruptedException ignored) {}
        finally {
            this.monitor.unlock();
        }

        this.patientQueue.await();
    }

    @Override
    public void enterAdult() {
        try {
            this.monitor.lock();
            while (adultSeatFull)
                this.waitAdultSeat.await();

            this.adultSeatFull = true;
            // Need to give a number to the patient
            this.monitor.unlock();
        } catch (InterruptedException ignored) {}
        finally {
            this.monitor.unlock();
        }

        this.patientQueue.await();
    }
}
