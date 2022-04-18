package HCP.Monitors.EH;

import HCP.Enums.AvailableHalls;
import HCP.Enums.PatientAge;
import HCP.Monitors.Simulation.MSimulationController;
import HCP.Utils.BoundedQueue;
import HCP.Utils.Counter;
import HCP.Utils.OrderedMonitor;
import HCP.gui.PatientColors;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MEntranceHall implements IEntranceHallCallCenter, IEntranceHallPatient {
    private final static Map<PatientAge, AvailableHalls> ageToEtr = Map.of(
            PatientAge.CHILD, AvailableHalls.ET1,
            PatientAge.ADULT, AvailableHalls.ET2
    );

    private final ReentrantLock monitor = new ReentrantLock();
    private final MSimulationController controller;

    // Seats for the patients
    private final HashMap<PatientAge, OrderedMonitor> rooms = new HashMap<>();
    // Number of free rooms in the Evaluation Hall if none has been
    // attributed to an already waiting patient
    private int evFreeRooms = 4;
    // Counts the amount of patients seating in a room
    private final Counter counter;
    // Queue used to determine what is the age of the next patient
    private final BoundedQueue<PatientAge> ageQueue;
    // Hashmap used to map the patients to the proper Condition
    private final HashMap<PatientAge, Condition> waitFreeSeats = new HashMap<>();

    private int ethNumberCounter = 1;

    public MEntranceHall(int nSeats, MSimulationController controller) {
        this.controller = controller;

        ageQueue = new BoundedQueue<>(nSeats);
        counter = new Counter(nSeats / 2);

        for (PatientAge age : PatientAge.values()) {
            waitFreeSeats.put(age, monitor.newCondition());
            rooms.put(age, new OrderedMonitor(nSeats / 2, monitor));
        }
    }

    @Override
    public void waitFreeRoom(int patientId, PatientAge age) throws InterruptedException {
        controller.waitIfPaused();
        monitor.lockInterruptibly();

        final Condition waitFreeSeat = waitFreeSeats.get(age);
        controller.log(age, patientId, AvailableHalls.BEN);
        controller.putPatient(AvailableHalls.BEN, age, patientId, PatientColors.GRAY);

        while (counter.reachedLimit(age)) {
            // Test code
            //System.out.println(age + " patient is waiting for free rooms in eth");
            // End of test code
            waitFreeSeat.await();
        }

        counter.increment(age);

        monitor.unlock();
    }

    @Override
    public int waitEvaluationHallCall(int patientId, PatientAge age) throws InterruptedException {
        controller.waitIfPaused();
        monitor.lockInterruptibly();

        final int etn = ethNumberCounter++;
        final OrderedMonitor room = rooms.get(age);
        controller.log(age, etn, AvailableHalls.BEN);
        controller.removePatient(AvailableHalls.BEN, age, patientId);
        controller.putPatient(ageToEtr.get(age), age, etn, PatientColors.GRAY);

        if (evFreeRooms == 0) {
            // Test code
            //System.out.println(age + " patient " + ethNumber + " is waiting for free rooms on evh");
            // End of test code
            ageQueue.enqueue(age);
            room.await();
        } else {
            evFreeRooms--;
        }

        counter.decrement(age);

        // Test code
        //System.out.println(age + " patient " + ethNumber + " is leaving");
        // End of test code

        monitor.unlock();

        return etn;
    }

    @Override
    public void awakeWaitingPatient(int etn, PatientAge age) throws InterruptedException {
        controller.waitIfPaused();

        monitor.lockInterruptibly();

        waitFreeSeats.get(age).signal();
        controller.removePatient(ageToEtr.get(age), age, etn);

        monitor.unlock();
    }

    @Override
    public void informEvaluationRoomFree() throws InterruptedException {
        monitor.lockInterruptibly();
        if (!ageQueue.isEmpty()) {
            PatientAge age = ageQueue.dequeue();
            rooms.get(age).awake();
        } else {
            evFreeRooms++;
        }
        monitor.unlock();
    }
}
