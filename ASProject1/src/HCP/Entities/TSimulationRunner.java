package HCP.Entities;

import HCP.Enums.PatientAge;
import HCP.Monitors.CCH.MCCHall;
import HCP.Monitors.EH.MEntranceHall;
import HCP.Monitors.EVH.MEvaluationHall;
import HCP.Monitors.MDH.MMedicalHall;
import HCP.Monitors.MLogger;
import HCP.Monitors.PYH.MPaymentHall;
import HCP.Monitors.Simulation.MSimulationController;
import HCP.Monitors.WTH.MWaitingHall;
import HCP.gui.HCP_GUI;

/**
 * Class representing a single simulation. This class
 * allows to start a simulation with several parameters
 * and to control it.
 */
public class TSimulationRunner {

    private final int childCount;
    private final int adultCount;
    private final int patientsCount;
    private final int seatCount;
    private final int maxEvaluationDelay;
    private final int maxTreatmentDelay;
    private final int maxPaymentDelay;
    private final int maxTimeToMove;

    private MSimulationController simulationController;

    private final MCCHall cch;
    private final MEntranceHall eh;
    private final MEvaluationHall evh;
    private final MWaitingHall wth;
    private final MMedicalHall mdh;
    private final MPaymentHall pyh;

    private final TPatient[] childThreads;
    private final TPatient[] adultThreads;
    private final TCallCenter callCenterThread;
    private final TNurse nurseThread;
    private final Thread guiThread;

    private final HCP_GUI gui;
    private final MLogger log;

    /**
     * Creates a simulation.
     * @param childCount Number of children patients
     * @param adultCount Number of adult patients
     * @param maxEvaluationDelay Maximum delay in milliseconds for evaluation of a patient
     * @param maxTreatmentDelay Maximum delay in milliseconds for treatment of a patient
     * @param maxTimeToMove Maximum delay in milliseconds for payment by a patient
     */
    public TSimulationRunner(
            int childCount, int adultCount, int seatCount,
            int maxEvaluationDelay, int maxTreatmentDelay,
            int maxPaymentDelay, int maxTimeToMove
    ) {
        this.childCount = childCount;
        this.adultCount = adultCount;
        this.seatCount = seatCount;
        this.maxEvaluationDelay = maxEvaluationDelay;
        this.maxTreatmentDelay = maxTreatmentDelay;
        this.maxPaymentDelay = maxPaymentDelay;
        this.maxTimeToMove = maxTimeToMove;

        patientsCount = childCount + adultCount;

        MSimulationController simulationController = new MSimulationController(patientsCount);
        this.simulationController = simulationController;
        this.log = new MLogger(patientsCount);
        this.gui = new HCP_GUI();

        System.out.println("GUI Initialized");

        cch = new MCCHall(simulationController);
        eh = new MEntranceHall(seatCount, simulationController);
        evh = new MEvaluationHall(patientsCount, maxEvaluationDelay, simulationController);
        wth = new MWaitingHall(childCount, adultCount, seatCount / 2, 1, simulationController);
        mdh = new MMedicalHall(childCount, adultCount, maxTreatmentDelay, simulationController);
        pyh = new MPaymentHall(patientsCount, maxPaymentDelay, simulationController);

        guiThread = new Thread(() -> gui.setVisible(true));

        callCenterThread = new TCallCenter(patientsCount, eh, cch, wth, mdh);
        nurseThread = new TNurse(evh);
        childThreads = new TPatient[childCount];
        adultThreads = new TPatient[adultCount];

        for (int i = 0; i < childCount; i++) {
            childThreads[i] = new TPatient(eh, cch, evh, wth, mdh, pyh, PatientAge.CHILD);
        }

        for (int i = 0; i < adultCount; i++) {
            adultThreads[i] = new TPatient(eh, cch, evh, wth, mdh, pyh, PatientAge.ADULT);
        }
    }

    public void start() {
        guiThread.setDaemon(true);
        guiThread.start();

        callCenterThread.start();
        nurseThread.start();

        for (int i = 0; i < childCount; i++) {
            childThreads[i].start();
        }

        for (int i = 0; i < adultCount; i++) {
            adultThreads[i].start();
        }


    }

    public void join() throws InterruptedException {

        for (int i = 0; i < childCount; i++) {
            childThreads[i].join();
        }

        for (int i = 0; i < adultCount; i++) {
            adultThreads[i].join();
        }

        nurseThread.join();

        cch.informExit();
        callCenterThread.join();
    }

    public void pause() {
        simulationController.pause();
    }

    public void resume() {
        simulationController.resume();
    }

    public void stop() {
        callCenterThread.interrupt();
        nurseThread.interrupt();

        for (int i = 0; i < childCount; i++) {
            childThreads[i].interrupt();
        }

        for (int i = 0; i < adultCount; i++) {
            adultThreads[i].interrupt();
        }

        try {
            join();
        } catch (InterruptedException ignored) {}

        simulationController.cleanup();
    }

    public void setAuto(boolean isAuto) {
        cch.setAuto(isAuto);
    }

    public void moveCCManually() {
        cch.manualMove();
    }
}
