package HCP.Monitors.MDH;

import HCP.Enums.PatientAge;
import HCP.Monitors.MTickets;
import HCP.Utils.BoundedQueue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MedicalHallSplit {
    private final ReentrantLock hallMonitor = new ReentrantLock();
    private final BoundedQueue<Integer> mdrFreeRooms = new BoundedQueue<>(2);
    // Ensures that if the thread waiting here hasn't exited yet, an incoming thread will
    // not bypass the waiting thread
    private final MTickets ticker;
    private final Condition waitMDRFree = hallMonitor.newCondition();
    private final int treatmentTime;

    public MedicalHallSplit(int patientsCount, int treatmentTime) {
        ticker = new MTickets(patientsCount);

        this.treatmentTime = treatmentTime;
        mdrFreeRooms.enqueue(1);
        mdrFreeRooms.enqueue(2);
    }

    public int waitMDRCall(PatientAge age, int wtn) throws InterruptedException {
        int ticket = ticker.acquire();

        ticker.enter(ticket);
        hallMonitor.lock();

        // System.out.println(age + " patient with wtn " + wtn + " has entered the MDW");
        while (mdrFreeRooms.isEmpty()) waitMDRFree.await();

        int freeRoom = mdrFreeRooms.dequeue();

        // System.out.println(age + " patient with wtn " + wtn + " is leaving MDW to MDR " + freeRoom);

        hallMonitor.unlock();
        ticker.exit();

        return freeRoom;
    }


    public void waitMDRConcluded(PatientAge age, int wtn, int room) throws InterruptedException {
        try {
            // System.out.println(age + " patient with wtn " + wtn + " is being treated at MDR " + room);
            TimeUnit.MILLISECONDS.sleep(treatmentTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hallMonitor.lockInterruptibly();
        mdrFreeRooms.enqueue(room);
        // System.out.println(age + " patient with wtn " + wtn + " is leaving MDR " + room);
        hallMonitor.unlock();
    }


    public void informMDRFree(PatientAge age) throws InterruptedException {
        hallMonitor.lockInterruptibly();
        // System.out.println("Informing there is room free MDR " + age);
        waitMDRFree.signal();
        hallMonitor.unlock();
    }
}
