package HCP.Monitors.PYH;

import HCP.Enums.AvailableHalls;
import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.ConditionHallQueue;
import HCP.Monitors.Simulation.MSimulationController;
import HCP.gui.PatientColors;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MPaymentHall implements IPYPatient, IPYCashier {
    private final static Map<PatientEvaluation, PatientColors> evalToColor = Map.of(
            PatientEvaluation.BLUE, PatientColors.BLUE,
            PatientEvaluation.YELLOW, PatientColors.YELLOW,
            PatientEvaluation.RED, PatientColors.RED
    );

    private final MSimulationController controller;

    private final ReentrantLock monitor = new ReentrantLock();

    private boolean isSomeonePaying = false;
    private final Condition waitForPatient = monitor.newCondition();

    private final ConditionHallQueue orderedWaitPYHCall = new ConditionHallQueue();

    private boolean isConcluded = false;
    private final Condition waitConcluded = monitor.newCondition();

    private final int totalPatients;

    private int patientsProcessed = 0;

    private final int timePay;

    private int pynCounter = 1;

    public MPaymentHall(int totalPatients, int timePay, MSimulationController controller) {
        this.controller = controller;
        this.totalPatients = totalPatients;
        this.timePay = timePay;

    }


    //Cashier
    @Override
    public boolean anyPatientsLeft() {
        return totalPatients == patientsProcessed;
    }

    @Override
    public void waitPatients() throws InterruptedException {
        controller.waitIfPaused();

        monitor.lockInterruptibly();

        while (!isSomeonePaying) {
            waitForPatient.await();
        }

        monitor.unlock();

    }

    @Override
    public void processNextPatient() throws InterruptedException{
        controller.waitIfPaused();

        monitor.lockInterruptibly();

        controller.putCashier();
        TimeUnit.MILLISECONDS.sleep(timePay);

        patientsProcessed++;
        isConcluded = true;
        controller.removeCashier();

        waitConcluded.signal();

        monitor.unlock();

    }

    //Patient
    @Override
    public void waitPayment(PatientAge age, PatientEvaluation evaluation) throws InterruptedException {
        controller.waitIfPaused();

        monitor.lockInterruptibly();
        int pyn = pynCounter++;

        controller.log(age, pyn, evaluation, AvailableHalls.PYH);
        controller.putPatient(AvailableHalls.PYH, age, pyn, evalToColor.get(evaluation));
        //System.out.println("Patient with id " + id + " entered WTR");

        while (isSomeonePaying) {
            orderedWaitPYHCall.await(monitor.newCondition());
        }

        isSomeonePaying = true;
        waitForPatient.signal();

        while(!isConcluded) {
            waitConcluded.await();
        }

        controller.log(age, pyn, evaluation, AvailableHalls.AEX);
        controller.removePatient(AvailableHalls.PYH, age, pyn);
        controller.putPatient(AvailableHalls.AEX, age, pyn, evalToColor.get(evaluation));
        //System.out.println(id + " exited WTR");

        isConcluded = false;
        orderedWaitPYHCall.signal();
        monitor.unlock();

    }

}
