package HCP.Entities;

import HCP.Enums.AvailableHalls;
import HCP.Enums.PatientAge;
import HCP.Enums.PatientEvaluation;
import HCP.Main.Main;
import HCP.Monitors.CCH.ICCHallPatient;
import HCP.Monitors.EH.IEntranceHallPatient;
import HCP.Monitors.EVH.IEVPatient;
import HCP.Monitors.Logger;

import java.util.concurrent.TimeUnit;

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

    private final Logger log;



    public Patient(
            IEntranceHallPatient eh, ICCHallPatient cch, IEVPatient evh,
            PatientAge age, Logger log
    ) {
        this.age = age;
        this.eh = eh;
        this.cch = cch;
        this.evh = evh;
        this.log = log;

        setDaemon(true);
    }

    @Override
    public void run() {
        eh.waitFreeRoom(id, age);
        eh.waitEvaluationHallCall(age);
        log.createContent(id, AvailableHalls.ETH);
        eh.awakeWaitingPatient(age);
        PatientEvaluation evaluation = evh.waitEvaluation();
        cch.informLeftEVHall();
        log.createContent(id, AvailableHalls.ET1);
        //Thread.currentThread().stop();
    }
}
