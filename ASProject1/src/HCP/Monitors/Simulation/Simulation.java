package HCP.Monitors.Simulation;

import HCP.Entities.TCallCenter;
import HCP.Entities.THCPStarter;
import HCP.Entities.TNurse;
import HCP.Entities.TPatient;
import HCP.Enums.PatientAge;
import HCP.Monitors.CCH.MCCHall;
import HCP.Monitors.EH.MEntranceHall;
import HCP.Monitors.EVH.MEvaluationHall;
import HCP.Monitors.MDH.MMedicalHall;
import HCP.Monitors.MLogger;
import HCP.Monitors.PYH.MPaymentHall;
import HCP.Monitors.WTH.MWaitingHall;

/**
 * Class representing a single simulation. This class
 * allows to start a simulation with several parameters
 * and to control it.
 */
public class Simulation {

    private final int childCount;
    private final int adultCount;
    private final int patientsCount;
    private final int seatCount;
    private final int maxEvaluationDelay;
    private final int maxTreatmentDelay;
    private final int maxPaymentDelay;
    private final int maxTimeToMove;

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

    private final int sendToHCPport;

    private final MLogger log;

    /**
     * Creates a simulation.
     * @param childCount Number of children patients
     * @param adultCount Number of adult patients
     * @param maxEvaluationDelay Maximum delay in milliseconds for evaluation of a patient
     * @param maxTreatmentDelay Maximum delay in milliseconds for treatment of a patient
     * @param maxTimeToMove Maximum delay in milliseconds for payment by a patient
     */
    public Simulation(
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

        this.sendToHCPport = 6060;




        patientsCount = childCount + adultCount;
        this.log = new MLogger(patientsCount);
        cch = new MCCHall();


        //TODO: is it really 4?
        eh = new MEntranceHall(4);
        evh = new MEvaluationHall(patientsCount, maxEvaluationDelay);
        wth = new MWaitingHall(childCount, adultCount, seatCount / 2, 1);
        mdh = new MMedicalHall(childCount, adultCount, maxTreatmentDelay);
        pyh = new MPaymentHall(patientsCount, maxPaymentDelay);

        callCenterThread = new TCallCenter(eh, cch, wth, mdh);
        nurseThread = new TNurse(evh);
        childThreads = new TPatient[childCount];
        adultThreads = new TPatient[adultCount];

        for (int i = 0; i < childCount; i++) {
            childThreads[i] = new TPatient(eh, cch, evh, wth, mdh, pyh, PatientAge.CHILD,log , sendToHCPport);
        }

        for (int i = 0; i < adultCount; i++) {
            adultThreads[i] = new TPatient(eh, cch, evh, wth, mdh, pyh, PatientAge.ADULT, log , sendToHCPport);
        }

        THCPStarter thcpStarter = new THCPStarter();
        thcpStarter.start();


    }

    public void start() {

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

    }

    public void resume() {

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
    }

    public void setAuto(boolean isAuto) {
        cch.setAuto(isAuto);
    }

    public void moveCCManually() {
        cch.manualMove();
    }
}
