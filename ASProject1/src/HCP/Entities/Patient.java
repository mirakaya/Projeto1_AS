package HCP.Entities;

import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Monitors.CCH.ICCHallPatient;
import HCP.Monitors.EH.IEntranceHallPatient;
import HCP.Monitors.EVH.IEVPatient;

/**
 * Patient Thread
 */
public class Patient extends Thread {
    private static int idCounter;

    private final int id = idCounter++;
    private final PatientAge age;

    private final IEntranceHallPatient eh;
    private final IEVPatient evh;
    private final ICCHallPatient cch;

    public Patient(
            IEntranceHallPatient eh, ICCHallPatient cch, IEVPatient evh,
            PatientAge age
    ) {
        this.age = age;
        this.eh = eh;
        this.cch = cch;
        this.evh = evh;

        setDaemon(true);
    }

    @Override
    public void run() {
        eh.waitFreeRoom(id, age);
        eh.waitEvaluationHallCall(age);
        eh.awakeWaitingPatient(age);
        PatientEvaluation evaluation = evh.waitEvaluation();
        cch.informLeftEVHall();
    }
}
