package HCP.Monitors.EH;

import HCP.Enums.PatientAge;
import HCP.Utils.BoundedQueue;
import HCP.Utils.Counter;
import HCP.Utils.OrderedMonitor;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MEntranceHall implements IEntranceHallCallCenter, IEntranceHallPatient {
    private final ReentrantLock monitor = new ReentrantLock();

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

    public MEntranceHall(int nSeats) {
        ageQueue = new BoundedQueue<>(nSeats);
        counter = new Counter(nSeats / 2);

        for (PatientAge age : PatientAge.values()) {
            waitFreeSeats.put(age, monitor.newCondition());
            rooms.put(age, new OrderedMonitor(nSeats / 2, monitor));
        }
    }

    @Override
    public void waitFreeRoom(int patientId, PatientAge age) {
        monitor.lock();

        final Condition waitFreeSeat = waitFreeSeats.get(age);

        try {
            while (counter.reachedLimit(age)) {
                // Test code
                System.out.println(age + " patient is waiting for free rooms in eth");
                // End of test code
                waitFreeSeat.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        counter.increment(age);

        monitor.unlock();
    }

    @Override
    public void waitEvaluationHallCall(PatientAge age) {
        monitor.lock();

        final int ethNumber = ethNumberCounter++;
        final OrderedMonitor room = rooms.get(age);

        if (evFreeRooms == 0) {
            // Test code
            System.out.println(age + " patient " + ethNumber + " is waiting for free rooms on evh");
            // End of test code
            ageQueue.enqueue(age);
            room.await();
        } else {
            evFreeRooms--;
        }

        counter.decrement(age);

        // Test code
        System.out.println(age + " patient " + ethNumber + " is leaving");
        // End of test code

        monitor.unlock();
    }

    @Override
    public void awakeWaitingPatient(PatientAge age) {
        monitor.lock();
        waitFreeSeats.get(age).signal();
        monitor.unlock();
    }

    @Override
    public void informEvaluationRoomFree() {
        monitor.lock();
        if (!ageQueue.isEmpty()) {
            PatientAge age = ageQueue.dequeue();
            rooms.get(age).awake();
        } else {
            evFreeRooms++;
        }
        monitor.unlock();
    }
}
