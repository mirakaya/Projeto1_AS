package HCP.Entities;

import HCP.Monitors.EVH.IEVNurse;

/**
 * Nurse Thread
 */
public class Nurse extends Thread {
    private final IEVNurse evh;

    public Nurse(IEVNurse evh) {
        this.evh = evh;

        setDaemon(true);
    }

    @Override
    public void run() {
        while(evh.anyPatientsLeft()) {
            evh.waitPatients();
            evh.evaluateNextPatient();
        }
    }
}
