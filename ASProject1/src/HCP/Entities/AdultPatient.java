package HCP.Entities;

import HCP.Monitors.CCH.ICCHPatient;
import HCP.Monitors.EH.IEHPatient;

public class AdultPatient extends Thread {
    private final ICCHPatient cch;
    private final IEHPatient eh;

    public AdultPatient(ICCHPatient cch, IEHPatient eh) {
        this.cch = cch;
        this.eh = eh;
    }

    @Override
    public void run() {
        eh.enterAdult();
        cch.informAdultLeftEH();
        cch.informTreated();
    }
}
