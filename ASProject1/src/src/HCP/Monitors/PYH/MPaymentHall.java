package HCP.Monitors.PYH;

import HCP.Entities.TPatient;
import HCP.Enums.PatientEvaluation;
import HCP.Utils.BoundedQueue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MPaymentHall implements IPYPatient, IPYCashier{

    private final ReentrantLock monitor = new ReentrantLock();

    // Number of patients in the simulation
    private int totalPatients = 0;
    // Number of patients evaluated
    private int patientsCount = 0;
    // Time that the evaluation takes;
    private int timePay;

    // Queue with the patients rooms
    private final BoundedQueue<Integer> patients;

    // Condition for the nurse to wait for patients
    private final Condition waitPatients = monitor.newCondition();

    private boolean isSomeonePaying;


    /**
     * Create a new instance of Evaluation Hall
     * @param totalPatients Number of patients present in the simulation
     * @param evalTime Time in milliseconds that the nurse takes to make an evaluation
     */
    public MPaymentHall(int totalPatients, int evalTime) {
        this.totalPatients = totalPatients;
        this.patients = new BoundedQueue<>(totalPatients);
        this.timePay = evalTime;
        this.isSomeonePaying = false;
    }

    @Override
    public boolean anyPatientsLeft() {
        return this.totalPatients > this.patientsCount;
    }

    @Override
    public void waitPatients() {
        monitor.lock();
        try {
            while (patients.isEmpty()) {
                //System.out.println("Nurse is waiting for patients");
                waitPatients.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        monitor.unlock();
    }

    @Override
    public void evaluateNextPatient() {

        monitor.lock();

        try {
            TimeUnit.MILLISECONDS.sleep(timePay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        patients.dequeue();

        waitPatients.signal();

        //System.out.println("Evaluating patient at " + roomIdx + " with " + evaluation);
        monitor.unlock();

        patientsCount++;
    }

    @Override
    public void waitPayment(int id) {

        monitor.lock();

        patients.enqueue(id);

        waitPatients.signal();

        System.out.println("Patient " + id +" payed.");
        monitor.unlock();
    }

}
