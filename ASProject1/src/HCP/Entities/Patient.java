package HCP.Entities;

import HCP.Enums.PatientAge;
import HCP.Monitors.CCH.ICCHallPatient;
import HCP.Monitors.EH.IEntranceHallPatient;

/**
 * Patient Thread
 */
public class Patient extends Thread {
    private static int idCounter;

    private final int id = idCounter++;
    private final PatientAge age;

    private final IEntranceHallPatient eh;
    private final ICCHallPatient cch;

    public Patient(
            IEntranceHallPatient eh, ICCHallPatient cch, PatientAge age
    ) {
        this.age = age;
        this.eh = eh;
        this.cch = cch;

        setDaemon(true);
    }

    @Override
    public void run() {
        eh.waitFreeRoom(id, age);
        eh.waitEvaluationHallCall(age);
        eh.awakeWaitingPatient(age);
    }
}
