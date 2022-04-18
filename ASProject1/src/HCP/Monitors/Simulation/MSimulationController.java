package HCP.Monitors.Simulation;

import HCP.Enums.AvailableHalls;
import HCP.Monitors.MLogger;
import HCP.gui.HCP_GUI;

import java.awt.event.WindowEvent;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MSimulationController implements ISCEntities, ISCSimulation {
    private final ReentrantLock monitor = new ReentrantLock();
    private final HCP_GUI gui = new HCP_GUI();
    private final MLogger logger;

    private boolean isPaused = false;
    private final Condition resumed = monitor.newCondition();

    public MSimulationController(int patientsCount) {
        logger = new MLogger(patientsCount);
    }

    @Override
    public void waitIfPaused() throws InterruptedException {
        monitor.lockInterruptibly();
        while (isPaused) {
            resumed.await();
        }
        monitor.unlock();
    }

    @Override
    public void pause() {
        monitor.lock();
        isPaused = true;
        monitor.unlock();
    }

    @Override
    public void resume() {
        monitor.lock();
        isPaused = false;
        resumed.signalAll();
        monitor.unlock();
    }

    public void log(String patientId, AvailableHalls room) throws InterruptedException {
        logger.createContent(patientId, room);
    }

    public void putPatient(String hall, String id, String color) throws InterruptedException {
        gui.put_patient(hall, id, color);
    }

    public void removePatient(String hall, String id) throws InterruptedException {
        gui.remove_patient(hall, id);
    }

    public void cleanup() {
        gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));
    }
}
