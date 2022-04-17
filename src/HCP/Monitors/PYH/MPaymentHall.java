package HCP.Monitors.PYH;

import HCP.Monitors.ConditionHallQueue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MPaymentHall implements IPYPatient, IPYCashier{

    private final ReentrantLock monitor = new ReentrantLock();

    private final Condition waitForPatient = monitor.newCondition();

    private boolean isSomeonePaying = false;
    private final ConditionHallQueue orderedWaitPYHCall = new ConditionHallQueue();

    private int totalPatients;

    private int patientsProcessed = 0;

    private int patientsArrived = 0;
    private int timePay;

    public MPaymentHall(int totalPatients, int timePay) {

        this.totalPatients = totalPatients;
        this.timePay = timePay;

    }


    //Cashier
    @Override
    public boolean anyPatientsLeft(){

        monitor.lock();

        if (totalPatients -1 == patientsProcessed){
            boolean returnValue = false;
            monitor.unlock();
            return returnValue;
        } else {
            boolean returnValue = true;
            monitor.unlock();
            return returnValue;
        }

    }

    @Override
    public void waitPatients() {

        monitor.lock();

        if (patientsArrived == patientsProcessed ){
            try {
                waitForPatient.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        monitor.unlock();

    }

    @Override
    public void processNextPatient() {

        monitor.lock();

        try {
            TimeUnit.MILLISECONDS.sleep(timePay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        patientsProcessed++;
        orderedWaitPYHCall.signal();

        monitor.unlock();

    }

    //Patient
    @Override
    public void waitPayment(int id) {

        monitor.lock();
        System.out.println("Patient with id " + id + " entered WTR");

        if (isSomeonePaying == true) {
            patientsArrived++;
            orderedWaitPYHCall.await(monitor.newCondition());
        }
        else{
            isSomeonePaying = true;
            waitForPatient.signal();
        }

        System.out.println(id + " exited WTR");
        monitor.unlock();

    }

}
