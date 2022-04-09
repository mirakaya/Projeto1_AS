package HCP.Monitors.EH;

import HCP.Enums.PatientAge;
import HCP.Utils.BoundedQueue;
import HCP.Utils.OrderedMonitor;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MEntranceHall implements IEntranceHallCallCenter, IEntranceHallPatient {
    private final ReentrantLock monitor = new ReentrantLock();

    // Seats for the patients
    private final HashMap<PatientAge, OrderedMonitor> rooms = new HashMap<>();
    // Counts the amount of patients seating in a room
    private final HashMap<PatientAge, Integer> seatsCounter = new HashMap<>();
    // Queue used to determine what is the age of the next patient
    private final BoundedQueue<PatientAge> ageQueue;
    // Hashmap used to map the patients to the proper Condition
    // with PatientAge as a key
    private final HashMap<PatientAge, Condition> waitFreeSeats = new HashMap<>();

    private int ethNumberCounter = 1;

    public MEntranceHall(int nSeats) {
        ageQueue = new BoundedQueue<>(nSeats);

        for (PatientAge age : PatientAge.values()) {
            waitFreeSeats.put(age, monitor.newCondition());
            rooms.put(age, new OrderedMonitor(nSeats / 2, monitor));
            seatsCounter.put(age, 0);
        }
    }

    @Override
    public void waitFreeRoom(int patientId, PatientAge age) {
        monitor.lock();

        final Condition waitFreeSeat = waitFreeSeats.get(age);

        try {
            while (seatsCounter.get(age) == rooms.size()) {
                waitFreeSeat.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        seatsCounter.put(age, seatsCounter.get(age) + 1);

        monitor.unlock();
    }

    @Override
    public void waitEvaluationHallCall(PatientAge age) {
        monitor.lock();

        final int ethNumber = ethNumberCounter++;
        final OrderedMonitor room = rooms.get(age);

        ageQueue.enqueue(age);
        room.await();
        seatsCounter.put(age, seatsCounter.get(age) - 1);

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
        PatientAge age = ageQueue.dequeue();
        rooms.get(age).awake();
        monitor.unlock();
    }
}
