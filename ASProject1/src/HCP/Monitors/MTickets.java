package HCP.Monitors;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MTickets {
    private final ReentrantLock monitor = new ReentrantLock();
    private final int limit;
    private int ticketCounter = 1;
    private int nextTicket = 1;
    private final Condition[] turns;

    public MTickets(int capacity) {
        limit = capacity;
        turns = new Condition[capacity];

        for (int i = 0; i < capacity; i++) {
            turns[i] = monitor.newCondition();
        }
    }

    public int acquire() throws InterruptedException {
        int ticket;

        monitor.lockInterruptibly();
        ticket = ticketCounter++;
        monitor.unlock();

        return ticket;
    }

    public void enter(int ticket) throws InterruptedException {
        monitor.lockInterruptibly();
        while (nextTicket != ticket) {
            turns[ticket - 1].await();
        }
        monitor.unlock();
    }

    public void exit() throws InterruptedException {
        monitor.lockInterruptibly();
        if (nextTicket != limit) {
            int ticket = nextTicket;
            nextTicket++;
            turns[ticket].signal();
        }
        monitor.unlock();
    }
}
