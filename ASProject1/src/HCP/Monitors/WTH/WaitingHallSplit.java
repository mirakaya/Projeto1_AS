package HCP.Monitors.WTH;

import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.ConditionHallQueue;
import HCP.Monitors.MHallNumber;
import HCP.Monitors.MTickets;

import java.util.concurrent.locks.ReentrantLock;

public class WaitingHallSplit {

    // Variables that control access to the wtn and wtn exit tickets
    private final ReentrantLock wtnMonitor = new ReentrantLock();
    private final MHallNumber hallNumber;
    private final MTickets wtnExitTicker;

    // Variables that control access to the WTH
    private final ReentrantLock wthMonitor = new ReentrantLock();
    private final MTickets wthExitTicker;
    private int freeWTRCounter;
    private int waitingWTHCounter = 0;
    private final ConditionHallQueue orderedWaitWTRCall = new ConditionHallQueue();

    // Variables that control access to the WTR
    private final ReentrantLock wtrMonitor = new ReentrantLock();
    private int freeMDWCounter;
    private int waitingMDWCounter = 0;
    private final ConditionEvaluationQueue orderedWaitMDWCall = new ConditionEvaluationQueue();

    public WaitingHallSplit(int capacity, int roomSeats, int mdwCapacity, MHallNumber hallNumber) {
        this.hallNumber = hallNumber;

        wtnExitTicker = new MTickets(capacity);
        wthExitTicker = new MTickets(capacity);
        freeWTRCounter = roomSeats;

        freeMDWCounter = mdwCapacity;
    }

    public int waitWTRFree(PatientAge age) {
        wtnMonitor.lock();
        int wtn = hallNumber.getNextHallHumber();
        int wtnExitTicket = wtnExitTicker.acquire();
        wtnMonitor.unlock();

        wtnExitTicker.enter(wtnExitTicket);

        wthMonitor.lock();
        wtnExitTicker.exit();

        System.out.println(age + " patient with wtn " + wtn + " entered WTH");

        int wthExitTicket = wthExitTicker.acquire();

        if (freeWTRCounter == 0) {
            waitingWTHCounter++;
            orderedWaitWTRCall.await(wthMonitor.newCondition());
        } else
            freeWTRCounter--;

        wthMonitor.unlock();

        wthExitTicker.enter(wthExitTicket);

        System.out.println(age + " Patient with wtn " + wtn + " exited WTH");

        return wtn;
    }


    public void waitMDWCall(int wtn, PatientAge age, PatientEvaluation evaluation) {
        wtrMonitor.lock();
        System.out.println(age + " Patient with wtn " + wtn + " and evaluation " + evaluation + " entered WTR");
        wthExitTicker.exit();

        if (freeMDWCounter == 0) {
            waitingMDWCounter++;
            orderedWaitMDWCall.await(wtrMonitor.newCondition(), evaluation);
        }
        else
            freeMDWCounter--;

        System.out.println(age + " Patient with wtn " + wtn + " and evaluation " + evaluation + " exited WTR");
        wtrMonitor.unlock();
    }


    public void informWTRFree(PatientAge age) {
        wthMonitor.lock();
        if (waitingWTHCounter > 0) {
            waitingWTHCounter--;
            orderedWaitWTRCall.signal();
        } else
            freeWTRCounter++;
        wthMonitor.unlock();
    }


    public void informMDWFree(PatientAge age) {
        wtrMonitor.lock();

        if (waitingMDWCounter > 0) {
            waitingMDWCounter--;
            orderedWaitMDWCall.signal();
        } else
            freeMDWCounter++;

        wtrMonitor.unlock();
    }
}
