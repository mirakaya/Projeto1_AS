package HCP.Monitors.EVH;

import HCP.Enums.AvailableHalls;
import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.Simulation.MSimulationController;
import HCP.Utils.BoundedQueue;
import HCP.gui.PatientColors;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MEvaluationHall implements IEVNurse, IEVPatient {
    private final ReentrantLock monitor = new ReentrantLock();

    private final MSimulationController controller;

    private static final AvailableHalls[] roomIdxToAH = {
            AvailableHalls.EVR1,
            AvailableHalls.EVR2,
            AvailableHalls.EVR3,
            AvailableHalls.EVR4
    };

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
    public MEvaluationHall(int totalPatients, int evalTime, MSimulationController controller) {
        this.controller = controller;

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
    public void waitPatients() throws InterruptedException {
        controller.waitIfPaused();

        monitor.lockInterruptibly();
        while (occupiedRooms.isEmpty()) {
            //System.out.println("Nurse is waiting for patients");
            waitPatients.await();
        }
        monitor.unlock();
    }

    @Override
    public void evaluateNextPatient() throws InterruptedException {
        controller.waitIfPaused();

        monitor.lockInterruptibly();
        final int roomIdx = occupiedRooms.dequeue();

        controller.putNurse(roomIdxToAH[roomIdx]);

        monitor.unlock();

        TimeUnit.MILLISECONDS.sleep(evalTime);

        final PatientEvaluation evaluation = PatientEvaluation.random();

        monitor.lockInterruptibly();
        evaluations[roomIdx] = evaluation;
        waitEval[roomIdx].signal();

        controller.removeNurse(roomIdxToAH[roomIdx]);

        //System.out.println("Evaluating patient at " + roomIdx + " with " + evaluation);
        monitor.unlock();

        patientsCount++;
    }

    @Override
    public PatientEvaluation waitEvaluation(int etn, PatientAge age) throws InterruptedException {
        controller.waitIfPaused();

        PatientEvaluation evaluation;

        monitor.lockInterruptibly();

        int roomIdx = freeRooms.dequeue();
        Condition room = waitEval[roomIdx];
        occupiedRooms.enqueue(roomIdx);
        waitPatients.signal();

        controller.log(age, etn, roomIdxToAH[roomIdx]);
        controller.putPatient(roomIdxToAH[roomIdx], age, etn, PatientColors.GRAY);

        while(evaluations[roomIdx] == PatientEvaluation.NONE) {
            room.await();
        }


        evaluation = evaluations[roomIdx];
        evaluations[roomIdx] = PatientEvaluation.NONE;
        freeRooms.enqueue(roomIdx);

        controller.log(age, etn, evaluation, roomIdxToAH[roomIdx]);
        controller.removePatient(roomIdxToAH[roomIdx], age, etn);

        //System.out.println("Patient at room " + roomIdx + " was evaluated with " + evaluation);
        monitor.unlock();

        return evaluation;
    }
}
