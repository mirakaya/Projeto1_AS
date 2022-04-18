package HCP.Entities;

import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.CCH.ICCHallPatient;
import HCP.Monitors.EH.IEntranceHallPatient;
import HCP.Monitors.EVH.IEVPatient;
import HCP.Monitors.MDH.IMDHPatient;
import HCP.Monitors.PYH.IPYPatient;
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
    private final IMDHPatient mdh;
    private final IPYPatient pyh;

    public TPatient(
            IEntranceHallPatient eh, ICCHallPatient cch,
            IEVPatient evh, IWTHPatient wth,
            IMDHPatient mdh, IPYPatient pyh,
            PatientAge age
    ) {
        this.age = age;
        this.eh = eh;
        this.cch = cch;
        this.evh = evh;
        this.wth = wth;
        this.mdh = mdh;
        this.pyh = pyh;

        setDaemon(true);
    }

    @Override
    public void run() {
        try {


            eh.waitFreeRoom(id, age);
            int etn = eh.waitEvaluationHallCall(id, age);
            eh.awakeWaitingPatient(etn, age);

            PatientEvaluation evaluation = evh.waitEvaluation(etn, age);
            cch.informLeftEVHall();

            int wtn = wth.waitWTRFree(age, evaluation);
            cch.informLeftWTR(age);
            wth.waitMDWCall(wtn, age,evaluation);

            int mdhRoom = mdh.waitMDRCall(age, wtn, evaluation);
            cch.informLeftMDW(age);
            mdh.waitMDRConcluded(age, wtn, mdhRoom, evaluation);
            cch.informLeftMDR(age);

            pyh.waitPayment(age, evaluation);

        } catch (InterruptedException ignored) {}
    }
}
