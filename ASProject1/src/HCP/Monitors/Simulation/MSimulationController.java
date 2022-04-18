package HCP.Monitors.Simulation;

import HCP.Enums.AvailableHalls;
import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.MLogger;
import HCP.gui.HCP_GUI;
import HCP.gui.PatientColors;

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

    public void log(PatientAge age, int patientId, AvailableHalls room) throws InterruptedException {
        logger.createContent(age.loggerString + patientId, room);
    }

    public void log(
            PatientAge age, int patientId,
            PatientEvaluation evaluation, AvailableHalls room
    ) throws InterruptedException {
        logger.createContent(age.loggerString + patientId + evaluation.loggerString, room);
    }

    public void putPatient(AvailableHalls hall, PatientAge age, int id, PatientColors color) throws InterruptedException {
        gui.put_patient(hall.name(), age.hcpGuiString + id, color.color);
    }

    public void removePatient(AvailableHalls hall, PatientAge age, int id) throws InterruptedException {
        gui.remove_patient(hall.name(), age.hcpGuiString + id);
    }

    public void putNurse(AvailableHalls hall) throws InterruptedException {
        gui.put_patient(hall.name(), "Nurse " + hall.name(), PatientColors.NONE.color);
    }

    public void removeNurse(AvailableHalls hall) throws InterruptedException {
        gui.remove_patient(hall.name(), "Nurse " + hall.name());
    }

    public void putCashier() throws InterruptedException {
        gui.put_patient(AvailableHalls.PYH.name(), "Cashier PYH", "");
    }

    public void removeCashier() throws InterruptedException {
        gui.remove_patient(AvailableHalls.PYH.name(), "Cashier PYH");
    }

    public void cleanup() {
        gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));
    }
}
