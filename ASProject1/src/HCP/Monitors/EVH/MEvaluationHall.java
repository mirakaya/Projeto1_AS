package HCP.Monitors.EVH;

import HCP.Enums.PatientEvaluation;
import HCP.Utils.BoundedQueue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MEvaluationHall implements IEVNurse, IEVPatient {
    private final ReentrantLock monitor = new ReentrantLock();

    // Number of patients in the simulation
    private final int totalPatients;
    // Number of patients evaluated
    private int patientsCount = 0;
    // Time that the evaluation takes;
    private final int evalTime;

    // Number of rooms in the hall
    private final int roomCount = 4;
    // Queue with the free rooms
    private final BoundedQueue<Integer> freeRooms = new BoundedQueue<>(roomCount);
    // Queue with the occupied rooms
    private final BoundedQueue<Integer> occupiedRooms = new BoundedQueue<>(roomCount);
    // Conditions for the patients to wait at
    private final Condition[] waitEval = new Condition[roomCount];
    // Condition for the nurse to wait for patients
    private final Condition waitPatients = monitor.newCondition();
    // Storage of the evaluations for the patients
    private final PatientEvaluation[] evaluations = new PatientEvaluation[roomCount];

    /**
     * Create a new instance of Evaluation Hall
     * @param totalPatients Number of patients present in the simulation
     * @param evalTime Time in milliseconds that the nurse takes to make an evaluation
     */
    public MEvaluationHall(int totalPatients, int evalTime) {
        this.totalPatients = totalPatients;
        this.evalTime = evalTime;

        for (int i = 0; i < roomCount; i++) {
            freeRooms.enqueue(i);
            waitEval[i] = monitor.newCondition();
            evaluations[i] = PatientEvaluation.NONE;
        }
    }

    @Override
    public boolean anyPatientsLeft() {
        return this.totalPatients > this.patientsCount;
    }

    @Override
    public void waitPatients() {
        monitor.lock();
        try {
            while (occupiedRooms.isEmpty()) {
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
        try {
            TimeUnit.MILLISECONDS.sleep(evalTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final PatientEvaluation evaluation = PatientEvaluation.random();

        monitor.lock();
        final int roomIdx = occupiedRooms.dequeue();

        evaluations[roomIdx] = evaluation;
        waitEval[roomIdx].signal();

        //System.out.println("Evaluating patient at " + roomIdx + " with " + evaluation);
        monitor.unlock();

        patientsCount++;
    }

    @Override
    public Object[] waitEvaluation() {
        PatientEvaluation evaluation;

        monitor.lock();

        int roomIdx = freeRooms.dequeue();
        Condition room = waitEval[roomIdx];
        occupiedRooms.enqueue(roomIdx);
        waitPatients.signal();

        try {
            //System.out.println("Patient waiting eval at room " + roomIdx);
            while(evaluations[roomIdx] == PatientEvaluation.NONE) {
                room.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        evaluation = evaluations[roomIdx];
        evaluations[roomIdx] = PatientEvaluation.NONE;
        freeRooms.enqueue(roomIdx);

        //System.out.println("Patient at room " + roomIdx + " was evaluated with " + evaluation);
        monitor.unlock();

        Object [] result = {evaluation, roomIdx};

        return result;
    }
}
