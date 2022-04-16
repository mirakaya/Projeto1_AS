package HCP.Entities;

import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.CCH.ICCHallPatient;
import HCP.Monitors.EH.IEntranceHallPatient;
import HCP.Monitors.EVH.IEVPatient;
import HCP.Monitors.WTH.IWTHPatient;

/**
 * Patient Thread
 */
public class TPatient extends Thread {
    private static int idCounter;

    private final int id = idCounter++;
    private final PatientAge age;

    private final IEntranceHallPatient eh;
    private final IEVPatient evh;
    private final ICCHallPatient cch;
    private final IWTHPatient wth;

    public TPatient(
            IEntranceHallPatient eh, ICCHallPatient cch,
            IEVPatient evh, IWTHPatient wth,
            PatientAge age
    ) {
        this.age = age;
        this.eh = eh;
        this.cch = cch;
        this.evh = evh;
        this.wth = wth;

        setDaemon(true);
    }

    @Override
    public void run() {
        eh.waitFreeRoom(id, age);
        eh.waitEvaluationHallCall(age);
        eh.awakeWaitingPatient(age);
        PatientEvaluation evaluation = evh.waitEvaluation();
        cch.informLeftEVHall();

        int wtn = wth.waitWTRFree(age);
        cch.informLeftWTR(age);
        wth.waitMDWCall(wtn, age,evaluation);

        // Missing code here to fill in later
        cch.informLeftMDW(age);
    }
}
