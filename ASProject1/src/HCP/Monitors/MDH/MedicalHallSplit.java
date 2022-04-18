package HCP.Monitors.MDH;

import HCP.Enums.AvailableHalls;
import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.MTickets;
import HCP.Monitors.Simulation.MSimulationController;
import HCP.Utils.BoundedQueue;
import HCP.gui.PatientColors;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MedicalHallSplit {
    private final static Map<PatientEvaluation, PatientColors> evalToColor = Map.of(
            PatientEvaluation.BLUE, PatientColors.BLUE,
            PatientEvaluation.YELLOW, PatientColors.YELLOW,
            PatientEvaluation.RED, PatientColors.RED
    );
    private final AvailableHalls[] roomToHall;

    private final MSimulationController controller;

    private final ReentrantLock hallMonitor = new ReentrantLock();
    private final BoundedQueue<Integer> mdrFreeRooms = new BoundedQueue<>(2);
    // Ensures that if the thread waiting here hasn't exited yet, an incoming thread will
    // not bypass the waiting thread
    private final MTickets ticker;
    private final Condition waitMDRFree = hallMonitor.newCondition();
    private final int treatmentTime;

    public MedicalHallSplit(
            int patientsCount, int treatmentTime,
            AvailableHalls[] roomToHall, MSimulationController controller
    ) {
        this.roomToHall = roomToHall;
        this.controller = controller;

        ticker = new MTickets(patientsCount);

        this.treatmentTime = treatmentTime;
        mdrFreeRooms.enqueue(0);
        mdrFreeRooms.enqueue(1);
    }

    public int waitMDRCall(PatientAge age, int wtn, PatientEvaluation evaluation) throws InterruptedException {
        controller.waitIfPaused();

        int ticket = ticker.acquire();

        ticker.enter(ticket);
        hallMonitor.lock();

        controller.log(age, wtn, evaluation, AvailableHalls.MDH);
        controller.putPatient(AvailableHalls.MDH, age, wtn, evalToColor.get(evaluation));
        // System.out.println(age + " patient with wtn " + wtn + " has entered the MDW");
        while (mdrFreeRooms.isEmpty()) waitMDRFree.await();

        int freeRoom = mdrFreeRooms.dequeue();

        controller.removePatient(AvailableHalls.MDH, age, wtn);
        // System.out.println(age + " patient with wtn " + wtn + " is leaving MDW to MDR " + freeRoom);

        hallMonitor.unlock();
        ticker.exit();

        return freeRoom;
    }


    public void waitMDRConcluded(
            PatientAge age, int wtn,
            int room, PatientEvaluation evaluation
    ) throws InterruptedException {
        controller.waitIfPaused();

        controller.log(age, wtn, evaluation, roomToHall[room]);
        controller.putPatient(roomToHall[room], age, wtn, evalToColor.get(evaluation));

        TimeUnit.MILLISECONDS.sleep(treatmentTime);
        // System.out.println(age + " patient with wtn " + wtn + " is being treated at MDR " + room);

        hallMonitor.lockInterruptibly();
        mdrFreeRooms.enqueue(room);

        controller.removePatient(roomToHall[room], age, wtn);
        // System.out.println(age + " patient with wtn " + wtn + " is leaving MDR " + room);

        hallMonitor.unlock();
    }


    public void informMDRFree(PatientAge age) throws InterruptedException {
        controller.waitIfPaused();

        hallMonitor.lockInterruptibly();
        // System.out.println("Informing there is room free MDR " + age);
        waitMDRFree.signal();
        hallMonitor.unlock();
    }
}
