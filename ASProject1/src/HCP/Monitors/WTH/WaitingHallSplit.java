package HCP.Monitors.WTH;

import HCP.Enums.AvailableHalls;
import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.ConditionHallQueue;
import HCP.Monitors.MHallNumber;
import HCP.Monitors.MTickets;
import HCP.Monitors.Simulation.MSimulationController;
import HCP.gui.PatientColors;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class WaitingHallSplit {
    private final static Map<PatientEvaluation, PatientColors> evalToColor = Map.of(
            PatientEvaluation.BLUE, PatientColors.BLUE,
            PatientEvaluation.YELLOW, PatientColors.YELLOW,
            PatientEvaluation.RED, PatientColors.RED
    );

    private final MSimulationController controller;
    private final AvailableHalls hall;

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


    public WaitingHallSplit(
            int capacity, int roomSeats,
            int mdwCapacity, MHallNumber hallNumber,
            AvailableHalls hall,
            MSimulationController controller
    ) {
        this.controller = controller;
        this.hall = hall;

        this.hallNumber = hallNumber;

        wtnExitTicker = new MTickets(capacity);
        wthExitTicker = new MTickets(capacity);
        freeWTRCounter = roomSeats;

        freeMDWCounter = mdwCapacity;
    }


    public int waitWTRFree(PatientAge age, PatientEvaluation evaluation) throws InterruptedException {
        controller.waitIfPaused();

        wtnMonitor.lock();
        int wtn = hallNumber.getNextHallHumber();
        int wtnExitTicket = wtnExitTicker.acquire();
        wtnMonitor.unlock();

        wtnExitTicker.enter(wtnExitTicket);

        wthMonitor.lock();
        wtnExitTicker.exit();

        controller.log(age, wtn, evaluation, AvailableHalls.WTH);
        controller.putPatient(AvailableHalls.WTH, age, wtn, evalToColor.get(evaluation));
        //System.out.println(age + " patient with wtn " + wtn + " entered WTH");

        int wthExitTicket = wthExitTicker.acquire();

        if (freeWTRCounter == 0) {
            waitingWTHCounter++;
            orderedWaitWTRCall.await(wthMonitor.newCondition());
        } else
            freeWTRCounter--;

        controller.removePatient(AvailableHalls.WTH, age, wtn);
        wthMonitor.unlock();

        wthExitTicker.enter(wthExitTicket);

        //System.out.println(age + " Patient with wtn " + wtn + " exited WTH");

        return wtn;
    }


    public void waitMDWCall(int wtn, PatientAge age, PatientEvaluation evaluation) throws InterruptedException {
        controller.waitIfPaused();

        wtrMonitor.lockInterruptibly();
        wthExitTicker.exit();

        controller.log(age, wtn, hall);
        controller.putPatient(hall, age, wtn, evalToColor.get(evaluation));
        //System.out.println(age + " Patient with wtn " + wtn + " and evaluation " + evaluation + " entered WTR");

        if (freeMDWCounter == 0) {
            waitingMDWCounter++;
            orderedWaitMDWCall.await(wtrMonitor.newCondition(), evaluation);
        }
        else
            freeMDWCounter--;

        controller.removePatient(hall, age, wtn);
        //System.out.println(age + " Patient with wtn " + wtn + " and evaluation " + evaluation + " exited WTR");
        wtrMonitor.unlock();
    }


    public void informWTRFree(PatientAge age) throws InterruptedException {
        controller.waitIfPaused();

        wthMonitor.lockInterruptibly();
        if (waitingWTHCounter > 0) {
            waitingWTHCounter--;
            orderedWaitWTRCall.signal();
        } else
            freeWTRCounter++;
        wthMonitor.unlock();
    }


    public void informMDWFree(PatientAge age) throws InterruptedException {
        controller.waitIfPaused();

        wtrMonitor.lockInterruptibly();

        if (waitingMDWCounter > 0) {
            waitingMDWCounter--;
            // System.out.println(age + " ");
            orderedWaitMDWCall.signal();
        } else
            freeMDWCounter++;

        wtrMonitor.unlock();
    }
}
